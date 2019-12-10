package com.susion.lifeclean.core

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import java.lang.ref.WeakReference

/**
 * susionwang at 2019-12-10
 */
open class LifeViewModel : ViewModel() {
    private var lifeOwnerReference = WeakReference<LifecycleOwner>(null)

    fun getLifeOwner() = lifeOwnerReference.get()

    fun injectLifeOwner(lifecycleOwner: LifecycleOwner) {
        lifeOwnerReference = WeakReference(lifecycleOwner)
    }
}

