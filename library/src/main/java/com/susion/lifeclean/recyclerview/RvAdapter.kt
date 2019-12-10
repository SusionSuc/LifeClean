package com.susion.lifeclean.recyclerview

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

/**
 * create by susion
 * 简单的做数据 到 UI 的映射
 */
abstract class RvAdapter<T>(val dataList: MutableList<T>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), AdapterUIMappingProtocol<T> {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CommonViewHolder(createItem(viewType))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CommonViewHolder<T>).item.bindData(dataList[position], position)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun getItemViewType(position: Int): Int {
        return getItemType(dataList[position])
    }

    private class CommonViewHolder<T> internal constructor(var item: AdapterItemView<T>) :
        RecyclerView.ViewHolder(if (item is View) item else throw RuntimeException("item view must is view"))

}
