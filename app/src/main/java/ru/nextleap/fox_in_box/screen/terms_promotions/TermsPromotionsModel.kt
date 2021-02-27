package ru.nextleap.fox_in_box.screen.terms_promotions

import ru.nextleap.sl.model.AbsPresenterModel


class TermsPromotionsModel(view: TermsPromotionsFragment) : AbsPresenterModel(view) {
    init {
        setPresenter(TermsPromotionsPresenter(this))
    }
}
