package com.gurpster.github.ui.adapters

import androidx.paging.PageKeyedDataSource
import com.gurpster.github.data.entity.Repo
import com.gurpster.github.data.entity.Search
import com.gurpster.github.data.remote.WebService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Created by Williaan Souza (dextter) on 08/01/2021
 * Contact williaanlopes@gmail.com
 */

class SearchDataSource(
    private val repoName: String,
    private val webService: WebService
) : PageKeyedDataSource<Int, Repo>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Repo>
    ) {
        webService.search(repoName, 1, params.requestedLoadSize).enqueue(object : Callback<Search> {
            override fun onResponse(call: Call<Search>, response: Response<Search>) {
                val data: Search? = response.body()
                callback.onResult(data?.items as MutableList<Repo>, null, 2)
            }

            override fun onFailure(call: Call<Search>, t: Throwable) {
            }
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Repo>) {
        webService.search(repoName, params.key, params.requestedLoadSize)
            .enqueue(object : Callback<Search> {
                override fun onResponse(call: Call<Search>, response: Response<Search>) {
                    val data: Search? = response.body()
                    callback.onResult(data?.items as MutableList<Repo>, params.key + 1)
                }

                override fun onFailure(call: Call<Search>, t: Throwable) {
                }
            })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Repo>) {
        webService.search(repoName, params.key, params.requestedLoadSize)
            .enqueue(object : Callback<Search> {
                override fun onResponse(call: Call<Search>, response: Response<Search>) {
                    val data: Search? = response.body()
                    callback.onResult(data?.items as MutableList<Repo>, params.key + 1)
                }

                override fun onFailure(call: Call<Search>, t: Throwable) {
                }
            })
    }
}