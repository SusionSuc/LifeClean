package com.susion.lifeclean.demo.view

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.TextView
import com.susion.lifeclean.common.recyclerview.AdapterItemView
import com.susion.lifeclean.demo.test.SimplePageContainerActivity
import com.susion.lifeclean.demo.adapter.AdapterTestActivity
import com.susion.lifeclean.demo.mvp.SimpleMvpActivity
import com.susion.lifeclean.demo.mvvm.SimpleMvvmActivity
import com.susion.lifeclean.demo.test.MergeAdapterTestActivity

/**
 * susionwang at 2020-04-09
 */

class SimpleTitleInfo(val title: String) {
    companion object {
        const val AC_MVP = "MVP For Activity"
        const val AC_MVVM = "MVVM For Activity"
        const val PAGE_MVP = "MVP For Page"
        const val PAGE_MVVM = "MVVM For Page"
        const val LIFE_PAGE = "Life Page"
        const val ADAPTER = "Adapter"
        const val MERGE_ADAPTER = "Merge Adapter"
    }
}

class SimpleTitleView(context: Context) : TextView(context), AdapterItemView<SimpleTitleInfo> {

    init {

        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18f)
        setPadding(30, 30, 30, 0)
        setTextColor(Color.BLACK)
    }

    override fun bindData(data: SimpleTitleInfo, position: Int) {
        text = data.title
        setOnClickListener {
            when (data.title) {
                SimpleTitleInfo.AC_MVP -> {
                    context.startActivity(Intent(context, SimpleMvpActivity::class.java))
                }
                SimpleTitleInfo.AC_MVVM -> {
                    context.startActivity(Intent(context, SimpleMvvmActivity::class.java))
                }
                SimpleTitleInfo.PAGE_MVVM -> {
                    SimplePageContainerActivity.start(
                        context,
                        SimplePageContainerActivity.MVVM
                    )
                }
                SimpleTitleInfo.PAGE_MVP -> {
                    SimplePageContainerActivity.start(
                        context,
                        SimplePageContainerActivity.MVP
                    )
                }
                SimpleTitleInfo.LIFE_PAGE -> {
                    SimplePageContainerActivity.start(
                        context,
                        SimplePageContainerActivity.LIFE
                    )
                }
                SimpleTitleInfo.ADAPTER -> {
                    context.startActivity(Intent(context, AdapterTestActivity::class.java))
                }
                SimpleTitleInfo.MERGE_ADAPTER -> {
                    context.startActivity(Intent(context, MergeAdapterTestActivity::class.java))
                }
            }
        }
    }

}