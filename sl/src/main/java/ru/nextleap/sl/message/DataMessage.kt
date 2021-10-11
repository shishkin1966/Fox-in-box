package ru.nextleap.sl.message

import ru.nextleap.sl.provider.IMessengerSubscriber

open class DataMessage(address: String, private val data: Any) : AbsMessage(address) {

    override fun read(subscriber: IMessengerSubscriber) {
        subscriber.read(this)
    }

    override fun copy(): IMessage {
        return DataMessage(getAddress(), data)
    }

    override fun isCheckDublicate(): Boolean {
        return true
    }

    fun getData() : Any {
        return data
    }

}