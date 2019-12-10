package com.susion.lifeclean.core

import androidx.lifecycle.LifecycleOwner
import java.lang.ref.WeakReference

/**
 * susionwang at 2019-12-10
 */
abstract class LifePresenter(lifePage: LifePage?) : Presenter {

    private var lifeOwnerReference = WeakReference<LifecycleOwner>(null)

    fun getLifeOwner() = lifeOwnerReference.get()

    fun injectLifeOwner(lifecycleOwner: LifecycleOwner) {
        lifeOwnerReference = WeakReference(lifecycleOwner)
    }

    override fun <T : State> getStatus(): T? {
        return null
    }

}