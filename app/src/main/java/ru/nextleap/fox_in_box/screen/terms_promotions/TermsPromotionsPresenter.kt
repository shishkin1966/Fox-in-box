package ru.nextleap.fox_in_box.screen.terms_promotions

import ru.nextleap.sl.presenter.AbsModelPresenter

class TermsPromotionsPresenter(model: TermsPromotionsModel) : AbsModelPresenter(model) {

    companion object {
        const val NAME = "TermsPromotionsPresenter"
    }

    override fun isRegister(): Boolean {
        return false
    }

    override fun getName(): String {
        return NAME
    }

}
