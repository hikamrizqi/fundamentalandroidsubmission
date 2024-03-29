package com.hikam.hikamfundamentalandroidsubmission1.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hikam.hikamfundamentalandroidsubmission1.data.response.GithubDetailResponse
import com.hikam.hikamfundamentalandroidsubmission1.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _detailUser = MutableLiveData<GithubDetailResponse>()
    val detailUser: LiveData<GithubDetailResponse> = _detailUser

    companion object {
        private const val TAG = "DetailViewModel"
    }

    fun setDetailUser(user: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(user)
        client.enqueue(object : Callback<GithubDetailResponse> {
            override fun onResponse(
                call: Call<GithubDetailResponse>,
                response: Response<GithubDetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailUser.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubDetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getDetailUser(): MutableLiveData<GithubDetailResponse> {
        return _detailUser
    }
}