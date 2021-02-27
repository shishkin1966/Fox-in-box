package ru.nextleap.fox_in_box.screen.aggregated

import ru.nextleap.sl.model.AbsPresenterModel


class AggregatedModel(view: AggregatedFragment) : AbsPresenterModel(view) {
    init {
        setPresenter(AggregatedPresenter(this))
    }
}
