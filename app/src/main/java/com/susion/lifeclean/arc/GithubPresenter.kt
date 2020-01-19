package com.susion.lifeclean.arc

import com.susion.lifeclean.common.PageStatus
import com.susion.lifeclean.common.protocol.SimpleRvPageProtocol
import com.susion.lifeclean.Action
import com.susion.lifeclean.LifePresenter
import com.susion.lifeclean.State
import com.susion.lifeclean.rx.disposeOnDestroy
import com.susion.lifeclean.api.GithubService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * create by susionwang at 2019-12-08
 */
class GithubPresenter(val view: SimpleRvPageProtocol) : LifePresenter() {

    private val apiService = GithubService.create()
    private val IN_QUALIFIER = "in:name,description"
    private var page = 0
    private var PAGE_SIZE = 20

    override fun dispatch(action: Action) {
        when (action) {
            is SimpleRvPageProtocol.LoadData -> {
                loadSearchResult(action.searchWord, action.isLoadMore)
            }
        }
    }

    override fun <T : State> getStatus(): T? {
        return SimpleRvPageProtocol.SimpleMvpStatus(page) as? T
    }

    private fun loadSearchResult(query: String, isLoadMore: Boolean = false) {

        if (!isLoadMore) {
            page = 0
        }

        val requestPage = if (isLoadMore) page + 1 else page

        apiService.searchRepos(query + IN_QUALIFIER, requestPage, PAGE_SIZE)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                view.refreshPageStatus(if (isLoadMore) PageStatus.START_LOAD_MORE else PageStatus.START_LOAD_PAGE_DATA)
            }.doOnTerminate {
                //doOnFinally : https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/doFinally.o.png
                //doOnTerminate : https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/doOnTerminate.o.png  -> 在onError之前调用
                view.refreshPageStatus(if (isLoadMore) PageStatus.END_LOAD_MORE else PageStatus.END_LOAD_PAGE_DATA)
            }
            .subscribe({
                if (isLoadMore) {
                    page++
                }
                val list = arrayListOf<Any>("github 搜索 Android 的结果 : ")
                list.addAll(it.items)
                view.refreshDatas(list, isLoadMore)
            }, {
                view.refreshPageStatus(PageStatus.NET_ERROR)
            }).disposeOnDestroy(getLifeOwner())
    }

}