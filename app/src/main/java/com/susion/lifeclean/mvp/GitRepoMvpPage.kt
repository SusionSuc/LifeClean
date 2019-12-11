package com.susion.lifeclean.mvp

import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.susion.lifeclean.LifeClean
import com.susion.lifeclean.R
import com.susion.lifeclean.core.LifePresenter
import com.susion.lifeclean.extensions.PageStatus
import com.susion.lifeclean.extensions.recyclerview.SimpleRvAdapter
import com.susion.lifeclean.api.Repo
import com.susion.lifeclean.arc.GitHubPageProtocol
import com.susion.lifeclean.arc.GithubPresenter
import com.susion.lifeclean.adapter.view.GitRepoView
import com.susion.lifeclean.adapter.view.SimpleStringView
import kotlinx.android.synthetic.main.page_git_repo.view.*

/**
 * susionwang at 2019-12-10
 */
class GitRepoMvpPage(context: AppCompatActivity) : GitHubPageProtocol, FrameLayout(context) {

    private val presenter: LifePresenter by lazy {
        LifeClean.createPresenter<GithubPresenter,GitHubPageProtocol>(context, this)
    }

    private val adapter = SimpleRvAdapter(context, ArrayList()).apply {
        registerMapping(String::class.java, SimpleStringView::class.java)
        registerMapping(Repo::class.java, GitRepoView::class.java)
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.page_git_repo, this)
        gitRepoRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        gitRepoRv.adapter = adapter
        presenter.dispatch(LoadData("Android", false))
        gitRepoRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val lastVisiblePos =
                    (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (lastVisiblePos >= adapter.data.size - 1 &&  gitRepoProgress.visibility == View.GONE) {
                    presenter.dispatch(LoadData("Android", true))
                }
            }
        })
    }

    override fun refreshDatas(datas: List<Any>, isLoadMore: Boolean) {
        adapter.submitDatas(datas, !isLoadMore)
        //查询数据状态
        val currentPageSize = presenter.getStatus<SimpleMvpStatus>()?.currentPageSize ?: 0
        Toast.makeText(context, "当前页 : $currentPageSize", Toast.LENGTH_SHORT).show()
    }

    override fun refreshPageStatus(status: String) {
        when (status) {
            PageStatus.START_LOAD_PAGE_DATA, PageStatus.STAT_LOAD_MORE -> {
                gitRepoProgress.visibility = View.VISIBLE
            }
            PageStatus.END_LOAD_PAGE_DATA, PageStatus.END_LOAD_MORE -> {
                gitRepoProgress.visibility = View.GONE
            }
            PageStatus.NET_ERROR -> {
                gitRepoNetError.visibility = View.VISIBLE
            }
        }
    }

}