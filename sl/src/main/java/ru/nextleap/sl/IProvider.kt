package ru.nextleap.sl

/**
 * Интерфейс провайдера - объекта предоставлющий сервис
 */
interface IProvider : INamed, IValidated, Comparable<IProvider> {
    /**
     * Получить тип провайдера
     *
     * @return true - не будет удаляться администратором
     */
    fun isPersistent(): Boolean

    /**
     * Событие - отключить регистрацию
     */
    fun onUnRegister()

    /**
     * Событие - регистрация
     */
    fun onRegister()

    /**
     * Остановитить работу провайдера
     */
    fun stop()
}
