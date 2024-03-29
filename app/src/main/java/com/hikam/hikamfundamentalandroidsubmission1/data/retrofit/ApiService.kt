package com.hikam.hikamfundamentalandroidsubmission1.data.retrofit

import com.hikam.hikamfundamentalandroidsubmission1.data.response.GithubDetailResponse
import com.hikam.hikamfundamentalandroidsubmission1.data.response.FollowersResponseItem
import com.hikam.hikamfundamentalandroidsubmission1.data.response.FollowingResponseItem
import com.hikam.hikamfundamentalandroidsubmission1.data.response.GithubUserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getUser(
        @Query("q") query: String): Call<GithubUserResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<GithubDetailResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<FollowersResponseItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<FollowingResponseItem>>
}