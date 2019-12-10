package com.susion.lifeclean.view

import android.content.Context
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.TextView
import com.susion.lifeclean.recyclerview.AdapterItemView

/**
 * susionwang at 2019-12-09
 */

// item type 2
class SimpleInfo(val name: String)

class SimpleInfoView(context: Context) : AdapterItemView<SimpleInfo>, TextView(context) {

    init {
        setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17f)
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100)
    }

    override fun bindData(data: SimpleInfo, position: Int) {
        text = data.name
    }
}
