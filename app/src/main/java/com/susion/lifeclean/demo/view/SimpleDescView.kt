package com.susion.lifeclean.demo.view

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import com.susion.lifeclean.common.recyclerview.AdapterItemView
import com.susion.lifeclean.demo.test.SimplePageContainerActivity
import com.susion.lifeclean.demo.adapter.AdapterTestActivity
import com.susion.lifeclean.demo.mvp.SimpleMvpActivity
import com.susion.lifeclean.demo.mvvm.SimpleMvvmActivity

/**
 * susionwang at 2020-04-09
 */

class SimpleDescInfo(val desc: String)

class SimpleDescView(context: Context) : AppCompatTextView(context), AdapterItemView<SimpleDescInfo> {

    init {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14f)
        setPadding(30, 30, 30, 0)
        setTextColor(Color.DKGRAY)
    }

    override fun bindData(data: SimpleDescInfo, position: Int) {
        text = data.desc
    }

}