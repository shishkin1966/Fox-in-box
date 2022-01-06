package ru.nextleap.fox_in_box.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.google.gson.internal.LinkedTreeMap
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import ru.nextleap.common.toDouble
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class News(private val map: LinkedTreeMap<String, Any?>) : Serializable, Parcelable,
    Comparable<News> {
    @IgnoredOnParcel
    @SerializedName("Id")
    var Id: Int = 0

    @IgnoredOnParcel
    @SerializedName("Title")
    var Title: String? = null

    @IgnoredOnParcel
    @SerializedName("Body")
    var Body: String? = null

    @IgnoredOnParcel
    @SerializedName("Preview")
    var Preview: String? = null

    @IgnoredOnParcel
    @SerializedName("StartDate")
    var StartDate: Date? = null

    @IgnoredOnParcel
    @SerializedName("EndDate")
    var EndDate: Date? = null

    @IgnoredOnParcel
    @SerializedName("Pinned")
    var Pinned: Boolean = false

    @IgnoredOnParcel
    @SerializedName("YoutubeLink")
    var YoutubeLink: String? = null

    @IgnoredOnParcel
    @SerializedName("Image")
    var Image: String? = null

    @IgnoredOnParcel
    @SerializedName("LikesEnabled")
    var LikesEnabled: Boolean? = null

    @IgnoredOnParcel
    @SerializedName("LikesCount")
    var LikesCount: Int? = null

    @IgnoredOnParcel
    @SerializedName("CommentsEnabled")
    var CommentsEnabled: Boolean? = null

    @IgnoredOnParcel
    @SerializedName("CommentsCount")
    var CommentsCount: Int? = null

    @IgnoredOnParcel
    @SerializedName("MyVote")
    var MyVote: Boolean = false

    @IgnoredOnParcel
    @SerializedName("DisplayViews")
    var DisplayViews: Boolean? = null

    @IgnoredOnParcel
    @SerializedName("ViewsCount")
    var ViewsCount: Int? = null

    @IgnoredOnParcel
    @SerializedName("ReadByUser")
    var ReadByUser: Boolean? = null

    @IgnoredOnParcel
    @SerializedName("LastLikedUser")
    var LastLikedUser: String? = null

    @IgnoredOnParcel
    @SerializedName("DateExpired")
    var DateExpired: Boolean? = null

    init {
        Id = map["Id"].toString().toDouble().toInt()
        Title = map["Title"] as String?
        Body = map["Body"] as String?
        Preview = map["Preview"] as String?
        if (!(map["StartDate"] as String?).isNullOrEmpty()) StartDate =
            SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss",
                Locale.getDefault()
            ).parse(map["StartDate"] as String)
        if (!(map["EndDate"] as String?).isNullOrEmpty()) EndDate =
            SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss",
                Locale.getDefault()
            ).parse(map["EndDate"] as String)
        Pinned = map["Pinned"] as Boolean
        YoutubeLink = map["YoutubeLink"] as String?
        Image = map["Image"] as String?
        LikesEnabled = map["LikesEnabled"] as Boolean?
        LikesCount = map["LikesCount"].toString().toDouble().toInt()
        CommentsEnabled = map["CommentsEnabled"] as Boolean?
        CommentsCount = map["CommentsCount"].toString().toDouble().toInt()
        if (map.containsValue("MyVote")) {
            MyVote = map["MyVote"] as Boolean
        }
        DisplayViews = map["DisplayViews"] as Boolean?
        ViewsCount = map["ViewsCount"].toString().toDouble().toInt()
        ReadByUser = map["ReadByUser"] as Boolean?
        LastLikedUser = map["LastLikedUser"] as String?
        DateExpired = map["DateExpired"] as Boolean?
    }

    override fun compareTo(other: News) = compareValuesBy(this, other, { !it.Pinned }, { it.Id })
}