package com.dicoding.restaurantreview.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.restaurantreview.data.GithubRepository
import com.dicoding.restaurantreview.data.local.entity.FavoriteUser

class FavoriteViewModel(application: Application) : ViewModel()  {
    private val favoriteRepository: GithubRepository = GithubRepository(application)

    fun getFavoriteUser(): LiveData<List<FavoriteUser>> = favoriteRepository.allFavoriteUser()

}