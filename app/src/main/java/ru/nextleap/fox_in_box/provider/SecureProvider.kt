package ru.nextleap.fox_in_box.provider

import android.security.KeyPairGeneratorSpec
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import io.paperdb.Paper
import ru.nextleap.common.ApplicationUtils.Companion.hasKitKat
import ru.nextleap.common.ApplicationUtils.Companion.hasMarshmallow
import ru.nextleap.common.ApplicationUtils.Companion.hasO
import ru.nextleap.common.CollectionsUtils
import ru.nextleap.common.byteArrayToHex
import ru.nextleap.fox_in_box.ApplicationSingleton.instance
import ru.nextleap.fox_in_box.R
import ru.nextleap.sl.AbsProvider
import ru.nextleap.sl.IProvider
import ru.nextleap.sl.provider.ApplicationProvider
import java.math.BigInteger
import java.security.*
import java.security.spec.MGF1ParameterSpec
import java.security.spec.RSAKeyGenParameterSpec
import java.util.*
import java.util.concurrent.locks.ReentrantLock
import javax.crypto.Cipher
import javax.crypto.spec.OAEPParameterSpec
import javax.crypto.spec.PSource
import javax.security.auth.x500.X500Principal

class SecureProvider : AbsProvider(), ISecureProvider {
    companion object {
        const val NAME = "SecureProvider"
        private const val ANDROID_KEYSTORE_INSTANCE = "AndroidKeyStore"
        private const val HASH_ALGORITHM = "SHA-1"
        private const val KEY_ALGORITHM = "RSA"
        private const val TRANSFORMATION = "RSA/ECB/PKCS1Padding"
        private const val TRANSFORMATION_M = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding"
    }

    private lateinit var keyStore: KeyStore
    private var alias: String? = null
    private var lock = ReentrantLock()
    private var isHardwareSupport = false

    override fun onRegister() {
        lock = ReentrantLock()
        try {
            val digest = MessageDigest.getInstance(HASH_ALGORITHM)
            val hash = digest.digest(NAME.toByteArray(Charsets.UTF_8))
            alias = hash.byteArrayToHex()
            val context = ApplicationProvider.appContext
            Paper.init(context)
            keyStore = KeyStore.getInstance(ANDROID_KEYSTORE_INSTANCE)
            keyStore.load(null)
            isHardwareSupport = checkKeyPair()
        } catch (e: Exception) {
            instance.onError(NAME, e)
        }
    }

    private fun checkKeyPair(): Boolean {
        if (!isValid()) {
            return false
        }
        lock.lock()
        try {
            if (!keyStore.containsAlias(alias)) {
                if (createKeyPair()) {
                    return true
                }
            } else {
                return true
            }
        } catch (e: Exception) {
            instance.onError(NAME, e)
        } finally {
            lock.unlock()
        }
        return false
    }

