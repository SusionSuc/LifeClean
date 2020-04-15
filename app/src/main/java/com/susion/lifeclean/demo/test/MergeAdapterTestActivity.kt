package com.susion.lifeclean.demo.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.susion.lifeclean.common.recyclerview.MergeAdapter
import com.susion.lifeclean.common.recyclerview.SimpleRvAdapter
import com.susion.lifeclean.demo.R
import com.susion.lifeclean.demo.view.SimpleDescInfo
import com.susion.lifeclean.demo.view.SimpleDescView
import com.susion.lifeclean.demo.view.SimpleTitleInfo
import com.susion.lifeclean.demo.view.SimpleTitleView
import kotlinx.android.synthetic.main.activity_merge_adapter_test.*

class MergeAdapterTestActivity : AppCompatActivity() {

    private val titleAdapter by lazy {
        SimpleRvAdapter<Any>(this).apply {
            registerMapping(SimpleTitleInfo::class.java, SimpleTitleView::class.java)
        }
    }

    private val descAdapter by lazy {
        SimpleRvAdapter<Any>(this).apply {
            registerMapping(SimpleDescInfo::class.java, SimpleDescView::class.java)
        }
    }

    private val mergeAdapter by lazy {
        MergeAdapter(
            titleAdapter,
            descAdapter
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_merge_adapter_test)

        mMergeAdapterTestAcRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        mMergeAdapterTestAcRv2.layoutManager =
//            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mergeAdapter.data.addAll(dataList())
        mMergeAdapterTestAcRv.adapter = mergeAdapter
//        mMergeAdapterTestAcRv2.adapter = descAdapter

//        mMergeAdapterTestAcRv2.postDelayed({
//            descAdapter.data.add(SimpleDescInfo("描述三"))
//            descAdapter.data.add(SimpleDescInfo("描述四"))
//            descAdapter.notifyDataSetChanged()
//        }, 3000)

    }

    private fun dataList(): List<Any> {
        return ArrayList<Any>().apply {
            add(SimpleTitleInfo("大标题"))
            add(SimpleDescInfo("描述一"))
            add(SimpleTitleInfo("小标题"))
            add(SimpleDescInfo("描述二"))
        }
    }

}
