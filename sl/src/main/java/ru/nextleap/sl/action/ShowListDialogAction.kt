package ru.nextleap.sl.action

import java.util.*

/**
 * Событие - выполнить команду "показать List диалог"
 */
class ShowListDialogAction : ShowDialogAction {

    private var list: List<String>? = null
    private var selected: Array<Int>? = null
    private var multiSelect = false

    constructor(id: Int, listener: String) : super(id, listener) {}

    constructor(
        id: Int = -1,
        listener: String,
        title: String? = null,
        message: String? = null,
        list: List<String>?,
        selected: Array<Int>? = null,
        multiSelect: Boolean = false,
        buttonPositive: Int = -1,
        buttonNegative: Int = -1,
        setCancelable: Boolean = true
    ) : super(id, listener, title, message) {

        this.list = list
        this.selected = selected
        this.multiSelect = multiSelect

        setPositiveButton(buttonPositive)
        setNegativeButton(buttonNegative)
        setCancelable(setCancelable)
    }

    constructor(
        id: Int = -1,
        listener: String,
        title: String? = null,
        message: String? = null,
        list: List<String>,
        buttonPositive: Int = -1,
        buttonNegative: Int = -1,
        setCancelable: Boolean = true
    ) : super(id, listener, title, message) {

        this.list = list
        multiSelect = false

        setPositiveButton(buttonPositive)
        setNegativeButton(buttonNegative)
        setCancelable(setCancelable)
    }

    fun getList(): List<String>? {
        return list
    }

    fun getSelected(): Array<Int>? {
        return selected
    }

    fun isMultiSelect(): Boolean {
        return multiSelect
    }

    fun setList(list: ArrayList<String>): ShowListDialogAction {
        this.list = list
        return this
    }

    fun setSelected(selected: Array<Int>): ShowListDialogAction {
        this.selected = selected
        return this
    }

    fun setMultiSelect(multiselect: Boolean): ShowListDialogAction {
        multiSelect = multiselect
        return this
    }
}
