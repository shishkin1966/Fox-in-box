package ru.nextleap.common

import android.animation.Animator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.text.Html
import android.text.Spanned
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.fragment.app.Fragment
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.annimon.stream.Stream
import com.annimon.stream.function.Predicate
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.muddzdev.styleabletoast.StyleableToast
import java.io.Serializable
import java.lang.reflect.Type


@Suppress("UNCHECKED_CAST", "unused")
@SuppressLint("ObsoleteSdkInt")
class ApplicationUtils {
    companion object {

        const val REQUEST_PERMISSIONS = 10000
        const val MESSAGE_TYPE_INFO = 0
        const val MESSAGE_TYPE_ERROR = 1
        const val MESSAGE_TYPE_WARNING = 2
        const val MESSAGE_TYPE_SUCCESS = 3

        @JvmStatic
        fun hasHoneycomb(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB
        }

        @JvmStatic
        fun hasJellyBean(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
        }

        @JvmStatic
        fun hasJellyBeanMR1(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1
        }

        @JvmStatic
        fun hasJellyBeanMR2(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2
        }

        @JvmStatic
        fun hasKitKat(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
        }

        @JvmStatic
        fun isLollipop(): Boolean {
            return Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP || Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP_MR1
        }

        @JvmStatic
        fun hasLollipop(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
        }

        @JvmStatic
        fun hasMarshmallow(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
        }

        @JvmStatic
        fun hasN(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
        }

        @JvmStatic
        fun hasNMR1(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1
        }

        @JvmStatic
        fun hasO(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
        }

        @JvmStatic
        fun hasOMR1(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1
        }

        @JvmStatic
        fun hasP(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P
        }

        @JvmStatic
        fun hasQ(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
        }

        @JvmStatic
        fun hasR(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.R
        }

        @JvmStatic
        fun getPhoneInfo(): String {
            val sb = StringBuilder()
            sb.append("\n")
            sb.append("Android version : " + Build.VERSION.RELEASE)
            sb.append("\n")
            sb.append("Board:" + Build.BOARD)
            sb.append("\n")
            sb.append("Manufacturer:" + Build.MANUFACTURER)
            sb.append("\n")
            sb.append("Model:" + Build.MODEL)
            sb.append("\n")
            sb.append("Product:" + Build.PRODUCT)
            sb.append("\n")
            sb.append("Device:" + Build.DEVICE)
            sb.append("\n")
            sb.append("ROM:" + Build.DISPLAY)
            sb.append("\n")
            sb.append("Hardware:" + Build.HARDWARE)
            sb.append("\n")
            sb.append("Id:" + Build.ID)
            sb.append("\n")
            sb.append("Tags:" + Build.TAGS)
            sb.append("\n")
            return sb.toString()
        }

        @JvmStatic
        fun runOnUiThread(action: Runnable) {
            Handler(Looper.getMainLooper()).post(action)
        }

        @JvmStatic
        fun getStatusPermission(context: Context?, permission: String): Int {
            return if (context != null) {
                ActivityCompat.checkSelfPermission(context, permission)
            } else {
                PackageManager.PERMISSION_DENIED
            }
        }

        @JvmStatic
        fun checkPermission(context: Context?, permission: String): Boolean {
            return getStatusPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        }

        @JvmStatic
        fun grantPermissions(permissions: Array<String>?, activity: Activity?): Boolean {
            if (activity != null && permissions != null) {
                if (hasMarshmallow()) {
                    val listPermissionsNeeded = ArrayList<String>()

                    for (permission in permissions) {
                        if (ActivityCompat.checkSelfPermission(
                                activity.applicationContext,
                                permission
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            listPermissionsNeeded.add(permission)
                        }
                    }

                    if (!listPermissionsNeeded.isEmpty()) {
                        val arrayPermissionsNeeded =
                            arrayOfNulls<String>(listPermissionsNeeded.size)
                        listPermissionsNeeded.toArray(arrayPermissionsNeeded)
                        ActivityCompat.requestPermissions(
                            activity,
                            arrayPermissionsNeeded,
                            REQUEST_PERMISSIONS
                        )
                        return false
                    }
                } else {
                    return true
                }
            }
            return false
        }

        @JvmStatic
        fun grantPermissions(permissions: Array<String>?, fragment: Fragment): Boolean {
            val context = fragment.activity?.applicationContext
            if (permissions != null && context != null) {
                if (hasMarshmallow()) {
                    val listPermissionsNeeded = ArrayList<String>()

                    for (permission in permissions) {
                        if (ActivityCompat.checkSelfPermission(
                                context,
                                permission
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            listPermissionsNeeded.add(permission)
                        }
                    }

                    if (listPermissionsNeeded.isNotEmpty()) {
                        val arrayPermissionsNeeded =
                            arrayOfNulls<String>(listPermissionsNeeded.size)
                        listPermissionsNeeded.toArray(arrayPermissionsNeeded)
                        fragment.requestPermissions(
                            arrayPermissionsNeeded,
                            REQUEST_PERMISSIONS
                        )
                        return false
                    }
                } else {
                    return true
                }
            }
            return false
        }

        @JvmStatic
        fun getDrawable(context: Context, id: Int): Drawable? {
            return ResourcesCompat.getDrawable(
                context.resources,
                id,
                if (hasMarshmallow()) context.theme else null
            )
        }

        @JvmStatic
        fun getVectorDrawable(
            context: Context,
            id: Int,
            theme: Resources.Theme
        ): VectorDrawableCompat? {
            return VectorDrawableCompat.create(context.resources, id, theme)
        }

        @JvmStatic
        fun getColor(context: Context, id: Int): Int {
            return ResourcesCompat.getColor(
                context.resources,
                id,
                if (hasMarshmallow()) context.theme else null
            )
        }

        @JvmStatic
        fun getDimensionPx(context: Context, resId: Int): Float {
            return context.resources.getDimension(resId)
        }

        @JvmStatic
        fun getDimensionDp(context: Context, resId: Int): Float {
            return (context.resources.getDimension(resId) / context.resources.displayMetrics.density)
        }

        @JvmStatic
        fun getDimensionSp(context: Context, resId: Int): Float {
            return (context.resources.getDimension(resId) / context.resources.displayMetrics.scaledDensity)
        }

        @JvmStatic
        fun showToast(
            context: Context,
            text: String?,
            duration: Int = Toast.LENGTH_SHORT,
            type: Int = MESSAGE_TYPE_INFO
        ) {
            if (text.isNullOrBlank()) return

            runOnUiThread {
                when (type) {
                    MESSAGE_TYPE_ERROR -> StyleableToast.Builder(context)
                        .text(text)
                        .textColor(getColor(context, R.color.white))
                        .backgroundColor(getColor(context, R.color.red))
                        .textSize(getDimensionSp(context, R.dimen.text_size_xlarge))
                        .cornerRadius(8)
                        .length(duration)
                        .show()
                    MESSAGE_TYPE_WARNING -> StyleableToast.Builder(context)
                        .text(text)
                        .textColor(getColor(context, R.color.white))
                        .backgroundColor(getColor(context, R.color.orange))
                        .textSize(getDimensionSp(context, R.dimen.text_size_xlarge))
                        .cornerRadius(8)
                        .length(duration)
                        .show()
                    else -> StyleableToast.Builder(context)
                        .text(text)
                        .textColor(getColor(context, R.color.white))
                        .backgroundColor(getColor(context, R.color.blue_dark))
                        .textSize(getDimensionSp(context, R.dimen.text_size_xlarge))
                        .cornerRadius(8)
                        .length(duration)
                        .show()
                }
            }
        }

        @JvmStatic
        fun <T> filter(list: Collection<T>, predicate: Predicate<in T>): Stream<T> {
            return Stream.of(list).filter(predicate)
        }

        @JvmStatic
        fun <T> sort(list: Collection<T>, comparator: Comparator<in T>): Stream<T> {
            return Stream.of(list).sorted(comparator)
        }

        @JvmStatic
        fun <V : View> findView(activity: Activity, @IdRes id: Int): V? {
            return activity.findViewById<View>(id) as V?
        }

        @JvmStatic
        fun <V : View> findView(view: View, @IdRes id: Int): V? {
            return view.findViewById<View>(id) as V?
        }

        @JvmStatic
        fun showToast(context: Context, resId: Int, duration: Int, type: Int) {
            showToast(context, context.getString(resId), duration, type)
        }

        @JvmStatic
        fun dp2px(context: Context, dpValue: Float): Float {
            val scale = context.resources.displayMetrics.density
            return (dpValue * scale + 0.5f)
        }

        @JvmStatic
        fun px2dp(context: Context, pxValue: Float): Float {
            val scale = context.resources.displayMetrics.density
            return (pxValue / scale + 0.5f)
        }

        @JvmStatic
        fun sp2px(context: Context, spValue: Float): Float {
            val fontScale = context.resources.displayMetrics.scaledDensity
            return (spValue * fontScale + 0.5f)
        }

        @JvmStatic
        fun px2sp(context: Context, pxValue: Float): Float {
            val fontScale = context.resources.displayMetrics.scaledDensity
            return (pxValue / fontScale + 0.5f)
        }

        @JvmStatic
        fun getScreenWidth(context: Context): Int {
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = wm.defaultDisplay
            val metrics = DisplayMetrics()
            display.getMetrics(metrics)
            return metrics.widthPixels
        }

        @JvmStatic
        fun getScreenHeight(context: Context): Int {
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = wm.defaultDisplay
            val metrics = DisplayMetrics()
            display.getMetrics(metrics)
            return metrics.heightPixels
        }

        /**
         * Get Smartphone screen size in inch
         *
         * @return The smartphone screen size
         */
        @JvmStatic
        fun diagonalInch(context: Context): Double {
            val widthPixels = context.resources.displayMetrics.widthPixels
            val heightPixels = context.resources.displayMetrics.heightPixels

            val widthDpi = context.resources.displayMetrics.xdpi
            val heightDpi = context.resources.displayMetrics.ydpi

            val widthInches = widthPixels / widthDpi
            val heightInches = heightPixels / heightDpi

            val diagonal =
                Math.sqrt((widthInches * widthInches + heightInches * heightInches).toDouble())
            return Math.floor(diagonal + 0.5)
        }

        @JvmStatic
        fun isPhone(context: Context): Boolean {
            val diagonalInches = diagonalInch(context)
            return diagonalInches < 7
        }

        @JvmStatic
        fun is6inchPhone(context: Context): Boolean {
            val diagonalInches = diagonalInch(context)
            return diagonalInches >= 6 && diagonalInches < 7
        }

        @JvmStatic
        fun is7inchTablet(context: Context): Boolean {
            val diagonalInches = diagonalInch(context)
            return diagonalInches >= 7 && diagonalInches < 9
        }

        @JvmStatic
        fun is10inchTablet(context: Context): Boolean {
            val diagonalInches = diagonalInch(context)
            return diagonalInches >= 9
        }

        /**
         * Return the handle to a system-level service by name. The class of the
         * returned object varies by the requested name.
         */
        @JvmStatic
        fun <S> getSystemService(context: Context, serviceName: String): S {
            return context.getSystemService(serviceName) as S
        }

        @JvmStatic
        fun showSnackbar(view: View, message: String, duration: Int, type: Int): Snackbar {
            val snackbar = BaseSnackbar().make(view, message, duration, type)
            snackbar.show()
            return snackbar
        }

        /**
         * Контролировать наличие и версию Google Play Services
         */
        @JvmStatic
        fun isGooglePlayServices(context: Context?): Boolean {
            if (context != null) {
                val resultCode =
                    GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context)
                if (ConnectionResult.SUCCESS == resultCode) {
                    return true
                }
            }
            return false
        }

        /**
         * Получить возможность службы геолокации
         */
        @JvmStatic
        fun isLocationEnabled(context: Context): Boolean {
            if (hasKitKat()) {
                var locationMode = 0
                try {
                    locationMode = Settings.Secure.getInt(
                        context.contentResolver,
                        Settings.Secure.LOCATION_MODE
                    )
                } catch (e: Settings.SettingNotFoundException) {
                    return false
                }
                return locationMode != Settings.Secure.LOCATION_MODE_OFF
            } else {
                val locationProviders = Settings.Secure.getString(
                    context.contentResolver,
                    Settings.Secure.LOCATION_PROVIDERS_ALLOWED
                )
                return !locationProviders.isNullOrEmpty()
            }
        }

        @JvmStatic
        fun showUrl(context: Context, url: String) {
            if (url.isEmpty()) return

            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP

            if (canStartActivity(context, intent)) {
                context.startActivity(intent)
            }
        }

        @JvmStatic
        fun canStartActivity(context: Context, intent: Intent): Boolean {
            val packageManager = context.packageManager
            return (packageManager?.resolveActivity(intent, 0) != null)
        }

        @JvmStatic
        fun getEmailIntent(recipient: String, subject: String, body: String): Intent {
            val intent = Intent()
            intent.action = Intent.ACTION_SENDTO
            intent.type = "plain/text"
            intent.data = Uri.parse("mailto:$recipient")
            intent.putExtra(Intent.EXTRA_SUBJECT, subject)
            intent.putExtra(Intent.EXTRA_TEXT, body)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            return intent
        }

        @JvmStatic
        fun getResourceId(context: Context?, typeResource: String?, nameResource: String?): Int {
            // Example: context.getResources().getIdentifier("widget_blue", "layout", context.getPackageName())
            return context?.resources?.getIdentifier(
                nameResource,
                typeResource,
                context.packageName
            )
                ?: 0
        }

        @JvmStatic
        fun toJson(obj: Any): Serializable {
            return Gson().toJson(obj)
        }

        @JvmStatic
        fun toJson(obj: Any, type: Type): Serializable {
            //use example : type = new com.google.gson.reflect.TypeToken<List<ContactItem>>(){}.getType()
            return Gson().toJson(obj, type)
        }

        @JvmStatic
        fun <T> fromJson(json: String, cl: Class<T>): T {
            return Gson().fromJson(json, cl)
        }

        @JvmStatic
        fun <T> fromJson(json: String, type: Type): T {
            // use example : type = new com.google.gson.reflect.TypeToken<List<ContactItem>>(){}.getType()
            return Gson().fromJson<T>(json, type)
        }

        @JvmStatic
        fun expand(v: View) {
            v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            val targetHeight = v.measuredHeight

            // Older versions of android (pre API 21) cancel animations for views with a height of 0.
            v.visibility = View.VISIBLE
            val va: ValueAnimator = ValueAnimator.ofInt(1, targetHeight)
            va.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
                override fun onAnimationUpdate(animation: ValueAnimator) {
                    v.layoutParams.height = (animation.getAnimatedValue() as Int)
                    v.requestLayout()
                }
            })
            va.addListener(object : Animator.AnimatorListener {
                override fun onAnimationEnd(animation: Animator?) {
                    v.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                }

                override fun onAnimationStart(animation: Animator?) {}
                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationRepeat(animation: Animator?) {}
            })
            va.setDuration(300)
            va.setInterpolator(OvershootInterpolator())
            va.start()
        }

