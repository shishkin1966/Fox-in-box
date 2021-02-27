package ru.nextleap.sl.lifecycle

/**
 * Интерфейс слушателя View объекта, имеющего жизненный цикл
 */
interface ILifecycleListener : ILifecycle {
    /**
     * Событие - view на этапе создания
     */
    fun onCreateView()

    /**
     * Событие - view готово к использованию
     */
    fun onReadyView()

    /**
     * Событие - уничтожение view
     */
    fun onDestroyView()

}
