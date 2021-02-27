package ru.nextleap.sl.request

import ru.nextleap.sl.task.RequestThreadPoolExecutor


abstract class AbsRequest : IRequest {
    private var rank: Int = Rank.MIDDLE_RANK
    private var isCanceled = false
    private var id = 0

    override fun getRank(): Int {
        return rank
    }

    override fun setRank(rank: Int): IRequest {
        this.rank = rank
        return this
    }


    override fun isCancelled(): Boolean {
        return isCanceled
    }

    override fun setCanceled() {
        isCanceled = true
    }

    override fun isValid(): Boolean {
        return !isCancelled()
    }

    override fun getId(): Int {
        return id
    }

    override fun setId(id: Int): IRequest {
        this.id = id
        return this
    }

    override operator fun compareTo(other: IRequest): Int {
        return other.getRank() - getRank()
    }

    override fun getAction(oldRequest: IRequest): Int {
        return RequestThreadPoolExecutor.ACTION_DELETE
    }

    override fun isDistinct(): Boolean {
        return false
    }

    override fun getName(): String {
        return this::class.java.simpleName
    }
}
