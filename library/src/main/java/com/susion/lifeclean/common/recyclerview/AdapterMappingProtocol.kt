package com.susion.lifeclean.common.recyclerview

/**
 * susionwang at 2019-12-09
 * 数据 -> Type -> View
 */
interface AdapterDataToViewMapping<T> {

    //数据 ——> Type
    fun getItemType(data: T): Int

    // Type -> View
    fun createItem(type: Int): AdapterItemView<*>?

    companion object {
        const val ERROR_ITEM_TYPE = -1
    }
}

/**
 * 把数据设置给View
 * */
interface AdapterItemView<T> {
    fun bindData(data: T, position: Int)
}
