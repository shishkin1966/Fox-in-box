package ru.nextleap.fox_in_box.screen.settings

import ru.nextleap.fox_in_box.ApplicationConstant
import ru.nextleap.fox_in_box.R
import ru.nextleap.fox_in_box.setting.Setting
import ru.nextleap.sl.action.DataAction
import ru.nextleap.sl.action.IAction
import ru.nextleap.sl.presenter.AbsModelPresenter
import ru.nextleap.sl.provider.ApplicationProvider

class SettingsPresenter(model: SettingsModel) : AbsModelPresenter(model) {
    companion object {
        const val NAME = "SettingsPresenter"
        const val RefreshViews = "RefreshViews"
        const val SettingChangedAction = "SettingChangedAction"
    }

    private lateinit var data: SettingsData

    override fun isRegister(): Boolean {
        return true
    }

    override fun getName(): String {
        return NAME
    }

    override fun onStart() {
        if (!::data.isInitialized) {
            data = SettingsData()
            getData()
        }
        getView<SettingsFragment>().addAction(DataAction(RefreshViews, data))
    }

    private fun getData() {
        val list = ArrayList<Setting>()
        var setting: Setting?

        setting = Setting.restore(ApplicationConstant.TitleSetting)
        if (setting == null) {
            setting = Setting(
                name = ApplicationConstant.TitleSetting,
                title = ApplicationProvider.appContext.getString(R.string.setting_title),
                type = Setting.TYPE_TEXT,
                current = null,
                default = null,
                values = null,
                id = -1,
                inputType = 0
            )
            setting.backup()
        }
        list.add(setting)

        setting = Setting.restore(ApplicationConstant.UsePushSetting)
        if (setting == null) {
            setting = Setting(
                name = ApplicationConstant.UsePushSetting,
                current = "false",
                default = "false",
                title = ApplicationProvider.appContext.getString(R.string.setting_use_push),
                values = null,
                id = ApplicationConstant.UsePushSettingId,
                type = Setting.TYPE_SWITCH,
                inputType = 0
            )
            setting.backup()
        }
        list.add(setting)

        setting = Setting.restore(ApplicationConstant.UseSoundSetting)
        if (setting == null) {
            setting = Setting(
                name = ApplicationConstant.UseSoundSetting,
                current = "false",
                default = "false",
                title = ApplicationProvider.appContext.getString(R.string.setting_use_sound),
                values = null,
                id = ApplicationConstant.UseSoundSettingId,
                type = Setting.TYPE_SWITCH,
                inputType = 0
            )
            setting.backup()
        }
        list.add(setting)

        data.settings = list
    }

    override fun onAction(action: IAction): Boolean {
        if (!isValid()) return false

        if (action is DataAction<*>) {
            when (action.getName()) {
                SettingChangedAction -> {
                    (action.getData() as Setting).backup()
                    return true
                }
            }
        }
        return false
    }
}
