package com.gurpster.github.ui.activities

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.SearchManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import com.gurpster.github.R
import com.gurpster.github.data.RepoRepository
import com.gurpster.github.databinding.ActivityMainBinding
import com.gurpster.github.ui.adapters.RepoAdapter
import com.gurpster.github.util.Urils.getThemeColor
import com.gurpster.github.util.Urils.isRtl
import com.jakewharton.rxbinding4.appcompat.queryTextChanges
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var repoRepository: RepoRepository

    private lateinit var binding: ActivityMainBinding
    private val repoViewModel: RepoViewModel by viewModels()

    private val repoAdapter = RepoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_github);
    }

    override fun onResume() {
        super.onResume()

        binding.shimmerViewContainer.startShimmer()

        binding.repos.adapter = repoAdapter
        repoViewModel.listRepos.observe(this, {
            repoAdapter.submitList(it)

            if (it != null) {
                binding.repos.visibility = View.VISIBLE
                binding.shimmerViewContainer.visibility = View.GONE
                binding.shimmerViewContainer.stopShimmer()
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.dashboard, menu)

        val searchItem: MenuItem? = menu.findItem(R.id.action_search)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView: SearchView = searchItem?.actionView as SearchView

        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(menuItem: MenuItem?): Boolean {
                animateSearchToolbar(1, containsOverflow = true, show = true)
                searchView.queryTextChanges()
                    .debounce(600, TimeUnit.MILLISECONDS)
                    .observeOn(Schedulers.io())
                    .subscribe { text ->
                        repoViewModel.searchRepos.postValue(text.toString())
                    }
                return true
            }

            override fun onMenuItemActionCollapse(menuItem: MenuItem?): Boolean {
                if (searchItem.isActionViewExpanded) {
                    animateSearchToolbar(1, containsOverflow = false, show = false)
                }
                return true
            }
        })

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        return true
    }

    fun animateSearchToolbar(numberOfMenuIcon: Int, containsOverflow: Boolean, show: Boolean) {
        binding.toolbar.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white))
        if (show) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val width: Int = binding.toolbar.width -
                        (if (containsOverflow) resources.getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material) else 0) -
                        resources.getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) * numberOfMenuIcon / 2
                val createCircularReveal: Animator = ViewAnimationUtils.createCircularReveal(
                    binding.toolbar,
                    if (isRtl(resources)) binding.toolbar.width - width else width,
                    binding.toolbar.height / 2,
                    0.0f,
                    width.toFloat()
                )
                createCircularReveal.duration = 250
                createCircularReveal.start()
            } else {
                val translateAnimation =
                    TranslateAnimation(0.0f, 0.0f, (-binding.toolbar.height).toFloat(), 0.0f)
                translateAnimation.duration = 220
                binding.toolbar.clearAnimation()
                binding.toolbar.startAnimation(translateAnimation)
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val width: Int = binding.toolbar.width -
                        (if (containsOverflow) resources.getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material) else 0) -
                        resources.getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) * numberOfMenuIcon / 2
                val createCircularReveal: Animator = ViewAnimationUtils.createCircularReveal(
                    binding.toolbar,
                    if (isRtl(resources)) binding.toolbar.width - width else width,
                    binding.toolbar.height / 2,
                    width.toFloat(),
                    0.0f
                )
                createCircularReveal.duration = 250
                createCircularReveal.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        binding.toolbar.setBackgroundColor(
                            getThemeColor(
                                this@MainActivity,
                                R.attr.colorPrimary
                            )
                        )
                    }
                })
                createCircularReveal.start()
            } else {
                val alphaAnimation = AlphaAnimation(1.0f, 0.0f)
                val translateAnimation: Animation =
                    TranslateAnimation(0.0f, 0.0f, 0.0f, (-binding.toolbar.height).toFloat())
                val animationSet = AnimationSet(true)
                animationSet.addAnimation(alphaAnimation)
                animationSet.addAnimation(translateAnimation)
                animationSet.duration = 220
                animationSet.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation?) {}
                    override fun onAnimationEnd(animation: Animation?) {
                        binding.toolbar.setBackgroundColor(
                            getThemeColor(
                                this@MainActivity,
                                R.attr.colorPrimary
                            )
                        )
                    }

                    override fun onAnimationRepeat(animation: Animation?) {}
                })
                binding.toolbar.startAnimation(animationSet)
            }
        }
    }
}