package ru.nextleap.sl.provider

import ru.nextleap.common.ApplicationUtils
import ru.nextleap.sl.AbsSmallUnion
import ru.nextleap.sl.IProvider
import ru.nextleap.sl.Secretary
import ru.nextleap.sl.lifecycle.Lifecycle
import ru.nextleap.sl.message.IMessage
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger
import kotlin.collections.ArrayList


class MessengerUnion : AbsSmallUnion<IMessengerSubscriber>(),
    IMessengerUnion {
    companion object {
        const val NAME = "MessengerUnion"
    }

    private val messages = Collections.synchronizedMap(ConcurrentHashMap<Int, IMessage>())
    private val messagingList = Secretary<List<String>>()
    private val atomicId = AtomicInteger(0)

    override fun getName(): String {
        return NAME
    }

    override fun onAddSubscriber(subscriber: IMessengerSubscriber) {
        readMessages(subscriber)
    }

    override fun getMessages(subscriber: IMessengerSubscriber): List<IMessage> {
        if (messages.isEmpty()) {
            return ArrayList()
        }

        // удаляем старые письма
        val name = subscriber.getName()
        val currentTime = System.currentTimeMillis()
        val list = ApplicationUtils.filter(
            messages.values
        ) { message -> message.contains(name) && message.getEndTime() != -1L && message.getEndTime() < currentTime }
            .toList()
        for (message in list) {
            messages.remove(message.getMessageId())
        }

        if (messages.isEmpty()) {
            return ArrayList()
        }

        val byId = Comparator<IMessage> { left, right ->
            left.getMessageId().compareTo(right.getMessageId())
        }
        return ApplicationUtils.filter(
            messages.values
        ) { message -> message.contains(name) && (message.getEndTime() == -1L || message.getEndTime() != -1L && message.getEndTime() > currentTime) }
            .sorted(byId).toList()
    }

    override fun clearMessages(subscriber: String) {
        if (messages.isEmpty()) {
            return
        }

        val list = ApplicationUtils.filter(
            messages.values
        ) { message -> message.contains(subscriber) }.toList()
        if (list.isNotEmpty()) {
            for (message in list) {
                messages.remove(message.getMessageId())
            }
        }
    }

    override fun clearMessages() {
        messages.clear()
    }

    override operator fun compareTo(other: IProvider): Int {
        return if (other is IMessengerUnion) 0 else 1
    }

    override fun addMessage(message: IMessage) {
        val list = ArrayList<String>()
        list.addAll(message.getCopyTo())
        if (!message.getAddress().isNullOrEmpty()) {
            list.add(message.getAddress())
        }
        val addresses = ArrayList<String>()
        for (address in list) {
            addresses.addAll(getAddresses(address))
        }
        for (address in addresses) {
            val id = atomicId.incrementAndGet()
            val newMessage = message.copy()
            newMessage.setMessageId(id)
            newMessage.setAddress(address)
            newMessage.setCopyTo(ArrayList())

            if (!message.isCheckDublicate()) {
                messages[id] = newMessage
            } else {
                removeDublicate(newMessage)
                messages[id] = newMessage
            }

            checkAndReadMessagesSubscriber(address)
        }
    }

    private fun checkAndReadMessagesSubscriber(address: String) {
        val subscriber = getSubscriber(address)
        if (subscriber != null && address == subscriber.getName()) {
            readMessages(subscriber)
        }
    }

    private fun checkSubscriber(address: String): IMessengerSubscriber? {
        val subscriber = getSubscriber(address)
        if (subscriber != null && address == subscriber.getName()) {
            val state = subscriber.getState()
            if (state == Lifecycle.VIEW_READY || state == Lifecycle.VIEW_NOT_READY) {
                return subscriber
            }
        }
        return null
    }

    private fun getAddresses(address: String): List<String> {
        val addresses = ArrayList<String>()
        if (messagingList.containsKey(address)) {
            val list = messagingList.get(address)
            if (list != null) {
                for (adr in list) {
                    addresses.addAll(getAddresses(adr))
                }
            }
        } else {
            addresses.add(address)
        }
        return addresses
    }

    private fun removeDublicate(message: IMessage) {
        for (tmpMessage in messages.values) {
            if (message.getSubj() == tmpMessage.getSubj() && message.getAddress() == tmpMessage.getAddress()) {
                removeMessage(tmpMessage)
            }
        }
    }

    override fun addNotMandatoryMessage(message: IMessage) {
        val list = ArrayList<String>()
        list.addAll(message.getCopyTo())
        if (!message.getAddress().isNullOrEmpty()) {
            list.add(message.getAddress())
        }
        val addresses = ArrayList<String>()
        for (address in list) {
            addresses.addAll(getAddresses(address))
        }
        for (address in addresses) {
            val subscriber = checkSubscriber(address)
            if (subscriber != null) {
                message.read(subscriber)
            }
        }
    }

    override fun replaceMessage(message: IMessage) {
        val list = ArrayList<String>()
        list.addAll(message.getCopyTo())
        if (!message.getAddress().isNullOrEmpty()) {
            list.add(message.getAddress())
        }
        val addresses = ArrayList<String>()
        for (address in list) {
            addresses.addAll(getAddresses(address))
        }
        for (address in addresses) {
            val id = atomicId.incrementAndGet()
            val newMessage = message.copy()
            newMessage.setMessageId(id)
            newMessage.setAddress(address)
            newMessage.setCopyTo(ArrayList())

            removeDublicate(newMessage)
            messages[id] = newMessage

            checkAndReadMessagesSubscriber(address)
        }
    }

    override fun removeMessage(message: IMessage) {
        messages.remove(message.getMessageId())
    }

    override fun readMessages(subscriber: IMessengerSubscriber) {
        val list = getMessages(subscriber)
        if (list.isNotEmpty()) {
            for (message in list) {
                val state = subscriber.getState()
                if (state == Lifecycle.VIEW_READY) {
                    message.read(subscriber)
                    removeMessage(message)
                }
            }
        }
    }

    override fun addMessagingList(name: String, addresses: List<String>) {
        messagingList.put(name, addresses)
    }

    override fun addMessagingList(name: String, addresses: Array<String>) {
        messagingList.put(name, addresses.toList())
    }

    override fun removeMessagingList(name: String) {
        messagingList.remove(name)
    }

    override fun getMessagingList(name: String): List<String>? {
        return if (messagingList.containsKey(name)) {
            messagingList.get(name)
        } else {
            null
        }
    }

}
