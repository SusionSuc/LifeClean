package com.susion.lifeclean.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.susion.lifeclean.common.recyclerview.SimpleRvAdapter
import com.susion.lifeclean.demo.view.SimpleTitleInfo
import com.susion.lifeclean.demo.view.SimpleTitleView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val adapter by lazy {
        SimpleRvAdapter<SimpleTitleInfo>(this).apply {
            registerMapping(SimpleTitleInfo::class.java, SimpleTitleView::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mMainAcRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter.data.addAll(getAllTitleInfo())
        mMainAcRv.adapter = adapter
    }

    private fun getAllTitleInfo(): List<SimpleTitleInfo> {
        return ArrayList<SimpleTitleInfo>().apply {
            add(SimpleTitleInfo(SimpleTitleInfo.AC_MVP))
            add(SimpleTitleInfo(SimpleTitleInfo.AC_MVVM))
            add(SimpleTitleInfo(SimpleTitleInfo.PAGE_MVP))
            add(SimpleTitleInfo(SimpleTitleInfo.PAGE_MVVM))
            add(SimpleTitleInfo(SimpleTitleInfo.ADAPTER))
            add(SimpleTitleInfo(SimpleTitleInfo.LIFE_PAGE))
            add(SimpleTitleInfo(SimpleTitleInfo.MERGE_ADAPTER))
        }
    }
}
