package com.gurpster.github.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gurpster.github.NetworkBoundResource
import com.gurpster.github.data.entity.Repo
import com.gurpster.github.data.remote.Resource
import com.gurpster.github.data.remote.WebService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by Williaan Souza (dextter) on 08/01/2021
 * Contact williaanlopes@gmail.com
 */

class RepoRepository @Inject constructor(
    private val webService: WebService
) {

    fun repo(owner: String, name: String): LiveData<Resource<Repo>> {
        val liveData = MutableLiveData<Resource<Repo>>()
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val user = async { webService.getUserAsync(name) }.await()
                val rep = async { webService.getRepoAsync(owner, name) }.await()
                if (user != null) {
                    rep.user = user
                }
                liveData.postValue(Resource.success(rep))
            } catch (e: Exception) {
                liveData.postValue(Resource.error(e.message.toString(), null))
            }
        }
        return liveData
    }
}