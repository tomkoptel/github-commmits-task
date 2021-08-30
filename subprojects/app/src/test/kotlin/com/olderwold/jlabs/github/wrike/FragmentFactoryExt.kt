package com.olderwold.jlabs.github.wrike

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import io.mockk.every
import io.mockk.spyk

fun fragmentFactory(configure: InterceptViewModelProviderFactory.() -> Unit) =
    object : FragmentFactory() {
        override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
            val fragment = super.instantiate(classLoader, className)
            val interceptViewModelProviderFactory = InterceptViewModelProviderFactory().apply(configure)

            return spyk(fragment) {
                every { defaultViewModelProviderFactory } answers {
                    val originalFactory = it.invocation.originalCall() as ViewModelProvider.Factory
                    interceptViewModelProviderFactory.delegate = originalFactory
                    interceptViewModelProviderFactory
                }
            }
        }
    }
