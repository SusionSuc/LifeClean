package com.susion.lifeclean.adapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.susion.lifeclean.R
import com.susion.lifeclean.extensions.recyclerview.*
import com.susion.lifeclean.adapter.view.SimpleInfo
import com.susion.lifeclean.adapter.view.SimpleInfoView
import com.susion.lifeclean.adapter.view.SimpleStringView
import kotlinx.android.synthetic.main.activity_adapter_test.*

/**
 * adapter 使用示例
 * */
class AdapterTestActivity : AppCompatActivity() {

    private val adapter by lazy {
        //快速注册的Adapter
        SimpleRvAdapter(this, ArrayList()).apply {
            registerMapping(String::class.java, SimpleStringView::class.java)
            registerMapping(SimpleInfo::class.java, SimpleInfoView::class.java)
        }

//        val mappingProtocol = object : AdapterUIMappingProtocol<Any> {
//            private val TYPE2 = 1
//            private val TYPE1 = 0
//
//            override fun getItemType(data: Any): Int {
//                return when (data) {
//                    is String -> TYPE1
//                    else -> TYPE2
//                }
//            }
//
//            override fun createItem(type: Int): AdapterItemView<*> {
//                return when (type) {
//                    TYPE1 -> SimpleItemView(this@AdapterTestActivity)
//                    else -> SimpleInfoView(this@AdapterTestActivity)
//                }
//            }
//        }

//        object : RvAdapter<Any>(ArrayList()) {
//            override fun getItemType(data: Any) = mappingProtocol.getItemType(data)
//            override fun createItem(type: Int) = mappingProtocol.createItem(type)
//        }

//        object : DifferRvAdapter<Any>() {
//            override fun getItemType(data: Any) = mappingProtocol.getItemType(data)
//            override fun createItem(type: Int) = mappingProtocol.createItem(type)
//        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adapter_test)
        supportActionBar?.title = "Adapter使用范例"
        adapterAcRootRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapterAcRootRv.adapter = adapter

//        adapterForDiffAdapter()

        adapterForCommon()
    }

    private fun adapterForCommon() {
        adapter.data.addAll(listOf("aaaa", "bbbb", "cccc", "dddd", SimpleInfo("xxxx")))
        adapter.notifyDataSetChanged()
    }


    //一定要给一个新的list  【 迷之新的list?????】
//    private fun adapterForDiffAdapter() {
//        adapter.submitList(listOf("aaaa", "bbbb", "cccc", "2秒后列表更新", SimpleInfo("dddd")))
//        adapterAcRootRv.postDelayed({
//            adapter.submitList(listOf("1111", "2222", "3333", "2333", "4444"))
//        }, 2000)
//    }

}
