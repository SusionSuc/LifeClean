package com.susion.lifeclean.common.recyclerview

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * susionwang at 2019-12-09
 * 异步更新数据的Adapter, 更新方式更加友好。
 */
abstract class DifferRvAdapter<T>(diffCallBack: DiffUtil.ItemCallback<T> = defaultItemDiffCallback()) :
    ListAdapter<T, RecyclerView.ViewHolder>(diffCallBack),
    AdapterDataToViewMapping<T> {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val item = createItem(viewType)
            ?: throw RuntimeException("AdapterDataToViewMapping.createItem cannot return null")
        return CommonViewHolder(item)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CommonViewHolder<T>).item.bindData(getItem(position), position)
    }

    override fun getItemViewType(position: Int): Int {
        return getItemType(getItem(position))
    }

    private class CommonViewHolder<T> internal constructor(var item: AdapterItemView<T>) :
        RecyclerView.ViewHolder(if (item is View) item else throw RuntimeException("item view must is view"))

    companion object {
        fun <T> defaultItemDiffCallback(): DiffUtil.ItemCallback<T> {
            return object : DiffUtil.ItemCallback<T>() {
                override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = true
                override fun areItemsTheSame(oldItem: T, newItem: T): Boolean = oldItem == newItem
            }
        }
    }
}

