package com.gurpster.github.data.remote

import com.gurpster.github.data.entity.Repo
import com.gurpster.github.data.entity.Search
import com.gurpster.github.data.entity.User
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Created by Williaan Souza (dextter) on 09/01/2021
 * Contact williaanlopes@gmail.com
 */

interface WebService {

    @GET("/users/{name}")
    suspend fun getUserAsync(
        @Path("name") name: String
    ): User?

    @GET("/repos/{owner}/{repo}")
    suspend fun getRepoAsync(
        @Path("owner") owner: String,
        @Path("repo") repository: String
    ): Repo

    @GET("/repositories")
    fun listRepos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Call<List<Repo>>

    @GET("/search/repositories")
    fun search(
        @Query("q") name: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Call<Search>

}