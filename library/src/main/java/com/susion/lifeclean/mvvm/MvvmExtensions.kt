package com.susion.lifeclean.mvvm

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

/**
 * susionwang at 2019-12-09
 *
 * 1. MVVM 中不存在 Presenter
 * 2. ViewModel 负责加载数据、维护数据、通知UI更新。
 * 3. 接收LifeOwner, 负责释放网络请求资源
 */

open class LifeCycleViewModel(val lifeOwner: AppCompatActivity, application: Application) :
    AndroidViewModel(application)

/**
 * 创建可以感知生命周期的ViewModel
 * */
class AcLifeCycleViewModelFactory(val activity: AppCompatActivity) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (LifeCycleViewModel::class.java.isAssignableFrom(modelClass)) {
            return modelClass.getConstructor(AppCompatActivity::class.java, Application::class.java)
                .newInstance(activity, activity.application)
        }
        throw IllegalArgumentException("View Model Must Is Child of LifeCycleViewModel")
    }
}

/**
 * 快速创建 LifeCycleViewModel 的方法
 * */
inline fun <reified T : ViewModel> getAcViewModel(activity: AppCompatActivity): T {
    return ViewModelProviders.of(activity, AcLifeCycleViewModelFactory(activity)).get(T::class.java)
}





