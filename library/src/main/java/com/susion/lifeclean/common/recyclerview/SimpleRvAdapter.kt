package com.susion.lifeclean.common.recyclerview

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * susionwang at 2019-12-09
 * 1. 快速注册映射的Adapter
 * 2. 只支持带有默认构造函数的View
 */
class SimpleRvAdapter<T : Any>(val context: Context, dataList: MutableList<T> = ArrayList()) :
    CommonRvAdapter<T>(dataList),
    AdapterUIMappingProtocol<T> {

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
    @SuppressLint("UseSparseArrays")
    private val type2ViewMap = HashMap<Int, Class<out AdapterItemView<*>>>()

    fun registerMapping(dataClass: Class<*>, viewClass: Class<out AdapterItemView<*>>) {
        val type = getTypeByHash(dataClass, viewClass)
        data2TypeMap[dataClass.name] = type
        type2ViewMap[type] = viewClass
    }

    /**
     * 会有异常， 必须提供一个 context的构造函数
     * */
    override fun createItem(type: Int): AdapterItemView<*>? {
        val viewClass = type2ViewMap[type] ?: return null
        val contextConstructor = viewClass.getDeclaredConstructor(Context::class.java)
        return contextConstructor.newInstance(context)
    }

    override fun getItemType(data: T): Int {
        return data2TypeMap[data.javaClass.name] ?: AdapterUIMappingProtocol.ERROR_ITEM_TYPE
    }

    private fun getTypeByHash(dataClass: Class<*>, viewClass: Class<out AdapterItemView<*>>): Int {
        var result = dataClass.hashCode()
        result = 31 * result + viewClass.hashCode()
        return result
    }

}
