package ru.nextleap.fox_in_box.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.google.gson.internal.LinkedTreeMap
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import ru.nextleap.common.doubleValue
import java.io.Serializable

@Parcelize
data class Orders(private var map: LinkedTreeMap<String, Any?>) : Serializable, Parcelable {
    @IgnoredOnParcel
    @SerializedName("Id")
    var Id: Int = 0


    init {
        Id = map["Id"].toString().doubleValue.toInt()

    }
}