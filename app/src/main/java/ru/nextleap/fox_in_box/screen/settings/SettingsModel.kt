package ru.nextleap.fox_in_box.screen.settings

import ru.nextleap.sl.model.AbsPresenterModel


class SettingsModel(view: SettingsFragment) : AbsPresenterModel(view) {
    init {
        setPresenter(SettingsPresenter(this))
    }
}
