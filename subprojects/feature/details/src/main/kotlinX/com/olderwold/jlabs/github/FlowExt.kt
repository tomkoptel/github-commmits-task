package com.olderwold.jlabs.github

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.TimeUnit

/**
 * Emits immediately and the delays by interval.
 * @see [How to implement timer with Kotlin coroutines?](https://stackoverflow.com/a/54828055)
 */
fun emitThenDelay(delay: Long, unit: TimeUnit): Flow<Unit> {
    return flow {
        while (true) {
            emit(Unit)
            delay(unit.toMillis(delay))
        }
    }
}
