package ru.nextleap.fox_in_box.data

import com.google.gson.annotations.SerializedName
import com.google.gson.internal.LinkedTreeMap
import java.io.Serializable


class PaymentSettings(map: LinkedTreeMap<String, Any?>) : Serializable {
    @SerializedName("UserId")
    var UserId: String? = null

    @SerializedName("BankBik")
    var BankBik: String? = null

    @SerializedName("BankName")
    var BankName: String? = null

    @SerializedName("BankCorrAccount")
    var BankCorrAccount: String? = null

    @SerializedName("BankPaymentAccount")
    var BankPaymentAccount: String? = null

    init {
        UserId = map["UserId"] as String?
        BankBik = map["BankBik"] as String?
        BankName = map["BankName"] as String?
        BankCorrAccount = map["BankCorrAccount"] as String?
        BankPaymentAccount = map["BankPaymentAccount"] as String?
    }
}
