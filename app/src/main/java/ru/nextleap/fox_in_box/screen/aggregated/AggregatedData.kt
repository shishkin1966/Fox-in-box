package ru.nextleap.fox_in_box.screen.aggregated

import com.google.gson.annotations.SerializedName
import ru.nextleap.fox_in_box.data.Aggregated
import java.io.Serializable

class AggregatedData : Serializable {
    @SerializedName("list")
    var list: ArrayList<Aggregated> = ArrayList()
}
