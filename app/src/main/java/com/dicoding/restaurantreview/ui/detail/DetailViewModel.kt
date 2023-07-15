package com.dicoding.restaurantreview.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.restaurantreview.data.GithubRepository
import com.dicoding.restaurantreview.data.local.entity.FavoriteUser
import com.dicoding.restaurantreview.data.remote.response.DetailUserResponse
import com.dicoding.restaurantreview.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Response

class DetailViewModel(application: Application) : ViewModel() {
    private val _detail = MutableLiveData<DetailUserResponse>()
    val detail: LiveData<DetailUserResponse> = _detail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG2 = "DetailViewModel"
    }


    internal fun detailGithub(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : retrofit2.Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detail.value = response.body()
                } else {
                    Log.e(TAG2, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG2, "onFailure: ${t.message.toString()}")
            }
        })
    }

    private val favoriteRepository: GithubRepository = GithubRepository(application)

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser> =
        favoriteRepository.getFavoriteUserByUsername(username)


    fun insertFavorite(favoriteUser: FavoriteUser) {
        favoriteRepository.insertFavorite(favoriteUser)
    }


    fun deleteFavorite(favoriteUser: FavoriteUser) {
        favoriteRepository.deleteFavorite(favoriteUser)
    }

}