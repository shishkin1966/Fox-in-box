package ru.nextleap.sl.ui

import android.os.Bundle
import ru.nextleap.sl.model.IModel

@Suppress("UNCHECKED_CAST")
abstract class AbsModelFragment : AbsFragment(), IModelFragment {
    private lateinit var model: IModel

    override fun <T : IModel> getModel(): T {
        if (!::model.isInitialized) {
            model = createModel()
        }
        return model as T
    }

    override fun <T : IModel> setModel(model: T) {
        this.model = model
    }

    abstract fun createModel(): IModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setModel(createModel())
    }

}
