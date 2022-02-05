package ru.nextleap.fox_in_box.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.google.gson.internal.LinkedTreeMap
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import ru.nextleap.common.doubleValue
import java.io.Serializable

@Parcelize
data class Aggregated(private val map: LinkedTreeMap<String, Any?>) : Serializable, Parcelable {
    @IgnoredOnParcel
    @SerializedName("Id")
    var Id: Int = 0

    @IgnoredOnParcel
    @SerializedName("Image")
    var Image: String? = null

    @IgnoredOnParcel
    @SerializedName("Name")
    var Name: String? = null

    @IgnoredOnParcel
    @SerializedName("Description")
    var Description: String? = null


    init {
        Id = map["Id"].toString().doubleValue.toInt()
        Image = map["Image"] as String?
        Name = map["Name"] as String?
        Description = map["Description"] as String?
    }
}