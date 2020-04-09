package com.susion.lifeclean

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import java.lang.ref.WeakReference

/**
 * susionwang at 2019-12-10
 * 带有生命周期组件的ViewModel, 使用[LifeClean]来创建
 */
open class LifeViewModel : ViewModel() {

    private var lifeOwnerReference = WeakReference<AppCompatActivity>(null)

    fun getLifeOwner() = lifeOwnerReference.get()

    fun injectLifeOwner(lifecycleOwner: AppCompatActivity) {
        lifeOwnerReference = WeakReference(lifecycleOwner)
    }

}

