package ru.nextleap.fox_in_box.screen

import ru.nextleap.fox_in_box.ApplicationSingleton
import ru.nextleap.sl.ui.AbsContentFragment

abstract class AbsDesktopFragment(name: String, id: Int) : AbsContentFragment(
    ApplicationSingleton.instance.desktopProvider.getLayoutId(
        name,
        id
    )
) {}