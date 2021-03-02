package ru.nextleap.fox_in_box.screen.products

import com.google.gson.annotations.SerializedName
import ru.nextleap.fox_in_box.data.SKU
import java.io.Serializable

class SKUData : Serializable {
    @SerializedName("list")
    var list: ArrayList<SKU> = ArrayList()
}
