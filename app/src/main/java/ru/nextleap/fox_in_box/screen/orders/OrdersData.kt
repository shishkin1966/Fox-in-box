package ru.nextleap.fox_in_box.screen.orders

import com.google.gson.annotations.SerializedName
import ru.nextleap.fox_in_box.data.Orders
import java.io.Serializable

class OrdersData : Serializable {
    @SerializedName("list")
    var list: ArrayList<Orders> = ArrayList()
}
