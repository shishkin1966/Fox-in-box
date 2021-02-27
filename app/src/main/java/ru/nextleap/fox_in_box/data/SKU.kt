package ru.nextleap.fox_in_box.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.google.gson.internal.LinkedTreeMap
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import ru.nextleap.common.to_double
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class SKU(private var map: LinkedTreeMap<String, Any?>) : Serializable, Parcelable {
    @IgnoredOnParcel
    @SerializedName("Id")
    var Id: Int = 0

    @IgnoredOnParcel
    @SerializedName("Category")
    var Category: String? = null

    @IgnoredOnParcel
    @SerializedName("Subcategory")
    var Subcategory: String? = null

    @IgnoredOnParcel
    @SerializedName("Brand")
    var Brand: String? = null

    @IgnoredOnParcel
    @SerializedName("CreateDate")
    var CreateDate: Date? = null

    @IgnoredOnParcel
    @SerializedName("EndDate")
    var EndDate: Date? = null

    @IgnoredOnParcel
    @SerializedName("Code")
    var Code: String? = null

    @IgnoredOnParcel
    @SerializedName("ExternalCode")
    var ExternalCode: String? = null

    @IgnoredOnParcel
    @SerializedName("UnitBarCode")
    var UnitBarCode: String? = null

    @IgnoredOnParcel
    @SerializedName("BoxBarCode")
    var BoxBarCode: String? = null

    @IgnoredOnParcel
    @SerializedName("Volume")
    var Volume: Double? = null

    @IgnoredOnParcel
    @SerializedName("Weight")
    var Weight: Double? = null

    @IgnoredOnParcel
    @SerializedName("Amount")
    var Amount: Int? = null

    @IgnoredOnParcel
    @SerializedName("Links")
    var Links: String? = null

    @IgnoredOnParcel
    @SerializedName("DomainFolderId")
    var DomainFolderId: Int? = null

    @IgnoredOnParcel
    @SerializedName("Group")
    var Group: String? = null

    @IgnoredOnParcel
    @SerializedName("VolumeUnit")
    var VolumeUnit: String? = null

    @IgnoredOnParcel
    @SerializedName("WeightUnit")
    var WeightUnit: String? = null

    @IgnoredOnParcel
    @SerializedName("VendorCode")
    var VendorCode: String? = null

    @IgnoredOnParcel
    @SerializedName("RRC")
    var RRC: String? = null

    @IgnoredOnParcel
    @SerializedName("Order")
    var Order: Int? = null

    @IgnoredOnParcel
    @SerializedName("CustomField1")
    var CustomField1: String? = null

    @IgnoredOnParcel
    @SerializedName("CustomField2")
    var CustomField2: String? = null

    @IgnoredOnParcel
    @SerializedName("CustomField3")
    var CustomField3: String? = null

    @IgnoredOnParcel
    @SerializedName("CustomField4")
    var CustomField4: String? = null

    @IgnoredOnParcel
    @SerializedName("CustomField5")
    var CustomField5: String? = null

    @IgnoredOnParcel
    @SerializedName("CustomField6")
    var CustomField6: String? = null

    @IgnoredOnParcel
    @SerializedName("CustomField7")
    var CustomField7: String? = null

    @IgnoredOnParcel
    @SerializedName("CustomField8")
    var CustomField8: String? = null

    @IgnoredOnParcel
    @SerializedName("CustomField9")
    var CustomField9: String? = null

    @IgnoredOnParcel
    @SerializedName("CustomField10")
    var CustomField10: String? = null

    @IgnoredOnParcel
    @SerializedName("AmountForUser")
    var AmountForUser: Int? = null

    @IgnoredOnParcel
    @SerializedName("ProjectId")
    var ProjectId: Int? = null

    @IgnoredOnParcel
    @SerializedName("Name")
    var Name: String? = null

    @IgnoredOnParcel
    @SerializedName("Description")
    var Description: String? = null

    @IgnoredOnParcel
    @SerializedName("Image")
    var Image: String? = null

    @IgnoredOnParcel
    @SerializedName("ImageStyle")
    var ImageStyle: String? = null

    @IgnoredOnParcel
    @SerializedName("IsFolder")
    var IsFolder: Boolean? = null

    init {
        Id = map["Id"].toString().to_double().toInt()
        Category = map["Category"] as String?
        Subcategory = map["Subcategory"] as String?
        Brand = map["Brand"] as String?
        Brand = map["Brand"] as String?
        if (!(map["CreateDate"] as String?).isNullOrEmpty()) CreateDate =
            SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss",
                Locale.getDefault()
            ).parse(map["CreateDate"] as String)
        if (!(map["EndDate"] as String?).isNullOrEmpty()) EndDate =
            SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss",
                Locale.getDefault()
            ).parse(map["EndDate"] as String)
        Code = map["Code"] as String?
        ExternalCode = map["ExternalCode"] as String?
        UnitBarCode = map["UnitBarCode"] as String?
        BoxBarCode = map["BoxBarCode"] as String?
        Volume = map["Volume"].toString().to_double()
        Weight = map["Weight"].toString().to_double()
        Amount = map["Amount"].toString().to_double().toInt()
        Links = map["Links"] as String?
        DomainFolderId = map["DomainFolderId"].toString().to_double().toInt()
        Group = map["Group"] as String?
        VolumeUnit = map["VolumeUnit"] as String?
        WeightUnit = map["WeightUnit"] as String?
        VendorCode = map["VendorCode"] as String?
        RRC = map["RRC"] as String?
        Order = (map["Order"] as Double?)?.toInt()
        CustomField1 = map["CustomField1"] as String?
        CustomField2 = map["CustomField2"] as String?
        CustomField3 = map["CustomField3"] as String?
        CustomField4 = map["CustomField4"] as String?
        CustomField5 = map["CustomField5"] as String?
        CustomField6 = map["CustomField6"] as String?
        CustomField7 = map["CustomField7"] as String?
        CustomField8 = map["CustomField8"] as String?
        CustomField9 = map["CustomField9"] as String?
        CustomField10 = map["CustomField10"] as String?
        AmountForUser = map["AmountForUser"].toString().to_double().toInt()
        ProjectId = map["ProjectId"].toString().to_double().toInt()
        Name = map["Name"] as String?
        Description = map["Description"] as String?
        Image = map["Image"] as String?
        ImageStyle = map["ImageStyle"] as String?
        IsFolder = map["IsFolder"] as Boolean?

    }
}