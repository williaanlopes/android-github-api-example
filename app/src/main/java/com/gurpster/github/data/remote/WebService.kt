package com.gurpster.github.data.remote

import androidx.lifecycle.LiveData
import com.gurpster.github.data.entity.Repo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Created by Williaan Souza (dextter) on 09/01/2021
 * Contact williaanlopes@gmail.com
 */

interface WebService {

    @GET("/repositories")
    fun listRepos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Call<List<Repo>> //LiveData<ApiResponse<List<Repo>>>

    @GET("/users/{owner}")
    fun getRepos(): LiveData<ApiResponse<List<Repo>>>

    @GET("/repos/{owner}/{repo}")
    fun getDetails(
        @Path("owner") owner: String,
        @Path("repo") repository: String
    ): LiveData<ApiResponse<Repo>>

}