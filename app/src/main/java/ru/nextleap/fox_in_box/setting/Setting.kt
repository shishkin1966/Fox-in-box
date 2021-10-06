package ru.nextleap.fox_in_box.setting


import com.google.gson.annotations.SerializedName
import ru.nextleap.common.ApplicationUtils
import ru.nextleap.sl.PreferencesUtils
import ru.nextleap.sl.provider.ApplicationProvider
import java.io.Serializable


class Setting(
    @SerializedName("values") var values: List<String>?,
    @SerializedName("current") var current: String?,
    @SerializedName("default") var default: String?,
    @SerializedName("name") var name: String,
    @SerializedName("title") var title: String?,
    @SerializedName("id") var id: Int,
    @SerializedName("type") var type: Int,
    @SerializedName("inputType") var inputType: Int
) : Serializable {

    companion object {
        const val TYPE_TEXT = 0
        const val TYPE_SWITCH = 1

        @JvmStatic
        fun restore(name: String): Setting? {
            val json = PreferencesUtils.getString(ApplicationProvider.appContext, name, null)
            if (json == null) {
                return null
            }
            return ApplicationUtils.fromJson(json, Setting::class.java)
        }
    }

    fun backup() {
        PreferencesUtils.putString(
            ApplicationProvider.appContext,
            name,
            ApplicationUtils.toJson(this).toString()
        )
    }
}
