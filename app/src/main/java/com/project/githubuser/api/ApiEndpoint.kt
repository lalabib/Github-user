package com.project.githubuser.api

import com.project.githubuser.BuildConfig
import com.project.githubuser.model.User
import com.project.githubuser.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiEndpoint {
    @GET("search/users")
    @Headers("Authorization: token ${BuildConfig.TOKEN}")
    fun getUser(@Query("q") username: String): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token ${BuildConfig.TOKEN}")
    fun getDetail(@Path("username") username: String): Call<User>

    @GET("users/{username}/following")
    @Headers("Authorization: token ${BuildConfig.TOKEN}")
    fun getFollowing(@Path("username") username: String): Call<ArrayList<User>>

    @GET("users/{username}/followers")
    @Headers("Authorization: token ${BuildConfig.TOKEN}")
    fun getFollower(@Path("username") username: String): Call<ArrayList<User>>
}