package ru.nextleap.sl.observe

import android.database.ContentObserver
import android.os.Handler


class BaseContentObserver(private val observable: IObservable) : ContentObserver(Handler()) {

    override fun onChange(selfChange: Boolean) {
        super.onChange(selfChange)

        observable.onChange(selfChange)
    }

}
