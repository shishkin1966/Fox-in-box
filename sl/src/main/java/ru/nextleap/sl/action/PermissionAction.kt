package ru.nextleap.sl.action

class PermissionAction(private val permission: String) : AbsAction(), IAction {
    private var listener: String? = null
    private var helpMessage: String? = null

    constructor(permission: String, listener: String, helpMessage: String) : this(permission) {
        this.listener = listener
        this.helpMessage = helpMessage
    }

    fun getListener(): String? {
        return listener
    }

    fun getPermission(): String {
        return permission
    }

    fun getHelpMessage(): String? {
        return helpMessage
    }


}
