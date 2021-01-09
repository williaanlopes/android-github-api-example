package com.gurpster.github.ui.adapters

import androidx.paging.PageKeyedDataSource
import com.gurpster.github.data.entity.Repo
import com.gurpster.github.data.remote.WebService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Created by Williaan Souza (dextter) on 08/01/2021
 * Contact williaanlopes@gmail.com
 */

class RepoDataSource(private val webService: WebService) : PageKeyedDataSource<Int, Repo>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Repo>) {
        webService.listRepos(1, params.requestedLoadSize).enqueue(object : Callback<List<Repo>> {
            override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {
                callback.onResult(response.body() as MutableList<Repo>, null, 2)
            }

            override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
            }
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Repo>) {
        webService.listRepos(params.key, params.requestedLoadSize).enqueue(object :
            Callback<List<Repo>> {
            override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {
                callback.onResult(response.body() as MutableList<Repo>, params.key + 1)
            }

            override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
            }
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Repo>) {
        webService.listRepos(params.key, params.requestedLoadSize).enqueue(object :
            Callback<List<Repo>> {
            override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {
                callback.onResult(response.body() as MutableList<Repo>, params.key + 1)
            }

            override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
            }
        })
    }
}