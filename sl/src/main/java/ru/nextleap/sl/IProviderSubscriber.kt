package ru.nextleap.sl

/**
 * Интерфейс объекта, который регистрируется у провайдеров для получения/предоставления сервиса
 */
interface IProviderSubscriber : ISubscriber {

    /**
     * Получить список имен провайдеров, у которых должен быть зарегистрирован объект
     *
     * @return список имен провайдеров
     */
    fun getProviderSubscription(): List<String>

    /**
     * Событие - провайдер прекратил работу
     */
    fun onStopProvider(provider: IProvider)

    /**
     * Остановить
     */
    fun stop()

}
