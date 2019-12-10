package com.susion.lifeclean.page

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup

class SimplePageContainerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<ViewGroup>(android.R.id.content).addView(GitRepoPage(this))
    }
}
