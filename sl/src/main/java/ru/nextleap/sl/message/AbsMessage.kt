package ru.nextleap.sl.message

import ru.nextleap.sl.action.AbsAction
import java.util.*


abstract class AbsMessage() : AbsAction(), IMessage {
    private var id = -1
    private lateinit var address: String
    private var copyTo: LinkedList<String> = LinkedList()
    private var keepAliveTime: Long = -1
    private var subj: String? = null

    constructor(address: String) : this() {
        this.address = address
    }

    constructor(message: IMessage) : this() {
    }

    override fun getMessageId(): Int {
        return id
    }

    override fun setMessageId(id: Int): IMessage {
        this.id = id
        return this
    }

    override fun getAddress(): String {
        return address
    }

    override fun setAddress(address: String): IMessage {
        this.address = address
        return this
    }

    override fun getCopyTo(): List<String> {
        return copyTo
    }

    override fun setCopyTo(copyTo: List<String>): IMessage {
        this.copyTo.clear()
        this.copyTo.addAll(copyTo)
        return this
    }

    override fun contains(address: String): Boolean {
        if (address.isBlank()) {
            return false
        }

        if (address == this.address) {
            return true
        }

        for (s in copyTo) {
            if (s == address) {
                return true
            }
        }
        return false
    }

    override fun getEndTime(): Long {
        return keepAliveTime
    }

    override fun setEndTime(keepAliveTime: Long): IMessage {
        this.keepAliveTime = keepAliveTime
        return this
    }

    override fun isCheckDublicate(): Boolean {
        return false
    }

    override fun isSticky(): Boolean {
        return false
    }

    override fun setSubj(subj: String): IMessage {
        this.subj = subj
        return this
    }

    override fun getSubj(): String {
        return if (subj.isNullOrEmpty()) {
            this::class.java.simpleName
        } else {
            subj!!
        }
    }
}
