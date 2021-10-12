package ru.nextleap.fox_in_box.provider

import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ListenableWorker
import androidx.work.WorkRequest
import ru.nextleap.sl.IProvider
import java.util.concurrent.TimeUnit

interface IJobProvider: IProvider {
    fun run(request: WorkRequest)

    fun <T:ListenableWorker> runOnce(workerClass: Class<T>)

    fun <T:ListenableWorker> runOnce(workerClass: Class<T>, data: Data)

    fun <T:ListenableWorker> runOnce(workerClass: Class<T>, constraints: Constraints)

    fun <T:ListenableWorker> runOnce(workerClass: Class<T>, data: Data, constraints: Constraints)

    fun <T : ListenableWorker> runPeriodic(workerClass: Class<T>,
                                           data: Data,
                                           constraints: Constraints,
                                           repeatInterval:Long,
                                           repeatIntervalTimeUnit: TimeUnit)

    fun <T : ListenableWorker> runPeriodic(workerClass: Class<T>,
                                           data: Data,
                                           constraints: Constraints,
                                           repeatInterval:Long,
                                           repeatIntervalTimeUnit:TimeUnit,
                                           flexInterval:Long,
                                           flexIntervalTimeUnit:TimeUnit)
}