package com.susion.lifeclean.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.susion.lifeclean.R
import com.susion.lifeclean.model.Repo
import com.susion.lifeclean.recyclerview.AdapterItemView
import kotlinx.android.synthetic.main.repo_view_item.view.*

/**
 * susionwang at 2019-12-09
 */
class GitRepoView(context: Context) : AdapterItemView<Repo>, ConstraintLayout(context) {

    init {
        LayoutInflater.from(context).inflate(R.layout.repo_view_item, this)
        setPadding(20, 20, 20, 20)
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun bindData(repo: Repo, position: Int) {
        repo_name.text = repo.fullName

        // if the description is missing, hide the TextView
        var descriptionVisibility = View.GONE
        if (repo.description != null) {
            repo_description.text = repo.description
            descriptionVisibility = View.VISIBLE
        }
        repo_description.visibility = descriptionVisibility

        repo_stars.text = repo.stars.toString()
        repo_forks.text = repo.forks.toString()
    }

}