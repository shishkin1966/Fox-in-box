package ru.nextleap.sl.request

import android.annotation.SuppressLint
import io.reactivex.Single
import ru.nextleap.sl.data.ExtError
import ru.nextleap.sl.data.ExtResult
import ru.nextleap.sl.message.ResultMessage
import ru.nextleap.sl.provider.ApplicationProvider
import ru.nextleap.sl.provider.IMessengerUnion
import ru.nextleap.sl.provider.MessengerUnion

abstract class AbsNetResultMessageRequest(owner: String) : AbsResultMessageRequest(owner) {

    @SuppressLint("CheckResult")
    override fun run() {
        if (!isValid()) return
        val union = ApplicationProvider.serviceLocator?.get<IMessengerUnion>(
            MessengerUnion.NAME
        ) ?: return

        val single = getData() as Single<*>
        single
            .map { t: Any -> ExtResult(t) }
            .subscribe(
                { result: ExtResult ->
                    if (isValid() && result.getData() != null) {
                        union.addNotMandatoryMessage(
                            ResultMessage(
                                getOwner(),
                                result.setName(getName())
                            )
                                .setSubj(getName())
                                .setCopyTo(getCopyTo())
                        )
                    }
                }, { throwable: Throwable ->
                    if (isValid()) {
                        val result = ExtResult().setError(
                            ExtError().addError(
                                getName(),
                                throwable.localizedMessage
                            )
                        ).setName(getName())
                        union.addNotMandatoryMessage(
                            ResultMessage(getOwner(), result)
                                .setSubj(getName())
                                .setCopyTo(getCopyTo())
                        )
                    }
                }
            )
    }

}
