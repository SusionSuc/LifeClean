package com.susion.lifeclean

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import java.lang.ref.WeakReference
import kotlin.reflect.KClass

/**
 * susionwang at 2019-12-10
 * 带有生命周期组件的Presenter, 使用[LifeClean]来创建
 */
abstract class LifePresenter : Presenter, LifecycleObserver {

    private var lifeOwnerReference = WeakReference<AppCompatActivity>(null)

    fun getLifeOwner() = lifeOwnerReference.get()

    fun injectLifeOwner(lifecycleOwner: AppCompatActivity) {
        lifeOwnerReference = WeakReference(lifecycleOwner)
        lifecycleOwner.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    open fun onActivityCreate() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    open fun onActivityStart() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    open fun onActivityResume() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    open fun onActivityPause() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    open fun onActivityStop() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    open fun onActivityDestroy() {

    }

}