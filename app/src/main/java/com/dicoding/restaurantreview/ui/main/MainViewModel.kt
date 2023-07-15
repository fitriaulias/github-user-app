package com.dicoding.restaurantreview.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.dicoding.restaurantreview.GithubResponse
import com.dicoding.restaurantreview.ItemsGithub
import com.dicoding.restaurantreview.data.local.datastore.SettingPreferences
import com.dicoding.restaurantreview.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class MainViewModel(private val pref: SettingPreferences) : ViewModel() {

    private val _github = MutableLiveData<List<ItemsGithub>>()
    val github: LiveData<List<ItemsGithub>> = _github

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "MainViewModel"
    }

    init {
        findGithub("a")
    }

//    fun findGithub(query: String) = githubRepository.getGithubUser(query)

    internal fun findGithub(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getGithub(query)
        client.enqueue(object : retrofit2.Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _github.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }
}