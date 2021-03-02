package ru.nextleap.fox_in_box.screen.news

import com.google.gson.annotations.SerializedName
import ru.nextleap.fox_in_box.data.News
import java.io.Serializable

class NewsData : Serializable {
    @SerializedName("list")
    var list: ArrayList<News> = ArrayList()
}
