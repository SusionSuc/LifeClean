package com.susion.lifeclean.mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
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
import kotlinx.android.synthetic.main.page_git_repo.*

/**
 * Android ViewModel 使用范例
 * */
class SimpleMvvmActivity : AppCompatActivity() {

    private val TAG = javaClass.simpleName

    // 推荐使用 by lazy, 这样不需要每次使用变量时都需要判null
    private val viewModel by lazy {
        LifeClean.createLifeViewModel<GithubViewModel>(this)
    }

    private val adapter = SimpleRvAdapter(
        this,
        ArrayList()
    ).apply {
        registerMapping(String::class.java, SimpleStringView::class.java)
        registerMapping(Repo::class.java, GitRepoView::class.java)
    }

    private val searchWord = "Android"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_git_repo)
        supportActionBar?.title = "MVVM For Activity"
        gitRepoRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        gitRepoRv.adapter = adapter

        viewModel.pageStatus.observe(this, Observer<String> { pageStatus ->
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

        viewModel.dataList.observe(this, Observer<List<Any>> {
            Log.d(TAG, "data change : $it")
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
