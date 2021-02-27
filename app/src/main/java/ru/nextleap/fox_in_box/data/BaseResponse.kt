package ru.nextleap.fox_in_box.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class BaseResponse : Serializable {
    @SerializedName("code")
    var code: Int? = null

    @SerializedName("result")
    var result: Any? = null
}