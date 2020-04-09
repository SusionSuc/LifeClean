package com.susion.lifeclean.common.recyclerview

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

/**
 * create by susion
 * 简单的做数据 到 UI 的映射
 */
abstract class CommonRvAdapter<T>(val data: MutableList<T> = ArrayList()) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    AdapterUIMappingProtocol<T> {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val item = createItem(viewType)
            ?: throw RuntimeException("AdapterUIMappingProtocol.createItem cannot return null")
        return CommonViewHolder(item)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CommonViewHolder<T>).item.bindData(data[position], position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return getItemType(data[position])
    }

    protected class CommonViewHolder<T> internal constructor(var item: AdapterItemView<T>) :
        RecyclerView.ViewHolder(if (item is View) item else throw RuntimeException("item view must is view"))

}