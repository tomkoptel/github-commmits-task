package com.olderwold.jlabs.github

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

// https://stackoverflow.com/a/67135899
fun interval(delay: Long, unit: TimeUnit): Flow<LocalDateTime> {
    val timer = (0..Int.MAX_VALUE)
        .asSequence()
        .asFlow()
        .onEach { delay(unit.toMillis(delay)) }

    return timer.map { LocalDateTime.now() }
}
