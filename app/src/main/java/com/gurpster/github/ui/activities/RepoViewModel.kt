package com.gurpster.github.ui.activities

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.gurpster.github.data.entity.Repo
import com.gurpster.github.data.remote.WebService
import com.gurpster.github.ui.adapters.RepoDataSourceFactory
import java.util.*


/**
 * Created by Williaan Souza (dextter) on 09/01/2021
 * Contact williaanlopes@gmail.com
 */

class RepoViewModel @ViewModelInject constructor(private val webService: WebService) : ViewModel() {

    var listRepos: LiveData<PagedList<Repo>>

    init {

        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(30)
            .setPrefetchDistance(8)
            .setPageSize(20)
            .build()


        val defaultFactory = RepoDataSourceFactory(webService)
        listRepos = LivePagedListBuilder(defaultFactory, pagedListConfig).build()
    }
}