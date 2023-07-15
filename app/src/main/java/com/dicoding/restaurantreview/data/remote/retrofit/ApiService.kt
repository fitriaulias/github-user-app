package com.dicoding.restaurantreview.data.remote.retrofit

import com.dicoding.restaurantreview.GithubResponse
import com.dicoding.restaurantreview.ItemsGithub
import com.dicoding.restaurantreview.data.remote.response.DetailUserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getGithub(
        @Query("q") query: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<ItemsGithub>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<ItemsGithub>>
}

