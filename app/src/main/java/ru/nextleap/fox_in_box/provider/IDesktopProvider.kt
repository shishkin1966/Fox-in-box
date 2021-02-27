package ru.nextleap.fox_in_box.provider

import ru.nextleap.sl.IProvider

interface IDesktopProvider : IProvider {
    /**
     * Получить layout id.
     *
     * @param name      имя layout
     * @param defaultId default layout id
     * @return layout id
     */
    fun getLayoutId(name: String, defaultId: Int): Int

    /**
     * Получить color id.
     *
     * @param name      имя color
     * @param defaultId default color id
     * @return color id
     */
    fun getColorId(name: String, defaultId: Int): Int

    /**
     * Получить style id.
     *
     * @param name      имя style
     * @param defaultId default style id
     * @return style id
     */
    fun getStyleId(name: String, defaultId: Int): Int

    /**
     * Получить menu id.
     *
     * @param name      имя menu
     * @param defaultId default menu id
     * @return menu id
     */
    fun getMenuId(name: String, defaultId: Int): Int

    /**
     * Получить ресурс id.
     *
     * @param name      имя ресурса
     * @param type      тип ресурса
     * @param defaultId id ресурса по умолчанию
     * @return id ресурса
     */
    fun getResourceId(name: String, type: String, defaultId: Int): Int

    /**
     * Установить рабочий стол
     *
     * @param desktop имя рабочего стола
     */
    fun setDesktop(desktop: String)

    /**
     * Получить рабочий стол
     *
     * @return имя рабочего стола
     */
    fun getDesktop(): String

    fun getItems(): List<String>

    fun setItems(items: List<String>)

}