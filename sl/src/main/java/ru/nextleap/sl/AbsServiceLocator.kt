package ru.nextleap.sl

import ru.nextleap.sl.provider.ActivityUnion
import ru.nextleap.sl.provider.ApplicationProvider


/**
 * Абстрактный администратор
 */
@Suppress("UNCHECKED_CAST")
abstract class AbsServiceLocator : IServiceLocator {
    private val secretary = Secretary<IProvider>()

    companion object {
        const val NAME = "AbsServiceLocator"
    }

    override fun <C : IProvider> get(name: String): C? {
        if (!exists(name) && !registerProvider(name)) {
            return null
        }

        if (secretary.get(name) != null) {
            return secretary.get(name) as C
        } else {
            secretary.remove(name)
        }
        return null
    }

    override fun exists(name: String): Boolean {
        return secretary.containsKey(name)
    }

    override fun registerProvider(provider: IProvider): Boolean {
        if (secretary.containsKey(provider.getName())) {
            val oldprovider = get<IProvider>(provider.getName())
            if (oldprovider != null && oldprovider.compareTo(provider) != 0) {
                return false
            }
            if (!unregisterProvider(provider.getName())) {
                return false
            }
        }

        secretary.put(provider.getName(), provider)
        provider.onRegister()
        return true
    }

    override fun registerProvider(name: String): Boolean {
        val provider = getProviderFactory().create(name)
        return if (provider != null) {
            registerProvider(provider)
        } else false
    }

    override fun unregisterProvider(name: String): Boolean {
        if (secretary.containsKey(name)) {
            val provider = secretary.get(name)
            if (provider != null) {
                // нельзя отменить регистрацию у объединения с подписчиками
                if (!provider.isPersistent()) {
                    if (provider is ISmallUnion<*> && provider.hasSubscribers()) {
                        return false
                    }
                    provider.onUnRegister()
                    secretary.remove(name)
                }
            } else {
                secretary.remove(name)
            }
        }
        return true
    }

    override fun registerSubscriber(subscriber: IProviderSubscriber): Boolean {
        val types = subscriber.getProviderSubscription()
        // регистрируемся subscriber у провайдеров
        for (type in types) {
            if (secretary.containsKey(type)) {
                val provider = secretary.get(type)
                if (provider is ISmallUnion<*>) {
                    (provider as ISmallUnion<IProviderSubscriber>).register(subscriber)
                }
            } else {
                registerProvider(type)
                if (secretary.containsKey(type)) {
                    (secretary.get(type) as ISmallUnion<IProviderSubscriber>).register(subscriber)
                } else {
                    return false
                }
            }
        }
        return true
    }

    override fun unregisterSubscriber(subscriber: IProviderSubscriber): Boolean {
        val types = subscriber.getProviderSubscription()
        for (type in types) {
            if (secretary.containsKey(type)) {
                val provider = secretary.get(type)
                if (provider is ISmallUnion<*>) {
                    (provider as ISmallUnion<IProviderSubscriber>).unregister(subscriber)
                }
            }
        }
        return true
    }

    override fun setCurrentSubscriber(subscriber: IProviderSubscriber): Boolean {
        val types = subscriber.getProviderSubscription()
        for (type in types) {
            if (secretary.containsKey(type)) {
                val provider = secretary.get(type)
                if (provider is IUnion<*>) {
                    (provider as IUnion<IProviderSubscriber>).setCurrentSubscriber(subscriber)
                }
            }
        }
        return true
    }

    override fun getProviders(): List<IProvider> {
        return secretary.values()
    }

    override fun stop() {
        if (ApplicationProvider.instance.isExit()) {
            get<ActivityUnion>(ActivityUnion.NAME)?.stop()
        }
    }
}
