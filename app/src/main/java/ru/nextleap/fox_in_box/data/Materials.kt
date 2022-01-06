package ru.nextleap.fox_in_box.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.google.gson.internal.LinkedTreeMap
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import ru.nextleap.common.toDouble
import java.io.Serializable

@Parcelize
data class Materials(private var map: LinkedTreeMap<String, Any?>) : Serializable, Parcelable {
    @IgnoredOnParcel
    @SerializedName("Id")
    var Id: Int = 0

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
    @SerializedName("IsFolder")
    var IsFolder: Boolean? = null

    @IgnoredOnParcel
    @SerializedName("Complete")
    var Complete: Boolean? = null

    @IgnoredOnParcel
    @SerializedName("ImageStyle")
    var ImageStyle: String? = null

    @IgnoredOnParcel
    @SerializedName("LikesEnabled")
    var LikesEnabled: Boolean? = null

    @IgnoredOnParcel
    @SerializedName("LikesCount")
    var LikesCount: Int? = null

    @IgnoredOnParcel
    @SerializedName("ViewButtonName")
    var ViewButtonName: String? = null

    @IgnoredOnParcel
    @SerializedName("TestButtonName")
    var TestButtonName: String? = null

    @IgnoredOnParcel
    @SerializedName("MyVote")
    var MyVote: Boolean? = null

    @IgnoredOnParcel
    @SerializedName("Category")
    var Category: String? = null

    @IgnoredOnParcel
    @SerializedName("ProgressPercents")
    var ProgressPercents: String? = null

    @IgnoredOnParcel
    @SerializedName("ProgressStatus")
    var ProgressStatus: String? = null

    @IgnoredOnParcel
    @SerializedName("ImagePath")
    var ImagePath: String? = null

    init {
        Id = map["Id"].toString().toDouble().toInt()
        ProjectId = map["ProjectId"].toString().toDouble().toInt()
        Name = map["Name"] as String?
        Description = map["Description"] as String?
        Image = map["Image"] as String?
        IsFolder = map["IsFolder"] as Boolean?
        Complete = map["Complete"] as Boolean?
        ImageStyle = map["ImageStyle"] as String?
        LikesEnabled = map["LikesEnabled"] as Boolean?
        LikesCount = map["LikesCount"].toString().toDouble().toInt()
        ViewButtonName = map["ViewButtonName"] as String?
        TestButtonName = map["TestButtonName"] as String?
        MyVote = map["MyVote"] as Boolean?
        Category = map["Category"] as String?
        ProgressPercents = map["ProgressPercents"] as String?
        ProgressStatus = map["ProgressStatus"] as String?
        ImagePath = map["ImagePath"] as String?
    }
}