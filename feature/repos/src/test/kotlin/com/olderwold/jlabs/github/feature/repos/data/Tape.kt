package com.olderwold.jlabs.github.feature.repos.data

import okreplay.OkReplayConfig
import okreplay.OkReplayInterceptor
import okreplay.RecorderRule
import okreplay.TapeMode
import org.junit.rules.TestRule

internal fun <Api : Any> tape(
    builder: OkReplayConfig.Builder.() -> Unit = {},
    api: (OkReplayInterceptor) -> Api,
): TapeRule<Api> {
    val okReplayInterceptor = OkReplayInterceptor()
    val configuration = OkReplayConfig.Builder()
        .defaultMode(TapeMode.READ_ONLY)
        .sslEnabled(true)
        .interceptor(okReplayInterceptor)
        .apply(builder)
        .build()

    return TapeRule(
        delegate = RecorderRule(configuration),
        api = api(okReplayInterceptor)
    )
}

internal class TapeRule<Api : Any>(
    private val delegate: TestRule,
    val api: Api,
) : TestRule by delegate
