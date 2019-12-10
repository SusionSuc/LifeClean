package com.susion.lifeclean.mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.susion.lifeclean.R
import com.susion.lifeclean.lifepage.PageStatus
import com.susion.lifeclean.model.Repo
import com.susion.lifeclean.recyclerview.SimpleRvAdapter
import com.susion.lifeclean.view.GitRepoView
import com.susion.lifeclean.view.SimpleStringView
import kotlinx.android.synthetic.main.activity_simple_mvvm.*

/**
 * Android ViewModel 使用范例
 * */
class SimpleMvvmActivity : AppCompatActivity() {

    private val TAG = javaClass.simpleName

    // 推荐使用 by lazy, 这样不需要每次使用变量时都需要判null
    val viewModel by lazy {
        getAcViewModel<SimpleViewModel>(this)
    }

    val adapter = SimpleRvAdapter(this, ArrayList()).apply {
        registerMapping(String::class.java, SimpleStringView::class.java)
        registerMapping(Repo::class.java, GitRepoView::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_mvvm)

        mvvmAcRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mvvmAcRv.adapter = adapter

        viewModel.pageStatus.observe(this, Observer<String> { pageStatus ->
            when (pageStatus) {
                PageStatus.START_LOAD_PAGE_DATA -> mvvmAcProgress.visibility = View.VISIBLE
                PageStatus.END_LOAD_PAGE_DATA -> mvvmAcProgress.visibility = View.GONE
                PageStatus.NET_ERROR -> mvvmAcTvNetError.visibility = View.VISIBLE
            }
        })

        viewModel.dataList.observe(this, Observer<List<Any>> {
            Log.d(TAG, "data change : $it")
            adapter.refresh(it)
        })

        viewModel.loadData()

        mvvmAcRv.postDelayed({
            viewModel.searchRepos("Android", 1, 20)
        }, 3000)
    }

}
