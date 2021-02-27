package ru.nextleap.fox_in_box.provider

import ru.nextleap.fox_in_box.data.Token
import ru.nextleap.sl.IProvider

interface ISessionProvider : IProvider {
    fun onUserInteraction()

    fun stopSession()

    fun startSession()

    fun getLogin(): String?

    fun getPsw(): String?

    fun getToken(): String?

    fun setToken(token: Token)

    fun clearToken()

    fun getFio(): String?
}