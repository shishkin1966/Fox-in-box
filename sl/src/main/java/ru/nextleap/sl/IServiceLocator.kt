package ru.nextleap.sl

/**
 * Интерфейс администратора(Service Locator)
 */
interface IServiceLocator : INamed {
    /**
     * Проверить существование провайдера
     *
     * @param name имя провайдера
     * @return true - провайдер существует
     */
    fun exists(name: String): Boolean

    /**
     * Получить провайдера
     *
     * @param <C>  тип провайдера
     * @param name имя провайдера
     * @return провайдер
    </ */
    fun <C : IProvider> get(name: String): C?

    /**
     * Зарегистрировать провайдера
     *
     * @param provider провайдер
     * @return флаг - операция завершена успешно
     */
    fun registerProvider(provider: IProvider): Boolean

    /**
     * Зарегистрировать провайдера
     *
     * @param name имя провайдера
     * @return флаг - операция завершена успешно
     */
    fun registerProvider(name: String): Boolean

    /**
     * Отменить регистрацию провайдера
     *
     * @param name имя провайдера
     * @return флаг - операция завершена успешно
     */
    fun unregisterProvider(name: String): Boolean

    /**
     * Зарегистрировать подписчика провайдера
     *
     * @param subscriber подписчик провайдера
     * @return флаг - операция завершена успешно
     */
    fun registerSubscriber(subscriber: IProviderSubscriber): Boolean

    /**
     * Отменить регистрацию подписчика провайдера
     *
     * @param subscriber подписчик провайдера
     * @return флаг - операция завершена успешно
     */
    fun unregisterSubscriber(subscriber: IProviderSubscriber): Boolean

    /**
     * Установить подписчика текущим
     *
     * @param subscriber подписчик
     * @return флаг - операция завершена успешно
     */
    fun setCurrentSubscriber(subscriber: IProviderSubscriber): Boolean

    /**
     * Остановитить работу service locator
     */
    fun stop()

    /**
     * Запустить работу service locator
     */
    fun start()

    /**
     * Получить список провайдеров
     *
     * @return список провайдеров
     */
    fun getProviders(): List<IProvider>

    /**
     * Получить фабрику провайдеров
     *
     * @return фабрика провайдеров
     */
    fun getProviderFactory(): IProviderFactory

}
