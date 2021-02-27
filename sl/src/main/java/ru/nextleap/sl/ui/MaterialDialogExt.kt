package ru.nextleap.sl.ui


import android.content.Context
import android.os.Bundle
import com.afollestad.materialdialogs.DialogAction
import com.afollestad.materialdialogs.MaterialDialog
import ru.nextleap.common.ApplicationUtils
import ru.nextleap.sl.R
import ru.nextleap.sl.action.DialogResultAction
import ru.nextleap.sl.message.DialogResultMessage
import ru.nextleap.sl.provider.ApplicationProvider
import ru.nextleap.sl.provider.IMessengerUnion
import ru.nextleap.sl.provider.MessengerUnion
import java.util.*


class MaterialDialogExt {

    companion object {
        const val NO_BUTTON = -1
        const val ID = "id"
        const val BUTTON = "button"
        const val POSITIVE = "positive"
        const val NEGATIVE = "negative"
        const val NEUTRAL = "neutral"
    }

    private var id: Int = 0
    private val materialDialog: MaterialDialog
    private val listener: String

    constructor(
        context: Context,
        listener: String,
        id: Int,
        title: String? = null,
        message: String? = null,
        positiveButton: Int = NO_BUTTON,
        setCancelable: Boolean = false
    ) : this(
        context,
        listener,
        id,
        title,
        message,
        positiveButton,
        NO_BUTTON,
        NO_BUTTON,
        setCancelable
    )

    constructor(
        context: Context,
        listener: String,
        id: Int,
        title: String? = null,
        message: String? = null,
        positiveButton: Int = NO_BUTTON,
        negativeButton: Int = NO_BUTTON,
        setCancelable: Boolean = false
    ) : this(
        context,
        listener,
        id,
        title,
        message,
        positiveButton,
        negativeButton,
        NO_BUTTON,
        setCancelable
    )

    constructor(
        context: Context,
        listener: String,
        id: Int,
        title: String? = null,
        message: String? = null,
        positiveButton: Int = NO_BUTTON,
        negativeButton: Int = NO_BUTTON,
        neutralButton: Int = NO_BUTTON,
        setCancelable: Boolean = false
    ) {

        this.id = id
        this.listener = listener

        val builder = MaterialDialog.Builder(context)
        if (!title.isNullOrEmpty()) {
            builder.title(title)
        }
        if (!message.isNullOrEmpty()) {
            builder.content(message)
        }
        if (positiveButton != NO_BUTTON) {
            builder.positiveText(positiveButton)
            builder.onPositive { _, _ ->
                if (this.id > -1) {
                    val bundle = Bundle()
                    bundle.putInt(ID, this.id)
                    bundle.putString(BUTTON, POSITIVE)
                    if (this.listener.isNotEmpty()) {
                        ApplicationProvider.serviceLocator?.get<IMessengerUnion>(
                            MessengerUnion.NAME
                        )?.addNotMandatoryMessage(
                            DialogResultMessage(
                                this.listener,
                                DialogResultAction(bundle, this.id)
                            )
                        )
                    }
                }
            }
        }
        if (negativeButton != NO_BUTTON) {
            builder.negativeText(negativeButton)
            builder.onNegative { _, _ ->
                if (this.id > -1) {
                    val bundle = Bundle()
                    bundle.putInt(ID, this.id)
                    bundle.putString(BUTTON, NEGATIVE)
                    if (this.listener.isNotEmpty()) {
                        ApplicationProvider.serviceLocator?.get<IMessengerUnion>(
                            MessengerUnion.NAME
                        )?.addNotMandatoryMessage(
                            DialogResultMessage(
                                this.listener,
                                DialogResultAction(bundle, this.id)
                            )
                        )
                    }
                }
            }
        }
        if (neutralButton != NO_BUTTON) {
            builder.neutralText(neutralButton)
            builder.onNeutral { _, _ ->
                if (this.id > -1) {
                    val bundle = Bundle()
                    bundle.putInt(ID, this.id)
                    bundle.putString(BUTTON, NEUTRAL)
                    if (this.listener.isNotEmpty()) {
                        ApplicationProvider.serviceLocator?.get<IMessengerUnion>(
                            MessengerUnion.NAME
                        )?.addNotMandatoryMessage(
                            DialogResultMessage(
                                this.listener,
                                DialogResultAction(bundle, this.id)
                            )
                        )
                    }
                }
            }
        }
        builder.cancelable(setCancelable)

        materialDialog = builder.build()
    }

