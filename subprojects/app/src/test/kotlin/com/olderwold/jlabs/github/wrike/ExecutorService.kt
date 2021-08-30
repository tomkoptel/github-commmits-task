package com.olderwold.jlabs.github.wrike

import java.util.concurrent.ExecutorService
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit


/**
 * @see [Origin](https://stackoverflow.com/questions/6581188/is-there-an-executorservice-that-uses-the-current-thread)
 */
fun currentThreadExecutorService(): ExecutorService {
    val callerRunsPolicy = ThreadPoolExecutor.CallerRunsPolicy()
    return object : ThreadPoolExecutor(
        0,
        1,
        0L,
        TimeUnit.SECONDS,
        SynchronousQueue(),
        callerRunsPolicy
    ) {
        override fun execute(command: Runnable?) {
            callerRunsPolicy.rejectedExecution(command, this)
        }
    }
}
