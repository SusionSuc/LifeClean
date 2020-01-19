package com.susion.lifeclean.mvvm

import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.susion.lifeclean.LifeClean
import com.susion.lifeclean.common.PageStatus
import com.susion.lifeclean.common.recyclerview.SimpleRvAdapter
import com.susion.lifeclean.arc.GithubViewModel
import com.susion.lifeclean.R
import com.susion.lifeclean.api.Repo
import com.susion.lifeclean.adapter.view.GitRepoView
import com.susion.lifeclean.adapter.view.SimpleStringView
import kotlinx.android.synthetic.main.page_git_repo.view.*

/**
 * susionwang at 2019-12-10
 */
class GitRepoMvvmPage(context: AppCompatActivity) : FrameLayout(context) {

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
    }

}