package com.olderwold.jlabs.github.wrike

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

infix fun ViewModelProvider.Factory.extends(
    factory: ViewModelProvider.Factory
): ViewModelProvider.Factory {
    val self = this
    return object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (self is InterceptViewModelProviderFactory) {
                if (self.contains(modelClass)) {
                    self.create(modelClass)
                } else {
                    factory.create(modelClass)
                }
            } else {
                factory.create(modelClass)
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class InterceptViewModelProviderFactory : ViewModelProvider.Factory {
    val overrides = mutableMapOf<Class<ViewModel>, () -> ViewModel>()
    lateinit var delegate: ViewModelProvider.Factory

    inline fun <reified T : ViewModel> overrideViewModel(
        crossinline viewModel: () -> T
    ) {
        val key = T::class.java as Class<ViewModel>
        overrides[key] = { viewModel() }
    }

    fun <T : ViewModel> contains(modelClass: Class<T>): Boolean {
        return overrides.contains(modelClass as Class<ViewModel>)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val key = modelClass as Class<ViewModel>
        val overrideViewModel = overrides[key]?.invoke() as? T
        return overrideViewModel ?: delegate.create(modelClass) as T
    }
}
