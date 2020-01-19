package com.susion.lifeclean

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import java.lang.ref.WeakReference

/**
 * susionwang at 2019-12-10
 * 带有生命周期组件的ViewModel, 使用[LifeClean]来创建
 */
open class LifeAndroidViewModel(application: Application) : AndroidViewModel(application) {

    private var lifeOwnerReference = WeakReference<LifecycleOwner>(null)

    fun getLifeOwner() = lifeOwnerReference.get()

    fun injectLifeOwner(lifecycleOwner: LifecycleOwner) {
        lifeOwnerReference = WeakReference(lifecycleOwner)
    }
}