package ru.nextleap.fox_in_box.provider

import ru.nextleap.fox_in_box.action.ImageAction
import ru.nextleap.sl.IProvider


interface INetImageProvider : IProvider {
    /**
     * Скачать image по его url
     *
     * @param action - событие для скачивания image
     */
    fun downloadImage(subscriber: String, action: ImageAction)

    /**
     * очистить кеш
     *
     */
    fun clearCache()

}