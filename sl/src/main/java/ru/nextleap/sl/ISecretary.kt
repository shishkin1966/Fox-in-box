package ru.nextleap.sl

/**
 * Интерфейс секретаря объединиения
 */
interface ISecretary<T> {
    /**
     * удалить члена объединения
     *
     * @param key - имя члена объединения
     */
    fun remove(key: String): T?

    /**
     * получить размер объединения
     *
     * @return - получить размер объединения
     */
    fun size(): Int

    /**
     * добавить члена объединения
     *
     * @param key - имя члена объединения
     * @param value - член объединения
     */
    fun put(key: String, value: T): T?

    /**
     * проверить наличие члена объединения
     *
     * @param key - имя члена объединения
     */
    fun containsKey(key: String): Boolean

    /**
     * получить члена объединения
     *
     * @param key - имя члена объединения
     */
    fun get(key: String): T?

    /**
     * получить список членов объединения
     */
    fun values(): List<T>

    /**
     * проверить объединение на наличие членов
     *
     * @return true - объединение не пустое
     */
    fun isEmpty(): Boolean

    /**
     * очистить объединение
     */
    fun clear()

    /**
     * получить список имен членов объединения
     * @return список имен
     */
    fun keys(): Collection<String>

}
