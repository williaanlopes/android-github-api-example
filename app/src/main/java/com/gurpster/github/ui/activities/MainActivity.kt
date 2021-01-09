package com.gurpster.github.ui.activities

import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.gurpster.github.data.RepoRepository
import com.gurpster.github.data.entity.Repo
import com.gurpster.github.databinding.ActivityMainBinding
import com.gurpster.github.ui.adapters.RepoAdapter
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val repoViewModel: RepoViewModel by viewModels()

    @Inject
    lateinit var repoRepository: RepoRepository

    private val repoAdapter = RepoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        binding.repos.adapter = repoAdapter
        repoViewModel.listRepos.observe(this, {
            repoAdapter.submitList(it)
        })
    }

    object DataBindingAdapter {
        @JvmStatic
        @BindingAdapter("onRepositoryClick")
        fun onRepositoryClick(viewGroup: ViewGroup, repo: Repo) {
            viewGroup.setOnClickListener {
                TODO()
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
}