package com.susion.lifeclean.mvvm

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.susion.lifeclean.api.GithubService
import com.susion.lifeclean.lifepage.PageStatus
import com.susion.lifeclean.rx.disposeOnDestroy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * create by susionwang at 2019-12-08
 */
class SimpleViewModel(activity: AppCompatActivity, application: Application) :
    LifeCycleViewModel(activity, application) {

    private val apiService = GithubService.create()
    val pageStatus = MutableLiveData<String>()
    val dataList = MutableLiveData<List<Any>>()

    fun loadData() {
        dataList.postValue(arrayListOf("1", "2", "3", "3秒后搜索github"))
    }

    private val IN_QUALIFIER = "in:name,description"

    fun searchRepos(
        query: String,
        page: Int,
        itemsPerPage: Int
    ) {
        dataList.postValue(emptyList()) //清空数据
        apiService.searchRepos(query + IN_QUALIFIER, page, itemsPerPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                pageStatus.value = PageStatus.START_LOAD_PAGE_DATA
            }.doOnTerminate {
                //doOnFinally : https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/doFinally.o.png
                //doOnTerminate : https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/doOnTerminate.o.png  -> 在onError之前调用
                pageStatus.value = PageStatus.END_LOAD_PAGE_DATA
            }
            .subscribe({
                val list = arrayListOf<Any>("github 搜索 Android 的结果 : ")
                list.addAll(it.items)
                dataList.postValue(list)
            }, {
                pageStatus.postValue(PageStatus.NET_ERROR)
            }).disposeOnDestroy(lifeOwner)
    }

}