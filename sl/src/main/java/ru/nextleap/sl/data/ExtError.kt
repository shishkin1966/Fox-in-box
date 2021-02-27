package ru.nextleap.sl.data

class ExtError {
    private val errorText = StringBuilder()
    private var sender: String? = null

    fun getErrorText(): String? {
        return if (errorText.isEmpty()) {
            null
        } else errorText.toString()
    }

    fun addError(sender: String, error: String?): ExtError {
        this.sender = sender
        addError(error)
        return this
    }

    private fun addError(error: String?) {
        if (error == null) return

        if (!error.isNullOrEmpty()) {
            if (errorText.isNotEmpty()) {
                errorText.append("\n")
            }
            errorText.append(error)
        }
    }

    fun addError(sender: String, e: Exception?): ExtError {
        if (e != null) {
            this.sender = sender
            addError(e.message)
        }
        return this
    }

    fun hasError(): Boolean {
        return errorText.isNotEmpty()
    }

    fun getSender(): String? {
        return sender
    }

    fun setSender(sender: String): ExtError {
        this.sender = sender
        return this
    }

}
