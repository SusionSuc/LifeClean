package com.susion.lifeclean.demo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.susion.lifeclean.demo.adapter.AdapterTestActivity
import com.susion.lifeclean.demo.mvp.SimpleMvpActivity
import com.susion.lifeclean.demo.mvvm.SimpleMvvmActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainAcTvSimpleMvp.setOnClickListener {
            startActivity(Intent(this, SimpleMvpActivity::class.java))
        }

        mainAcTvSimpleMvvm.setOnClickListener {
            startActivity(Intent(this, SimpleMvvmActivity::class.java))
        }

        mainAcTvAdapter.setOnClickListener {
            startActivity(Intent(this, AdapterTestActivity::class.java))
        }

        mainAcTvSimpleMvpPage.setOnClickListener {
            SimplePageContainerActivity.start(
                this,
                SimplePageContainerActivity.MVP
            )
        }

        mainAcTvSimpleMvvmPage.setOnClickListener {
            SimplePageContainerActivity.start(
                this,
                SimplePageContainerActivity.MVVM
            )
        }

        mainAcTvSimpleLifePage.setOnClickListener {
            SimplePageContainerActivity.start(
                this,
                SimplePageContainerActivity.LIFE
            )
        }

    }
}
