package ru.nextleap.fox_in_box.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.google.gson.internal.LinkedTreeMap
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import ru.nextleap.common.to_double
import java.io.Serializable

@Parcelize
@Suppress("UNCHECKED_CAST")
data class Profile(private var map: LinkedTreeMap<String, Any?>) : Serializable, Parcelable {
    @IgnoredOnParcel
    @SerializedName("Id")
    var Id: String? = null

    @IgnoredOnParcel
    @SerializedName("FirstName")
    var FirstName: String? = null

    @IgnoredOnParcel
    @SerializedName("LastName")
    var LastName: String? = null

    @IgnoredOnParcel
    @SerializedName("MiddleName")
    var MiddleName: String? = null

    @IgnoredOnParcel
    @SerializedName("Email")
    var Email: String? = null

    @IgnoredOnParcel
    @SerializedName("PhoneNumber")
    var PhoneNumber: String? = null

    @IgnoredOnParcel
    @SerializedName("TermsConfirmed")
    var TermsConfirmed: Boolean? = null

    @IgnoredOnParcel
    @SerializedName("NotificationsEnabled")
    var NotificationsEnabled: Boolean? = null

    @IgnoredOnParcel
    @SerializedName("PersonalDataEditingAccept")
    var PersonalDataEditingAccept: Boolean? = null

    @IgnoredOnParcel
    @SerializedName("Image")
    var Image: String? = null

    @IgnoredOnParcel
    @SerializedName("PassportSeries")
    var PassportSeries: String? = null

    @IgnoredOnParcel
    @SerializedName("PassportNumber")
    var PassportNumber: String? = null

    @IgnoredOnParcel
    @SerializedName("PassportIssuedBy")
    var PassportIssuedBy: String? = null

    @IgnoredOnParcel
    @SerializedName("PassportIssuedCode")
    var PassportIssuedCode: String? = null

    @IgnoredOnParcel
    @SerializedName("PassportIssuedDate")
    var PassportIssuedDate: String? = null

    @IgnoredOnParcel
    @SerializedName("PassportRegistrationAddress")
    var PassportRegistrationAddress: String? = null

    @IgnoredOnParcel
    @SerializedName("Inn")
    var Inn: String? = null

    @IgnoredOnParcel
    @SerializedName("PassportVerificationState")
    var PassportVerificationState: Int? = null

    @IgnoredOnParcel
    @SerializedName("PassportVerificationComment")
    var PassportVerificationComment: String? = null

    @IgnoredOnParcel
    @SerializedName("Images")
    var Images: List<String>? = null

    @IgnoredOnParcel
    @SerializedName("Blocked")
    var Blocked: Boolean? = null

    @IgnoredOnParcel
    @SerializedName("ExternalId")
    var ExternalId: String? = null

    @IgnoredOnParcel
    @SerializedName("BirthDate")
    var BirthDate: String? = null

    @IgnoredOnParcel
    @SerializedName("Sex")
    var Sex: Boolean? = null

    @IgnoredOnParcel
    @SerializedName("PaymentSettings")
    var PaymentSettings: PaymentSettings? = null

    @IgnoredOnParcel
    @SerializedName("ExternalLogins")
    var ExternalLogins: List<String>? = null

    @IgnoredOnParcel
    @SerializedName("Fio")
    var Fio: String? = null

    init {
        Id = map["Id"] as String?
        FirstName = map["FirstName"] as String?
        LastName = map["LastName"] as String?
        MiddleName = map["MiddleName"] as String?
        Email = map["Email"] as String?
        PhoneNumber = map["PhoneNumber"] as String?
        TermsConfirmed = map["TermsConfirmed"] as Boolean?
        NotificationsEnabled = map["NotificationsEnabled"] as Boolean?
        PersonalDataEditingAccept = map["PersonalDataEditingAccept"] as Boolean?
        Image = map["Image"] as String?
        PassportSeries = map["PassportSeries"] as String?
        PassportNumber = map["PassportNumber"] as String?
        PassportIssuedBy = map["PassportIssuedBy"] as String?
        PassportIssuedCode = map["PassportIssuedCode"] as String?
        PassportIssuedDate = map["PassportIssuedDate"] as String?
        PassportRegistrationAddress = map["PassportRegistrationAddress"] as String?
        Inn = map["Inn"] as String?
        PassportVerificationState =
            map["PassportVerificationState"].toString().to_double().toInt()
        PassportVerificationComment = map["PassportVerificationComment"] as String?
        Images = map["Images"] as List<String>?
        Blocked = map["Blocked"] as Boolean?
        ExternalId = map["ExternalId"] as String?
        BirthDate = map["BirthDate"] as String?
        Sex = map["Sex"] as Boolean?
        if (map["PaymentSettings"] != null) {
            PaymentSettings =
                PaymentSettings(map["PaymentSettings"] as LinkedTreeMap<String, Any?>)
        }
        ExternalLogins = map["ExternalLogins"] as List<String>?
        Fio = map["Fio"] as String?
    }
}