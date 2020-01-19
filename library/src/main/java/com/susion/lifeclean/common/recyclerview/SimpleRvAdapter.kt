package com.susion.lifeclean.common.recyclerview

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * susionwang at 2019-12-09
 * 1. 快速注册映射的Adapter
 * 2. 只支持带有默认构造函数的View
 */
class SimpleRvAdapter<T : Any>(val context: Context, val data: MutableList<T> = ArrayList()) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    AdapterUIMappingProtocol<T> {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CommonViewHolder(
            createItem(
                viewType
            )
        )
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

    fun submitDatas(datas: List<T>, clear: Boolean = true) {
        if (clear) {
            data.clear()
            data.addAll(datas)
            notifyDataSetChanged()
        } else {
            val oldDataCount = data.size
            data.addAll(datas)
            notifyItemRangeInserted(oldDataCount, datas.size)
        }
    }

    private val data2TypeMap = HashMap<String, Int?>()    //<classname, type>
    private val type2ViewMap = HashMap<Int, Class<out AdapterItemView<*>>>()

    fun registerMapping(dataClass: Class<*>, viewClass: Class<out AdapterItemView<*>>) {
        val type = getTypeByHash(dataClass, viewClass)
        data2TypeMap[dataClass.name] = type
        type2ViewMap[type] = viewClass
    }

    /**
     * 会有异常， 必须提供一个 context的构造函数
     * */
    override fun createItem(type: Int): AdapterItemView<*> {
        val viewClass = type2ViewMap[type] ?: return FakeHolderItemView<Any>(
            context
        )
        val contextConstructor = viewClass.getDeclaredConstructor(Context::class.java)
        return contextConstructor.newInstance(context)
    }

    override fun getItemType(data: T): Int {
        return data2TypeMap[data.javaClass.name] ?: -1
    }

    private class CommonViewHolder<T> internal constructor(var item: AdapterItemView<T>) :
        RecyclerView.ViewHolder(if (item is View) item else throw RuntimeException("item view must is view"))

    private fun getTypeByHash(dataClass: Class<*>, viewClass: Class<out AdapterItemView<*>>): Int {
        var result = dataClass.hashCode()
        result = 31 * result + viewClass.hashCode()
        return result
    }

    class FakeHolderItemView<F>(context: Context) : View(context),
        AdapterItemView<F> {
        override fun bindData(data: F, position: Int) {
        }
    }

}
