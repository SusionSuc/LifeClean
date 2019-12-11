package com.susion.lifeclean.adapter.view

import android.content.Context
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.TextView
import com.susion.lifeclean.extensions.recyclerview.AdapterItemView

/**
 * susionwang at 2019-12-09
 */

class SimpleStringView(context: Context) : AdapterItemView<String>, TextView(context) {

    init {
        setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17f)
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100)
    }

    override fun bindData(data: String, position: Int) {
        text = data
    }

}

