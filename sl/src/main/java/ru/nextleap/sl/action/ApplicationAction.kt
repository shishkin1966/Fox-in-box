package ru.nextleap.sl.action

import ru.nextleap.sl.INamed

open class ApplicationAction(private val name: String) : AbsAction(), INamed {

    override fun getName(): String {
        return name
    }
}
