package com.susion.lifeclean.demo.page

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.susion.lifeclean.LifeClean
import com.susion.lifeclean.common.PageStatus
import com.susion.lifeclean.common.recyclerview.SimpleRvAdapter
import com.susion.lifeclean.LifePage
import com.susion.lifeclean.demo.R
import com.susion.lifeclean.demo.adapter.view.GitRepoView
import com.susion.lifeclean.demo.adapter.view.SimpleStringView
import com.susion.lifeclean.demo.api.Repo
import com.susion.lifeclean.demo.arc.GithubViewModel
import kotlinx.android.synthetic.main.page_git_repo.view.*

/**
 * susionwang at 2019-12-11
 * 带有生命周期的Page, 可以接收[Activity]的生命周期事件
 */
class GitHubLifePage(context: AppCompatActivity, id: String) : FrameLayout(context),
    LifePage {

    private val TAG = javaClass.simpleName
    private val searchWord = "Android"

    // 推荐使用 by lazy, 这样不需要每次使用变量时都需要判null
    private val viewModel by lazy {
        LifeClean.createLifeViewModel<GithubViewModel>(context)
    }

    private val adapter = SimpleRvAdapter(
        context,
        ArrayList()
    )
        .apply {
            registerMapping(String::class.java, SimpleStringView::class.java)
            registerMapping(Repo::class.java, GitRepoView::class.java)
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.page_git_repo, this)
        gitRepoRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        gitRepoRv.adapter = adapter
        viewModel.pageStatus.observe(context, Observer<String> { pageStatus ->
            when (pageStatus) {
                PageStatus.START_LOAD_PAGE_DATA, PageStatus.START_LOAD_MORE -> {
                    gitRepoProgress.visibility = View.VISIBLE
                }
                PageStatus.END_LOAD_PAGE_DATA, PageStatus.END_LOAD_MORE -> {
                    gitRepoProgress.visibility = View.GONE
                }
                PageStatus.NET_ERROR -> {
                    gitRepoNetError.visibility = View.VISIBLE
                    adapter.submitDatas(emptyList())
                }
            }
        })

        viewModel.dataList.observe(context, Observer<List<Any>> {
            adapter.submitDatas(it, false)
        })

        viewModel.loadSearchResult(searchWord, false)

        gitRepoRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val lastVisiblePos =
                    (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (lastVisiblePos >= adapter.data.size - 1) {
                    viewModel.loadSearchResult(searchWord, true)
                }
            }
        })

        Log.d(TAG, "id is :$id")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        Toast.makeText(context, "接收到Activity的生命周期事件 onResume", Toast.LENGTH_SHORT).show()
    }

}