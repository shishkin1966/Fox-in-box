package ru.nextleap.sl.provider

import ru.nextleap.sl.IUnion
import ru.nextleap.sl.ui.IActivity

interface IActivityUnion : IUnion<IActivity> {
    /**
     * Получить фрагмент по его id.
     *
     * @param cls класс фрагмента
     * @param id  the id
     * @return фрагмент
    </F> */
    fun <F> getFragment(cls: Class<F>, id: Int): F?

    /**
     * Получить текущий фрагмент
     *
     * @return фрагмент
    </F> */
    fun <F> getCurrentFragment(): F?

    /**
     * Переключиться на фрагмент
     *
     * @param name имя фрагмента
     */
    fun switchToFragment(name: String)

    /**
     * Переключиться на top фрагмент
     */
    fun switchToTopFragment()

    /**
     * Перейти по BackPress
     */
    fun back()

    /**
     * Получить Activity
     *
     * @return the Activity
     */
    fun <C> getActivity(): C?

    /**
     * Получить Activity
     *
     * @param name имя activity
     * @return the Activity
     */
    fun <C> getActivity(name: String): C?

    /**
     * Получить Activity
     *
     * @param name     имя activity
     * @param validate флаг - проверять activity на валидность
     * @return the Activity
     */
    fun <C> getActivity(name: String, validate: Boolean): C?

    /**
     * Проверить наличие записей в BackStack
     *
     * @return true - записей нет
     */
    fun isBackStackEmpty(): Boolean

    /**
     * Получить кол-во записей в BackStack
     *
     * @return кол-во записей
     */
    fun getBackStackEntryCount(): Int

    /**
     * Проверить наличие Top фрагмента
     *
     * @return true - Top фрагмент есть
     */
    fun hasTop(): Boolean

    /**
     * Запросить право приложению
     *
     * @param permission право
     */
    fun grantPermission(permission: String)

    /**
     * Запросить право приложению
     *
     * @param listener    имя слушателя
     * @param permission  право
     * @param helpMessage сообщение если право запрещено спрашивать
     */
    fun grantPermission(listener: String, permission: String, helpMessage: String)


    /**
     * Получить роутер
     *
     * @return роутер
     */
    fun getRouter(): IRouterProvider?
}