        @JvmStatic
        fun collapse(v: View) {
            val initialHeight = v.measuredHeight
            val va = ValueAnimator.ofInt(initialHeight, 0)
            va.addUpdateListener { animation ->
                v.layoutParams.height = (animation.animatedValue as Int)
                v.requestLayout()
            }
            va.addListener(object : Animator.AnimatorListener {
                override fun onAnimationEnd(animation: Animator) {
                    v.visibility = View.GONE
                }

                override fun onAnimationStart(animation: Animator) {}
                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}
            })
            va.duration = 300
            va.interpolator = DecelerateInterpolator()
            va.start()
        }

        @JvmStatic
        fun fromHtml(source: String?): Spanned? {
            if (source == null) return null
            return if (hasN()) {
                Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(source)
            }
        }

        @JvmStatic
        fun setRoundedImage(context: Context, view: ImageView) {
            val imageBitmap: Bitmap = (view.getDrawable() as BitmapDrawable).getBitmap()
            val imageDrawable: RoundedBitmapDrawable = RoundedBitmapDrawableFactory.create(
                context.getResources(),
                imageBitmap
            )
            imageDrawable.isCircular = true
            imageDrawable.cornerRadius = Math.max(
                imageBitmap.width,
                imageBitmap.height
            ) / 2.0f
            view.setImageDrawable(imageDrawable)
        }

    }

}
