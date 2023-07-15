package com.dicoding.restaurantreview.ui.detail.follow

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.restaurantreview.ItemsGithub
import com.dicoding.restaurantreview.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Response

class FollowViewModel : ViewModel() {

    private val _followers = MutableLiveData<List<ItemsGithub>>()
    val followers: LiveData<List<ItemsGithub>> = _followers

    private val _following = MutableLiveData<List<ItemsGithub>>()
    val following: LiveData<List<ItemsGithub>> = _following

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG_FOLLOWERS = "FollowersViewModel"
        private const val TAG_FOLLOWING = "FollowingViewModel"
    }


    fun detailFollowers(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : retrofit2.Callback<List<ItemsGithub>> {
            override fun onResponse(
                call: Call<List<ItemsGithub>>,
                response: Response<List<ItemsGithub>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _followers.value = response.body()
                } else {
                    Log.e(TAG_FOLLOWERS, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsGithub>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG_FOLLOWERS, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun detailFollowing(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : retrofit2.Callback<List<ItemsGithub>> {
            override fun onResponse(
                call: Call<List<ItemsGithub>>,
                response: Response<List<ItemsGithub>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _following.value = response.body()
                } else {
                    Log.e(TAG_FOLLOWING, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsGithub>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG_FOLLOWING, "onFailure: ${t.message.toString()}")
            }

        })
    }

}