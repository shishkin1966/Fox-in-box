package ru.nextleap.sl.data


class ExtResult() {
    companion object {
        const val NOT_SEND = -1
        const val LAST = -2
    }

    private var data: Any? = null
    private var error: ExtError? = null
    private var order = NOT_SEND
    private var name: String? = null
    private var id = 0

    constructor(data: Any?) : this() {
        this.data = data
    }

    fun getData(): Any? {
        return data
    }

    fun setData(data: Any?): ExtResult {
        this.data = data
        return this;
    }

    fun getError(): ExtError? {
        return error
    }

    fun setError(error: ExtError?): ExtResult {
        this.error = error
        return this;
    }

    fun setError(sender: String, error: String): ExtResult {
        if (this.error == null) {
            this.error = ExtError()
        }
        this.error?.addError(sender, error)
        return this
    }

    fun setError(sender: String, e: Exception): ExtResult {
        if (error == null) {
            error = ExtError()
        }
        error?.addError(sender, e)
        return this
    }

    fun setError(sender: String, t: Throwable): ExtResult {
        if (error == null) {
            error = ExtError()
        }
        error?.addError(sender, t.message)
        return this
    }

    fun getErrorText(): String? {
        return if (error != null) {
            error?.getErrorText()
        } else null
    }

    fun getSender(): String? {
        return if (error != null) {
            error?.getSender()
        } else null
    }

    fun validate(): Boolean {
        return if (error == null) {
            true
        } else !error!!.hasError()
    }

    fun isEmpty(): Boolean {
        return data == null
    }

    fun getOrder(): Int {
        return order
    }

    fun setOrder(order: Int): ExtResult {
        this.order = order
        return this
    }

    fun hasError(): Boolean {
        return if (error != null) {
            error!!.hasError()
        } else false
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String): ExtResult {
        this.name = name
        return this
    }

    fun getId(): Int {
        return id
    }

    fun setId(id: Int): ExtResult {
        this.id = id
        return this
    }
}
