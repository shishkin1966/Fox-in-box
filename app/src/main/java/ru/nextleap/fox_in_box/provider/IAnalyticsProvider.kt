package ru.nextleap.fox_in_box.provider

import ru.nextleap.sl.IProvider

interface IAnalyticsProvider : IProvider {
    fun eventContent(id: String, name: String)

    fun eventLogin()

    fun eventScreenView(name: String)

    fun event(id: String, key: String, value: String)
}