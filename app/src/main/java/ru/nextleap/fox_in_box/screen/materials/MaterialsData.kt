package ru.nextleap.fox_in_box.screen.materials

import com.google.gson.annotations.SerializedName
import ru.nextleap.fox_in_box.data.Materials
import java.io.Serializable

class MaterialsData : Serializable {
    @SerializedName("list")
    var list: ArrayList<Materials> = ArrayList()
}
