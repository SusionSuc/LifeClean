package com.susion.lifeclean.mvp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.susion.lifeclean.LifeClean
import com.susion.lifeclean.common.PageStatus
import com.susion.lifeclean.common.protocol.SimpleRvPageProtocol
import com.susion.lifeclean.common.recyclerview.SimpleRvAdapter
import com.susion.lifeclean.LifePresenter
import com.susion.lifeclean.arc.GithubPresenter
import com.susion.lifeclean.R
import com.susion.lifeclean.api.Repo
import com.susion.lifeclean.adapter.view.GitRepoView
import com.susion.lifeclean.adapter.view.SimpleStringView
import kotlinx.android.synthetic.main.page_git_repo.*

class SimpleMvpActivity : AppCompatActivity(),
    SimpleRvPageProtocol {

    private val presenter: LifePresenter by lazy {
        LifeClean.createPresenter<GithubPresenter, SimpleRvPageProtocol>(this, this)
    }

    private val adapter = SimpleRvAdapter(
        this,
        ArrayList()
    ).apply {
        registerMapping(String::class.java, SimpleStringView::class.java)
        registerMapping(Repo::class.java, GitRepoView::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_git_repo)
        supportActionBar?.title = "MVP For Activity"
        gitRepoRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
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

    override fun refreshDatas(datas: List<Any>, isLoadMore: Boolean, extra: Any) {
        adapter.submitDatas(datas, !isLoadMore)

        //查询数据状态
        val currentPageSize =
            presenter.getStatus<SimpleRvPageProtocol.SimpleMvpStatus>()?.currentPageSize ?: 0
        Toast.makeText(this, "当前页 : $currentPageSize", Toast.LENGTH_SHORT).show()
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
}
