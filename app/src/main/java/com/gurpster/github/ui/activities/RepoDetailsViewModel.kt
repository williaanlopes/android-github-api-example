package com.gurpster.github.ui.activities

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.gurpster.github.data.RepoRepository


/**
 * Created by Williaan Souza (dextter) on 10/01/2021
 * Contact williaanlopes@gmail.com
 */

class RepoDetailsViewModel @ViewModelInject constructor(
    private val repoRepository: RepoRepository
) : ViewModel() {
    fun getLiveData(owner: String, repoName: String) = repoRepository.repo(owner, repoName)
}