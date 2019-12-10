package com.susion.lifeclean

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.susion.lifeclean.core.LifeAndroidViewModel
import com.susion.lifeclean.core.LifePage
import com.susion.lifeclean.core.LifePresenter
import com.susion.lifeclean.core.LifeViewModel

/**
 * susionwang at 2019-12-10C
 * 提供基于[AppCompatActivity]的 Lifecycle Component
 */
object LifeClean {

    /**
     * 实例化的类必须继承自 : [LifeViewModel] or [LifeAndroidViewModel]
     * */
    inline fun <reified T : ViewModel> createViewModel(activity: AppCompatActivity): T {
        return ViewModelProviders.of(activity, ViewModelFactory(activity)).get(T::class.java)
    }

    /**
     * 实例化的类必须继承自[LifePage]  && 含有 construct(context) 的构造函数
     * */
    inline fun <reified T : LifePage> createPage(activity: AppCompatActivity): T {
        return PageFactory(activity).create(T::class.java)
    }

    /**
     * 实例化的类必须继承自[LifePresenter]  && 含有 construct(context) 的构造函数
     * */
    inline fun <reified T : LifePresenter, reified P : LifePage> createPresenter(
        activity: AppCompatActivity,
        lifePage: P
    ): T {
        return LifeClean.PresenterFactory(activity).create(T::class.java, lifePage)
    }

    /**
     * 创建可以感知生命周期的ViewModel
     * */
    class ViewModelFactory(val activity: AppCompatActivity) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {

            if (LifeAndroidViewModel::class.java.isAssignableFrom(modelClass)) {
                val obj =
                    modelClass.getConstructor(Application::class.java)
                        .newInstance(activity.application)
                (obj as LifeAndroidViewModel).injectLifeOwner(activity)
                return obj
            }

            if (LifeViewModel::class.java.isAssignableFrom(modelClass)) {
                val obj = modelClass.getConstructor().newInstance()
                (obj as LifeViewModel).injectLifeOwner(activity)
                return obj
            }

            throw IllegalArgumentException("View Model Must Is Child of LifeAndroidViewModel")
        }
    }

    /**
     * 构造一个LifePage, 可以感知Activity的生命周期
     * 必须含有一个构造函数 : construct(context)
     * */
    class PageFactory(val activity: AppCompatActivity) {

        fun <T : LifePage?> create(pageClass: Class<T>): T {
            if (LifePage::class.java.isAssignableFrom(pageClass)) {
                val obj = pageClass.getConstructor(Context::class.java).newInstance(activity)
                activity.lifecycle.addObserver(obj as LifePage)
                return obj
            }

            throw IllegalArgumentException("Page Must Is Child of LifePage")
        }
    }

    class PresenterFactory(val activity: AppCompatActivity) {
        inline fun <reified T : LifePresenter?, reified P> create(
            presenterClass: Class<T>,
            lifePage: P
        ): T {
            if (LifePresenter::class.java.isAssignableFrom(presenterClass)) {
                val obj =
                    presenterClass.getConstructor(P::class.java).newInstance(lifePage)
                (obj as LifePresenter).injectLifeOwner(activity)
                return obj
            }
            throw IllegalArgumentException("Page Must Is Child of LifePage")
        }
    }

}