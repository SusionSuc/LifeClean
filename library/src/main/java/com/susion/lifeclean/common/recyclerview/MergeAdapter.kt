package com.susion.lifeclean.common.recyclerview

/**
 * susionwang at 2020-04-09
 * 组合多个Adapter映射, 支持组合 [CommonRvAdapter][SimpleRvAdapter]
 *
 * 多个Adapter不应该包含相同的 《数据-类型》 映射
 */
class MergeAdapter<T>() : CommonRvAdapter<T>(), AdapterUIMappingProtocol<T> {

    protected val TAG = javaClass.simpleName
    private val adapterList: ArrayList<AdapterUIMappingProtocol<T>> = ArrayList()

    constructor(
        dataList: List<T>,
        vararg adapters: AdapterUIMappingProtocol<T>
    ) : this() {
        data.addAll(dataList)
        adapterList.addAll(adapters)
    }

    constructor(vararg adapters: AdapterUIMappingProtocol<T>) : this() {
        adapterList.addAll(adapters)
    }

    /**
     * 按照Adapter顺序，搜索第一个映射了这个数据的Adapter Item Type
     * */
    override fun getItemType(data: T): Int {
        for (adapter in adapterList) {
            val type = adapter.getItemType(data)
            if (type != AdapterUIMappingProtocol.ERROR_ITEM_TYPE) {
                return type
            }
        }
        return AdapterUIMappingProtocol.ERROR_ITEM_TYPE
    }

    /**
     * 按照Adapter顺序，搜索第一个映射了这种type的Adapter Item View
     * */
    override fun createItem(type: Int): AdapterItemView<*>? {
        for (adapter in adapterList) {
            val itemView = adapter.createItem(type)
            if (itemView != null) {
                return itemView
            }
        }
        return null
    }

}