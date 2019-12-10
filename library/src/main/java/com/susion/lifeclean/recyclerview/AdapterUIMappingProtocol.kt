package com.susion.lifeclean.recyclerview

/**
 * susionwang at 2019-12-09
 * 数据 -> UI View】的映射
 */
interface AdapterUIMappingProtocol<T> {

    /**
     * 获取数据的type
     */
    fun getItemType(data: T): Int

    /**
     * 根据type，获取一个 UI View
     */
    fun createItem(type: Int): AdapterItemView<*>
}

/**
 * 把数据设置给 UI View
 * */
interface AdapterItemView<T> {
    fun bindData(data: T, position: Int)
}
