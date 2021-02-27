package ru.nextleap.sl.task

import java.util.concurrent.ThreadFactory

class JobThreadFactory : ThreadFactory {
    private var counter = 0

    override fun newThread(runnable: Runnable): Thread {
        return Thread(runnable, "Thread_" + counter++)
    }
}
