package ru.nextleap.fox_in_box.provider

import androidx.work.*
import ru.nextleap.sl.AbsProvider
import ru.nextleap.sl.IProvider
import ru.nextleap.sl.provider.ApplicationProvider
import java.util.concurrent.TimeUnit

class JobProvider : AbsProvider(), IJobProvider {
    companion object {
        const val NAME = "JobProvider"
    }

    override fun <T : ListenableWorker> runOnce(workerClass: Class<T>) {
        val request: WorkRequest =
            OneTimeWorkRequest.from(workerClass)
        run(request)
    }

    override fun <T : ListenableWorker> runOnce(workerClass: Class<T>, data: Data) {
        val request: WorkRequest =
            OneTimeWorkRequest.Builder(workerClass)
                .setInputData(data)
                .build()
        run(request)
    }

    override fun <T : ListenableWorker> runOnce(workerClass: Class<T>, constraints: Constraints) {
        val request: WorkRequest =
            OneTimeWorkRequest.Builder(workerClass)
                .setConstraints(constraints)
                .build()
        run(request)
    }

    override fun <T : ListenableWorker> runOnce(
        workerClass: Class<T>,
        data: Data,
        constraints: Constraints
    ) {
        val request: WorkRequest =
            OneTimeWorkRequest.Builder(workerClass)
                .setInputData(data)
                .setConstraints(constraints)
                .build()
        run(request)
    }

    override fun <T : ListenableWorker> runPeriodic(
        workerClass: Class<T>,
        data: Data,
        constraints: Constraints,
        repeatInterval: Long,
        repeatIntervalTimeUnit: TimeUnit
    ) {
        val request: WorkRequest =
            PeriodicWorkRequest.Builder(workerClass, repeatInterval, repeatIntervalTimeUnit)
                .setInputData(data)
                .setConstraints(constraints)
                .build()
        run(request)
    }

    override fun <T : ListenableWorker> runPeriodic(
        workerClass: Class<T>,
        data: Data,
        constraints: Constraints,
        repeatInterval: Long,
        repeatIntervalTimeUnit: TimeUnit,
        flexInterval: Long,
        flexIntervalTimeUnit: TimeUnit
    ) {
        val request: WorkRequest =
            PeriodicWorkRequest.Builder(
                workerClass,
                repeatInterval,
                repeatIntervalTimeUnit,
                flexInterval,
                flexIntervalTimeUnit
            )
                .setInputData(data)
                .setConstraints(constraints)
                .build()
        run(request)
    }

    override fun getName(): String {
        return NAME
    }

    override fun compareTo(other: IProvider): Int {
        return if (other is IJobProvider) 0 else 1
    }

    override fun run(request: WorkRequest) {
        WorkManager
            .getInstance(ApplicationProvider.appContext)
            .enqueue(request)
    }


}