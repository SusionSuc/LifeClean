package com.susion.lifeclean.demo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.susion.lifeclean.LifeClean
import com.susion.lifeclean.demo.mvp.GitRepoMvpPage
import com.susion.lifeclean.demo.mvvm.GitRepoMvvmPage
import com.susion.lifeclean.demo.page.GitHubLifePage

class SimplePageContainerActivity : AppCompatActivity() {

    companion object {
        const val MVP = "mvp"
        const val MVVM = "mvvm"
        const val LIFE = "life"
        private val PAGE_TYPE = "page"
        fun start(context: Context, pageType: String = "") {
            val intent = Intent(context, SimplePageContainerActivity::class.java)
            intent.putExtra(PAGE_TYPE, pageType)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val page = intent.getStringExtra(PAGE_TYPE)
        supportActionBar?.title = "$page page"

        val contentView = when (page) {
            MVP -> GitRepoMvpPage(
                this
            )
            MVVM -> GitRepoMvvmPage(
                this
            )
            LIFE -> LifeClean.createPage<GitHubLifePage>(
                this
            )
            else -> View(this)
        }

        findViewById<ViewGroup>(android.R.id.content).addView(contentView)
    }
}
