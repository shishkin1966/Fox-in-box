package ru.nextleap.fox_in_box.screen.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import ru.nextleap.fox_in_box.ApplicationSingleton
import ru.nextleap.fox_in_box.R
import ru.nextleap.fox_in_box.setting.Setting
import ru.nextleap.sl.action.DataAction
import ru.nextleap.sl.action.IAction
import ru.nextleap.sl.action.handler.FragmentActionHandler
import ru.nextleap.sl.model.IModel
import ru.nextleap.sl.ui.AbsContentFragment


class SettingsFragment : AbsContentFragment(), CompoundButton.OnCheckedChangeListener {

    companion object {
        const val NAME = "SettingsFragment"

        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }

    private val actionHandler = FragmentActionHandler(this)
    private lateinit var listView: LinearLayout

    override fun createModel(): IModel {
        return SettingsModel(this)
    }

    override fun onAction(action: IAction): Boolean {
        if (!isValid()) return false

        if (action is DataAction<*>) {
            when (action.getName()) {
                SettingsPresenter.RefreshViews -> {
                    refreshViews(action.getData() as SettingsData)
                    return true
                }
            }
        }

        if (actionHandler.onAction(action)) return true

        return false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            ApplicationSingleton.instance.desktopProvider.getLayoutId(
                "fragment_settings",
                R.layout.fragment_settings
            ), container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listView = view.findViewById(R.id.list)
    }

    override fun isTop(): Boolean {
        return false
    }

    override fun getName(): String {
        return NAME
    }

    private fun refreshViews(data: SettingsData) {
        listView.removeAllViews()

        for (setting in data.settings) {
            val view = getView(listView, setting)
            if (view != null) {
                listView.addView(view)
            }
        }
    }

    private fun getView(parent: ViewGroup, setting: Setting): View? {
        var view: View? = null
        val titleView: TextView?
        var currentValue: String?

        when (setting.type) {
            Setting.TYPE_TEXT -> {
                view = layoutInflater.inflate(
                    ApplicationSingleton.instance.desktopProvider.getLayoutId(
                        "setting_item_text",
                        R.layout.setting_item_text
                    ), parent, false
                )
                titleView = view.findViewById(R.id.item_title)
                titleView.text = setting.title
            }

            Setting.TYPE_SWITCH -> {
                view = layoutInflater.inflate(
                    ApplicationSingleton.instance.desktopProvider.getLayoutId(
                        "setting_item_switch",
                        R.layout.setting_item_switch
                    ), parent, false
                )
                titleView = view.findViewById(R.id.item_title)
                titleView.text = setting.title

                val valueView = view.findViewById<SwitchCompat>(R.id.item_switch)
                currentValue = setting.current
                valueView.isChecked = currentValue!!.toBoolean()
                valueView.tag = setting
                valueView.setOnCheckedChangeListener(this)
            }

        }
        return view
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        val setting = buttonView?.tag as Setting?
        if (setting != null) {
            setting.current = isChecked.toString()
        }
        getModel<SettingsModel>().getPresenter<SettingsPresenter>()
            .addAction(DataAction(SettingsPresenter.SettingChangedAction, setting))
    }
}
