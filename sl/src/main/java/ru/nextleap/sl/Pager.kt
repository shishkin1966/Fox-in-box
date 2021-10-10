package ru.nextleap.sl

// Объект, реализующий динамическую постраничную выборку
open class Pager () {
    private var currentPageSize = 0 // текущий размер страницы
    var currentPosition = 0 // текущая позиция
    val nextPageSize: Int // следующий размер страницы
        get() {
            for (i in pageSize.indices) {
                if (pageSize[i] > currentPageSize) {
                    currentPageSize = pageSize[i]
                    return pageSize[i]
                }
            }
            return pageSize[pageSize.size - 1]
        }
    var pageSize: ArrayList<Int> = ArrayList() // массив размеров страниц
    var eof = false // индикатор достижения конца

    /**
     * Установить массив размеров страниц
     *
     * @param initialPageSize начальный размер страницы
     */
    open fun setPageSize(initialPageSize: Int) {
        if (initialPageSize > 0) {
            pageSize = ArrayList()
            pageSize.add(initialPageSize)
            pageSize.add(initialPageSize * 2)
            pageSize.add(initialPageSize * 4)
        }
    }

    /**
     * Инициализация объекта
     */
    fun init() {
        currentPosition = 0
        currentPageSize = 0
        eof = false
    }

    /**
     * Добавить к текущей позиции
     */
    fun add(count : Int) {
        currentPosition += count
        if (count < currentPageSize) {
            eof = true
        }
    }

}