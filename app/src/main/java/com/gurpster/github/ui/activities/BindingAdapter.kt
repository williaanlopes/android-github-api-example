package com.gurpster.github.ui.activities

import android.content.Intent
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.gurpster.github.data.entity.Repo
import com.gurpster.github.util.Config.Config


/**
 * Created by Williaan Souza (dextter) on 10/01/2021
 * Contact williaanlopes@gmail.com
 */

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("onRepositoryClick")
    fun onRepositoryClick(viewGroup: ViewGroup, repo: Repo) {
        viewGroup.setOnClickListener {
            val intent = Intent(viewGroup.context, RepoDetailsActivity::class.java)
            intent.putExtra(Config.Constants.REPO_NAME_EXTRA, repo.name)
            intent.putExtra(Config.Constants.OWNER_NAME_EXTRA, repo.owner.login)
            viewGroup.context.startActivity(intent)
        }
    }

    @JvmStatic
    @BindingAdapter("glide")
    fun glide(view: ShapeableImageView, url: String?) {
        if (!url.isNullOrEmpty()) {
            Glide.with(view).load(url).into(view)
        }
    }

}