    constructor(
        context: Context,
        listener: String,
        id: Int,
        title: String? = null,
        message: String? = null,
        items: List<String>,
        selected: Array<Int>? = null,
        multiSelect: Boolean = false,
        positiveButton: Int = NO_BUTTON,
        negativeButton: Int = NO_BUTTON,
        setCancelable: Boolean = false
    ) {

        this.id = id
        this.listener = listener

        val builder = MaterialDialog.Builder(context)
        if (!title.isNullOrEmpty()) {
            builder.title(title)
        }
        if (!message.isNullOrEmpty()) {
            builder.content(message)
        }
        builder.items(items)
        if (multiSelect) {
            builder.itemsCallbackMultiChoice(null) { _, _, _ -> true }
        } else {
            builder.alwaysCallSingleChoiceCallback()
            builder.itemsCallbackSingleChoice(-1) { dialog, _, _, text ->
                if (this.id > -1) {
                    val bundle = Bundle()
                    bundle.putInt(ID, this.id)
                    bundle.putString(BUTTON, POSITIVE)
                    val list = ArrayList<String>()
                    list.add(text.toString())
                    bundle.putStringArrayList("list", list)
                    if (this.listener.isNotEmpty()) {
                        val union =
                            ApplicationProvider.serviceLocator?.get<IMessengerUnion>(
                                MessengerUnion.NAME
                            )
                        union?.addNotMandatoryMessage(
                            DialogResultMessage(
                                this.listener,
                                DialogResultAction(bundle, this.id)
                            )
                        )
                    }
                }
                dialog.dismiss()
                true
            }
        }
        if (multiSelect) {
            if (positiveButton != NO_BUTTON) {
                builder.positiveText(positiveButton)
            }
        }
        if (negativeButton != NO_BUTTON) {
            builder.negativeText(negativeButton)
        }
        if (multiSelect) {
            builder.onPositive { dialog, _ ->
                if (this.id > -1) {
                    val bundle = Bundle()
                    bundle.putInt("id", this.id)
                    bundle.putString(BUTTON, POSITIVE)
                    val list = ArrayList<String>()
                    val itemsCharSequence = dialog.items
                    val selected1 = dialog.selectedIndices
                    for (i in selected1!!) {
                        list.add(itemsCharSequence!![i!!].toString())
                    }
                    bundle.putStringArrayList("list", list)
                    if (this.listener.isNotEmpty()) {
                        ApplicationProvider.serviceLocator?.get<IMessengerUnion>(
                            MessengerUnion.NAME
                        )?.addNotMandatoryMessage(
                            DialogResultMessage(
                                this.listener,
                                DialogResultAction(bundle, this.id)
                            )
                        )
                    }
                }
            }
        }
        builder.onNegative { _, _ ->
            if (this.id > -1) {
                val bundle = Bundle()
                bundle.putInt(ID, this.id)
                bundle.putString(BUTTON, NEGATIVE)
                if (this.listener.isNotEmpty()) {
                    ApplicationProvider.serviceLocator?.get<IMessengerUnion>(
                        MessengerUnion.NAME
                    )
                        ?.addNotMandatoryMessage(
                            DialogResultMessage(
                                this.listener,
                                DialogResultAction(bundle, this.id)
                            )
                        )
                }
            }
        }
        builder.cancelable(setCancelable)

        materialDialog = builder.build()
        if (selected != null) {
            materialDialog?.setSelectedIndices(selected)
        }
    }

    constructor(
        context: Context,
        listener: String,
        id: Int,
        title: String? = null,
        message: String? = null,
        edittext: String,
        hint: String,
        inputType: Int,
        positiveButton: Int,
        negativeButton: Int,
        setCancelable: Boolean = false
    ) {

        this.id = id
        this.listener = listener

        val builder = MaterialDialog.Builder(context)
        if (!title.isNullOrEmpty()) {
            builder.title(title)
        }
        if (!message.isNullOrEmpty()) {
            builder.content(message)
        }
        builder.positiveText(positiveButton)
        if (negativeButton != NO_BUTTON) {
            builder.negativeText(negativeButton)
        }
        builder.inputType(inputType)
        builder.input(hint, edittext) { _, _ -> }
        builder.onPositive { dialog, _ ->
            if (this.id > -1) {
                val bundle = Bundle()
                bundle.putInt(ID, this.id)
                bundle.putString(BUTTON, POSITIVE)
                bundle.putString("object", dialog.inputEditText?.text.toString())
                if (this.listener.isNotEmpty()) {
                    ApplicationProvider.serviceLocator?.get<IMessengerUnion>(
                        MessengerUnion.NAME
                    )
                        ?.addNotMandatoryMessage(
                            DialogResultMessage(
                                this.listener,
                                DialogResultAction(bundle, this.id)
                            )
                        )
                }
            }
        }
        builder.onNegative { _, _ ->
            if (this.id > -1) {
                val bundle = Bundle()
                bundle.putInt(ID, this.id)
                bundle.putString(BUTTON, NEGATIVE)
                if (this.listener.isNotEmpty()) {
                    ApplicationProvider.serviceLocator?.get<IMessengerUnion>(
                        MessengerUnion.NAME
                    )
                        ?.addNotMandatoryMessage(
                            DialogResultMessage(
                                this.listener,
                                DialogResultAction(bundle, this.id)
                            )
                        )
                }
            }
        }
        builder.cancelable(setCancelable)

        materialDialog = builder.build()
    }

    fun show() {
        var size = ApplicationUtils.px2sp(
            materialDialog.context,
            materialDialog.context.resources.getDimension(R.dimen.text_size_large)
        )
        materialDialog.getActionButton(DialogAction.POSITIVE).textSize = size
        materialDialog.getActionButton(DialogAction.POSITIVE)
            .setTextColor(
                ApplicationUtils.getColor(
                    materialDialog.context,
                    R.color.blue
                )
            )
        materialDialog.getActionButton(DialogAction.NEGATIVE).textSize = size
        materialDialog.getActionButton(DialogAction.NEGATIVE)
            .setTextColor(
                ApplicationUtils.getColor(
                    materialDialog.context,
                    R.color.blue
                )
            )
        materialDialog.getActionButton(DialogAction.NEUTRAL).textSize = size
        materialDialog.getActionButton(DialogAction.NEUTRAL)
            .setTextColor(
                ApplicationUtils.getColor(
                    materialDialog.context,
                    R.color.blue
                )
            )
        materialDialog.contentView?.textSize = size
        size = ApplicationUtils.px2sp(
            materialDialog.context,
            materialDialog.context.resources.getDimension(R.dimen.text_size_xlarge)
        )
        materialDialog.titleView.textSize = size
        materialDialog.show()
    }

}
