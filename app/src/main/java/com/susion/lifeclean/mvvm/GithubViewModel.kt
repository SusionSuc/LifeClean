package com.susion.lifeclean.mvvm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.susion.lifeclean.api.GithubService
import com.susion.lifeclean.core.LifeViewModel
import com.susion.lifeclean.extensions.PageStatus
import com.susion.lifeclean.core.disposeOnDestroy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * create by susionwang at 2019-12-08
 *
 * ViewModel 加载数据，维护数据逻辑
 */
//class GithubViewModel(application: Application) : LifeAndroidViewModel(application) {
class GithubViewModel : LifeViewModel() {

    private val TAG = javaClass.simpleName
    private val apiService = GithubService.create()
    val pageStatus = MutableLiveData<String>()
    val dataList = MutableLiveData<List<Any>>()

    private val IN_QUALIFIER = "in:name,description"
    private var page = 0
    private var PAGE_SIZE = 20

    fun loadSearchResult(query: String, isLoadMore: Boolean = false) {

        Log.d(TAG, "lifeowner : ${getLifeOwner()}")

        if (!isLoadMore) {
            dataList.postValue(emptyList()) //清空数据
            page = 0
        }

        val requestPage = if (isLoadMore) page + 1 else page

        apiService.searchRepos(query + IN_QUALIFIER, requestPage, PAGE_SIZE)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                pageStatus.value =
                    if (isLoadMore) PageStatus.STAT_LOAD_MORE else PageStatus.START_LOAD_PAGE_DATA
            }.doOnTerminate {
                //doOnFinally : https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/doFinally.o.png
                //doOnTerminate : https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/doOnTerminate.o.png  -> 在onError之前调用
                pageStatus.value =
                    if (isLoadMore) PageStatus.END_LOAD_MORE else PageStatus.END_LOAD_PAGE_DATA
            }
            .subscribe({
                if (isLoadMore) {
                    page++
                }
                val list = arrayListOf<Any>("github 搜索 Android 的结果 : ")
                list.addAll(it.items)
                dataList.postValue(list)
            }, {
                pageStatus.postValue(PageStatus.NET_ERROR)
            }).disposeOnDestroy(getLifeOwner())
    }

}