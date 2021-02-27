package ru.nextleap.sl

/**
 * Интерфейс Фабрики поставщиков услуг
 */
interface IProviderFactory {
    fun create(name: String): IProvider?
}
