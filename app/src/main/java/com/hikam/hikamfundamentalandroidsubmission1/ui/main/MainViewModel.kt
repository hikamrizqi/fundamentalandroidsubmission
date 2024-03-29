package com.hikam.hikamfundamentalandroidsubmission1.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hikam.hikamfundamentalandroidsubmission1.data.response.GithubUserResponse
import com.hikam.hikamfundamentalandroidsubmission1.data.response.ItemsItem
import com.hikam.hikamfundamentalandroidsubmission1.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listUser = MutableLiveData<List<ItemsItem>>()
    val listUser: LiveData<List<ItemsItem>> = _listUser

    companion object {
        private const val TAG = "MainViewModel"
    }

    init {
        findUser("aaa")
    }

    fun findUser(q: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(q)
        client.enqueue(object : Callback<GithubUserResponse> {
            override fun onResponse(
                call: Call<GithubUserResponse>,
                response: Response<GithubUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUser.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun searchUsers(q: String) {
        findUser(q)
    }
}