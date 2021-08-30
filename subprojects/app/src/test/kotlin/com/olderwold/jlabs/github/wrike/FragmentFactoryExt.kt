package com.olderwold.jlabs.github.wrike

import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.SavedStateViewModelFactory
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

fun fragmentFactory(configure: InterceptViewModelProviderFactory.() -> Unit) =
    object : FragmentFactory() {
        override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
            val fragment = super.instantiate(classLoader, className)
            val interceptViewModelProviderFactory = InterceptViewModelProviderFactory {
                fragment.defaultViewModelFactory()
            }.apply(configure)
            val property = Fragment::class.memberProperties.find { it.name == "mDefaultFactory" }
            if (property is KMutableProperty<*>) {
                property.isAccessible = true
                property.setter.call(fragment, interceptViewModelProviderFactory)
            }
            return fragment
        }
    }

private fun Fragment.defaultViewModelFactory(): SavedStateViewModelFactory {
    var application: Application? = null
    var appContext: Context? = requireContext().applicationContext
    while (appContext is ContextWrapper) {
        if (appContext is Application) {
            application = appContext
            break
        }
        appContext = appContext.baseContext
    }
    val app = requireNotNull(application) {
        "We can not create view model factory without context. " +
                "Otherwise we won't be able to provide AndroidViewModel instances."
    }
    return SavedStateViewModelFactory(
        app,
        this,
        arguments
    )
}