    private fun createKeyPair(): Boolean {
        var keyPairGenerator: KeyPairGenerator? = null
        try {
            /**
             * On Android Marshmellow we can use new security features
             */
            if (hasMarshmallow()) {
                keyPairGenerator = KeyPairGenerator.getInstance(
                    KEY_ALGORITHM, ANDROID_KEYSTORE_INSTANCE
                )
                keyPairGenerator.initialize(
                    KeyGenParameterSpec.Builder(
                        alias!!,
                        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                    )
                        .setBlockModes(KeyProperties.BLOCK_MODE_ECB)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_OAEP)
                        .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
                        .setAlgorithmParameterSpec(
                            RSAKeyGenParameterSpec(
                                2048,
                                RSAKeyGenParameterSpec.F4
                            )
                        )
                        .setKeySize(2048)
                        .build()
                )
            } else {
                if (hasKitKat()) {
                    val start: Calendar = GregorianCalendar()
                    val end: Calendar = GregorianCalendar()
                    end.add(Calendar.ERA, 1)
                    val context = ApplicationProvider.appContext
                    val keyPairGeneratorSpec = KeyPairGeneratorSpec.Builder(context)
                        .setAlias(alias!!) // The subject used for the self-signed certificate of the generated pair
                        .setSubject(X500Principal("CN=" + context.packageName)) // The serial number used for the self-signed certificate of the
                        // generated pair.
                        .setKeyType(KeyProperties.KEY_ALGORITHM_RSA)
                        .setKeySize(2048)
                        .setSerialNumber(BigInteger.valueOf(1967))
                        .setStartDate(start.time)
                        .setEndDate(end.time)
                        .build()
                    keyPairGenerator = KeyPairGenerator
                        .getInstance(KEY_ALGORITHM, ANDROID_KEYSTORE_INSTANCE)
                    keyPairGenerator.initialize(keyPairGeneratorSpec)
                }
            }
            val keyPair = keyPairGenerator!!.generateKeyPair() ?: return false
        } catch (e: Exception) {
            instance.onError(NAME, e)
            return false
        }
        return true
    }

    override fun deleteKeyPair(): Boolean {
        lock.lock()
        try {
            if (keyStore.containsAlias(alias)) {
                keyStore.deleteEntry(alias)
                if (keyStore.containsAlias(alias)) {
                    return false
                }
            }
            return true
        } catch (e: Exception) {
            instance.onError(NAME, e)
        } finally {
            lock.unlock()
        }
        return false
    }

    private fun encryptBase64(data: String): String? {
        if (!isHardwareSupport) return null
        if (!checkKeyPair()) {
            return null
        }
        lock.lock()
        var privateKeyEntry: KeyStore.PrivateKeyEntry? = null
        try {
            privateKeyEntry = keyStore.getEntry(alias, null) as KeyStore.PrivateKeyEntry
        } catch (e: Exception) {
            instance.onError(NAME, e)
        }
        try {
            if (privateKeyEntry != null) {
                val publicKey = privateKeyEntry.certificate.publicKey
                return encryptBase64(data, publicKey)
            }
        } catch (e: Exception) {
            instance.onError(NAME, e)
        } finally {
            lock.unlock()
        }
        return null
    }

    private fun encryptBase64(utf8string: String?, publicKey: PublicKey?): String? {
        if (utf8string == null) {
            return null
        }
        if (publicKey == null) {
            return null
        }
        try {
            val dataBytes: ByteArray = utf8string.toByteArray(Charsets.UTF_8)
            val cipher: Cipher = if (hasMarshmallow()) {
                Cipher.getInstance(TRANSFORMATION_M)
            } else {
                Cipher.getInstance(TRANSFORMATION)
            }
            if (hasO()) {
                val sp = OAEPParameterSpec(
                    "SHA-256",
                    "MGF1", MGF1ParameterSpec("SHA-1"),
                    PSource.PSpecified.DEFAULT
                )
                cipher.init(Cipher.ENCRYPT_MODE, publicKey, sp)
            } else {
                cipher.init(Cipher.ENCRYPT_MODE, publicKey)
            }
            return Base64.encodeToString(cipher.doFinal(dataBytes), Base64.DEFAULT)
        } catch (e: Exception) {
            instance.onError(NAME, e)
        }
        return null
    }

    private fun decryptBase64(base64string: String?, privateKey: PrivateKey?): String? {
        if (base64string == null) {
            return null
        }
        if (privateKey == null) {
            return null
        }
        try {
            val dataBytes = Base64.decode(base64string, Base64.DEFAULT)
            val cipher: Cipher = if (hasMarshmallow()) {
                Cipher.getInstance(TRANSFORMATION_M)
            } else {
                Cipher.getInstance(TRANSFORMATION)
            }
            if (hasO()) {
                val sp = OAEPParameterSpec(
                    "SHA-256",
                    "MGF1", MGF1ParameterSpec("SHA-1"),
                    PSource.PSpecified.DEFAULT
                )
                cipher.init(Cipher.DECRYPT_MODE, privateKey, sp)
            } else {
                cipher.init(Cipher.DECRYPT_MODE, privateKey)
            }
            return String(cipher.doFinal(dataBytes))
        } catch (e: Exception) {
            instance.onError(NAME, e)
        }
        return null
    }

    private fun decryptBase64(base64string: String): String? {
        if (!isHardwareSupport) return null
        if (!checkKeyPair()) {
            return null
        }
        lock.lock()
        var privateKeyEntry: KeyStore.PrivateKeyEntry? = null
        try {
            privateKeyEntry = keyStore.getEntry(alias, null) as KeyStore.PrivateKeyEntry
        } catch (e: Exception) {
            instance.onError(NAME, e)
        }
        try {
            if (privateKeyEntry != null) {
                val privateKey = privateKeyEntry.privateKey
                return decryptBase64(base64string, privateKey)
            }
        } catch (e: Exception) {
            instance.onError(NAME, e)
        } finally {
            lock.unlock()
        }
        return null
    }

    override fun put(key: String, data: String?): Boolean {
        if (data == null) return true
        if (!isHardwareSupport) return false

        lock.lock()
        try {
            val digest = MessageDigest.getInstance(HASH_ALGORITHM)
            val hash = digest.digest(key.toByteArray(Charsets.UTF_8))
            val alias: String = hash.byteArrayToHex()
            if (checkKeyPair()) {
                val dataEncrypted = encryptBase64(data)
                if (dataEncrypted != null) {
                    Paper.book(this.alias).delete(alias)
                    val byteEncrypted: ByteArray = dataEncrypted.toByteArray(Charsets.UTF_8)
                    Paper.book(this.alias).write(alias, byteEncrypted)
                    val dataRestored: ByteArray =
                        Paper.book(this.alias).read<ByteArray>(alias, null)
                    if (CollectionsUtils.equals(byteEncrypted, dataRestored)) {
                        return true
                    } else {
                        instance.onError(
                            NAME,
                            ApplicationProvider.appContext.getString(R.string.security_put_error),
                            true
                        )
                        return false
                    }
                }
            }
        } catch (e: Exception) {
            instance.onError(NAME, e)
        } finally {
            lock.unlock()
        }
        return false
    }

    override fun get(key: String): String? {
        if (!isHardwareSupport) return null

        lock.lock()
        try {
            val digest = MessageDigest.getInstance(HASH_ALGORITHM)
            val hash = digest.digest(key.toByteArray(Charsets.UTF_8))
            val alias: String = hash.byteArrayToHex()
            val data: ByteArray? = Paper.book(this.alias).read<ByteArray>(alias, null)
            if (data != null) {
                return decryptBase64(String(data))
            }
        } catch (e: Exception) {
            instance.onError(NAME, e)
        } finally {
            lock.unlock()
        }
        return null
    }

    override fun clear(key: String) {
        lock.lock()
        try {
            val digest = MessageDigest.getInstance(HASH_ALGORITHM)
            val hash = digest.digest(key.toByteArray(Charsets.UTF_8))
            val alias: String = hash.byteArrayToHex()
            Paper.book(this.alias).delete(alias)
        } catch (e: Exception) {
            instance.onError(NAME, e)
        } finally {
            lock.unlock()
        }
    }

    override fun compareTo(other: IProvider): Int {
        return if (other is ISecureProvider) 0 else 1
    }

    override fun getName(): String {
        return NAME
    }


}