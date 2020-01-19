package com.susion.lifeclean.demo.adapter.view

import android.content.Context
import android.util.TypedValue
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import com.susion.lifeclean.common.recyclerview.AdapterItemView

/**
 * susionwang at 2019-12-09
 */

// item type 2
class SimpleInfo(val name: String)

class SimpleInfoView(context: Context) : AdapterItemView<SimpleInfo>, AppCompatTextView(context) {

    init {
        setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17f)
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100)
    }

    override fun bindData(data: SimpleInfo, position: Int) {
        text = data.name
    }
}
