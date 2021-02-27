package ru.nextleap.sl.provider

import ru.nextleap.sl.IProviderSubscriber
import ru.nextleap.sl.lifecycle.ILifecycle
import ru.nextleap.sl.message.IMessage

interface IMessengerSubscriber : IProviderSubscriber, ILifecycle {
    fun read(message: IMessage)
}
