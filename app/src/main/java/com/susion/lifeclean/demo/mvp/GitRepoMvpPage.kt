package com.susion.lifeclean.demo.mvp

import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.susion.lifeclean.LifeClean
import com.susion.lifeclean.common.PageStatus
import com.susion.lifeclean.common.protocol.SimpleRvPageProtocol
import com.susion.lifeclean.common.recyclerview.SimpleRvAdapter
import com.susion.lifeclean.LifePresenter
import com.susion.lifeclean.demo.R
import com.susion.lifeclean.demo.api.Repo
import com.susion.lifeclean.demo.arc.GithubPresenter
import com.susion.lifeclean.demo.adapter.view.GitRepoView
import com.susion.lifeclean.demo.adapter.view.SimpleStringView
import kotlinx.android.synthetic.main.page_git_repo.view.*

/**
 * susionwang at 2019-12-10
 */
class GitRepoMvpPage(context: AppCompatActivity) : SimpleRvPageProtocol, FrameLayout(context) {

    private val presenter: LifePresenter by lazy {
        LifeClean.createPresenter<GithubPresenter, SimpleRvPageProtocol>(context, this)
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
        presenter.dispatch(SimpleRvPageProtocol.LoadData("Android", false))
        gitRepoRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val lastVisiblePos =
                    (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (lastVisiblePos >= adapter.data.size - 1 && gitRepoProgress.visibility == View.GONE) {
                    presenter.dispatch(SimpleRvPageProtocol.LoadData("Android", true))
                }
            }
        })
    }

    override fun refreshPageStatus(status: String, extra: Any) {
        when (status) {
            PageStatus.START_LOAD_PAGE_DATA, PageStatus.START_LOAD_MORE -> {
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

    override fun refreshDatas(datas: List<Any>, isLoadMore: Boolean, extra: Any) {
        adapter.submitDatas(datas, !isLoadMore)
        //查询数据状态
        val currentPageSize =
            presenter.getStatus<SimpleRvPageProtocol.SimpleMvpStatus>()?.currentPageSize ?: 0
        Toast.makeText(context, "当前页 : $currentPageSize", Toast.LENGTH_SHORT).show()
    }


}