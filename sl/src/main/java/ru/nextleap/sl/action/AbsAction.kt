package ru.nextleap.sl.action

open class AbsAction : IAction {
    private var isRun: Boolean = false

    override fun isRun(): Boolean {
        return isRun
    }

    override fun setRun() {
        isRun = true
    }
}