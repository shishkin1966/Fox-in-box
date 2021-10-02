package ru.nextleap.fox_in_box.provider

import ru.nextleap.sl.AbsProvider
import ru.nextleap.sl.IProvider

class DebugProvider : AbsProvider(), IDebugProvider {
    companion object {
        const val NAME = "DebugProvider"
    }

    override fun getName(): String {
        return NAME
    }

    override fun compareTo(other: IProvider): Int {
        return if (other is IDebugProvider) 0 else 1
    }

    override fun onRegister() {
    }
}