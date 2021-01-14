package com.gurpster.github.ui.activities

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.gurpster.github.R
import com.gurpster.github.data.entity.Repo
import com.gurpster.github.data.remote.Status
import com.gurpster.github.databinding.ActivityRepoDetailsBinding
import com.gurpster.github.util.Config.Config
import dagger.hilt.android.AndroidEntryPoint


/**
 * Created by Williaan Souza (dextter) on 10/01/2021
 * Contact williaanlopes@gmail.com
 */

@AndroidEntryPoint
class RepoDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRepoDetailsBinding
    private val repoDetailsViewModel: RepoDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepoDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.lifecycleOwner = this

        val repo = intent.getStringExtra(Config.Constants.REPO_NAME_EXTRA)
        val owner = intent.getStringExtra(Config.Constants.OWNER_NAME_EXTRA)

        repoDetailsViewModel.getLiveData(owner, repo).observe(this, {
            when (it.status) {
                Status.SUCCESS -> bindView(it.data)
                Status.ERROR -> Toast.makeText(
                    this,
                    "Não foi possível carregar o repositório",
                    Toast.LENGTH_SHORT
                ).show()
                Status.LOADING -> println("loading...")
            }
        })
    }

    private fun bindView(repo: Repo?) {
        binding.repo = repo
        binding.executePendingBindings()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}