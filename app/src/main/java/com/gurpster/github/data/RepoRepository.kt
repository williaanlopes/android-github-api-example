package com.gurpster.github.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gurpster.github.data.entity.Repo
import com.gurpster.github.data.remote.NetworkBoundResource
import com.gurpster.github.data.remote.Resource
import com.gurpster.github.data.remote.WebService
import com.gurpster.github.di.AppExecutorsModule
import javax.inject.Inject


/**
 * Created by Williaan Souza (dextter) on 08/01/2021
 * Contact williaanlopes@gmail.com
 */

class RepoRepository @Inject constructor(
    private val appExecutors: AppExecutorsModule,
    private val webService: WebService
) {

    fun details(owner: String, name: String): LiveData<Resource<Repo>> {
        return object : NetworkBoundResource<Repo, Repo>(appExecutors) {
            override fun saveCallResult(item: Repo) {
            }

            override fun shouldFetch(data: Repo): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<Repo> {
                return MutableLiveData()
            }

            override fun createCall() = webService.getDetails(owner, name)

        }.asLiveData()
    }
}