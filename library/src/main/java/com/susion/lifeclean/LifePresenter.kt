package com.susion.lifeclean

import androidx.appcompat.app.AppCompatActivity
import java.lang.ref.WeakReference
import kotlin.reflect.KClass

/**
 * susionwang at 2019-12-10
 * 带有生命周期组件的Presenter, 使用[LifeClean]来创建
 */
abstract class LifePresenter : Presenter {

    private var lifeOwnerReference = WeakReference<AppCompatActivity>(null)

    fun getLifeOwner() = lifeOwnerReference.get()

    fun getContext() =  lifeOwnerReference.get()

    fun injectLifeOwner(lifecycleOwner: AppCompatActivity) {
        lifeOwnerReference = WeakReference(lifecycleOwner)
    }

}