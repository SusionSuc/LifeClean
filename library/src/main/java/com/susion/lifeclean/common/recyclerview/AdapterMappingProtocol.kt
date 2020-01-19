package com.susion.lifeclean.common.recyclerview

/**
 * susionwang at 2019-12-09
 * 数据 -> Type -> View
 */
internal interface AdapterUIMappingProtocol<T> {

    //数据 ——> Type
    fun getItemType(data: T): Int

    // Type -> View
    fun createItem(type: Int): AdapterItemView<*>
}

/**
 * 把数据设置给View
 * */
interface AdapterItemView<T> {
    fun bindData(data: T, position: Int)
}
