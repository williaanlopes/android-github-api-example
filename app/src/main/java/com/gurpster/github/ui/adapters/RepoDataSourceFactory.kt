package com.gurpster.github.ui.adapters

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.gurpster.github.data.entity.Repo
import com.gurpster.github.data.remote.WebService


/**
 * Created by Williaan Souza (dextter) on 08/01/2021
 * Contact williaanlopes@gmail.com
 */

class RepoDataSourceFactory(private val webService: WebService) : DataSource.Factory<Int, Repo>() {

    private val dataSource = MutableLiveData<PageKeyedDataSource<Int, Repo>>()

    override fun create(): DataSource<Int, Repo> {
        val repoDataSource = RepoDataSource(webService)
        dataSource.postValue(repoDataSource)
        return repoDataSource
    }
}