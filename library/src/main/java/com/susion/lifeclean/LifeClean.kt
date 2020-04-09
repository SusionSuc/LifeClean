package com.susion.lifeclean

import android.app.Application
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

/**
 * susionwang at 2019-12-10
 *
 * 提供基于[AppCompatActivity]的 Lifecycle Component
 */
object LifeClean {

    /**
     * 实例化ViewModel
     * */
    inline fun <reified T : ViewModel> createViewModel(activity: AppCompatActivity): T {
        return ViewModelProviders.of(activity).get(T::class.java)
    }

    /**
     * 实例化的类必须继承自 : [LifeViewModel] or [LifeAndroidViewModel]
     * */
    inline fun <reified T : ViewModel> createLifeViewModel(activity: AppCompatActivity): T {
        return ViewModelProviders.of(
            activity,
            ViewModelFactory(activity)
        ).get(T::class.java)
    }

    /**
     * 实例化的类必须继承自[LifePage]  && 含有 construct(context) 的构造函数
     * */
    inline fun <reified T : LifePage> createPage(activity: AppCompatActivity): T {
        return PageFactory(activity).create(T::class.java)
    }

    /**
     *实例化[LifePresenter], 必须串如LifeCycle -> AppCompatActivity 。如果不需要生命周期的话可以直接继承自 [Presenter],使用普通的构造方法
     * */
    inline fun <reified T : LifePresenter> createPresenter(activity: AppCompatActivity): T {
        return PresenterFactory(activity).create(T::class.java)
    }

    /**
     * 实例化带有一个任意类型参数的[LifePresenter]
     * */
    inline fun <reified T : LifePresenter, reified P : Any> createPresenter(
        activity: AppCompatActivity,
        params1: P
    ): T {
        return PresenterFactory(activity)
            .create(T::class.java, params1)
    }

    /**
     * 实例化带有两个任意类型参数的[LifePresenter]
     * */
    inline fun <reified T : LifePresenter, reified P1 : Any, reified P2 : Any> createPresenter(
        activity: AppCompatActivity,
        params1: P1,
        params2: P2
    ): T {
        return PresenterFactory(activity)
            .create(T::class.java, params1, params2)
    }

    /**
     * 实例化带有三个任意类型参数的[LifePresenter]
     * */
    inline fun <reified T : LifePresenter, reified P1 : Any, reified P2 : Any, reified P3 : Any> createPresenter(
        activity: AppCompatActivity,
        params1: P1,
        params2: P2,
        params3: P3
    ): T {
        return PresenterFactory(activity)
            .create(T::class.java, params1, params2, params3)
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
                val obj =
                    pageClass.getConstructor(AppCompatActivity::class.java).newInstance(activity)
                activity.lifecycle.addObserver(obj as LifePage)
                if (obj is View) {
                    obj.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
                        override fun onViewDetachedFromWindow(v: View?) {
                            activity.lifecycle.removeObserver(obj as LifePage)
                        }

                        override fun onViewAttachedToWindow(v: View?) {
                            activity.lifecycle.addObserver(obj as LifePage)
                        }
                    })
                }
                return obj
            }

            throw IllegalArgumentException("Page Must Is Child of LifePage")
        }
    }

    /**
     * 构造Presenter
     * */
    class PresenterFactory(val activity: AppCompatActivity) {

        inline fun <reified T : LifePresenter> create(presenterClass: Class<T>): T {
            if (LifePresenter::class.java.isAssignableFrom(presenterClass)) {
                val obj = presenterClass.getConstructor().newInstance()
                (obj as LifePresenter).injectLifeOwner(activity)
                return obj
            }
            throw IllegalArgumentException("Page Must Is Child of LifePage")
        }


        inline fun <reified T : LifePresenter, reified P : Any> create(
            presenterClass: Class<T>,
            param: P
        ): T {
            if (LifePresenter::class.java.isAssignableFrom(presenterClass)) {
                val obj = presenterClass.getConstructor(P::class.java).newInstance(param)
                (obj as LifePresenter).injectLifeOwner(activity)
                return obj
            }
            throw IllegalArgumentException("Page Must Is Child of LifePage")
        }

        inline fun <reified T : LifePresenter, reified P1 : Any, reified P2 : Any> create(
            presenterClass: Class<T>,
            param1: P1,
            param2: P2
        ): T {
            if (LifePresenter::class.java.isAssignableFrom(presenterClass)) {
                val obj = presenterClass.getConstructor(P1::class.java, P2::class.java)
                    .newInstance(param1, param2)
                (obj as LifePresenter).injectLifeOwner(activity)
                return obj
            }
            throw IllegalArgumentException("Page Must Is Child of LifePage")
        }

        inline fun <reified T : LifePresenter, reified P1 : Any, reified P2 : Any, reified P3 : Any> create(
            presenterClass: Class<T>,
            param1: P1,
            param2: P2,
            param3: P3
        ): T {
            if (LifePresenter::class.java.isAssignableFrom(presenterClass)) {
                val obj =
                    presenterClass.getConstructor(P1::class.java, P2::class.java, P3::class.java)
                        .newInstance(param1, param2, param3)
                (obj as LifePresenter).injectLifeOwner(activity)
                return obj
            }
            throw IllegalArgumentException("Page Must Is Child of LifePage")
        }
    }

}