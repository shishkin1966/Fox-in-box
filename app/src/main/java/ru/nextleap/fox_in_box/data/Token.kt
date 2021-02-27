package ru.nextleap.fox_in_box.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Token : Serializable {
    @SerializedName("userName")
    var userName: String? = null

    @SerializedName("expires_in")
    var expires_in: Int? = null

    @SerializedName("access_token")
    var access_token: String? = null

    @SerializedName("error")
    var error: String? = null

    @SerializedName("error_description")
    var error_description: String? = null
}