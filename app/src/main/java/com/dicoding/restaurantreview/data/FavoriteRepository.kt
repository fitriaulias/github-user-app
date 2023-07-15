package com.dicoding.restaurantreview.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.restaurantreview.data.local.entity.FavoriteUser
import com.dicoding.restaurantreview.data.local.room.GithubDao
import com.dicoding.restaurantreview.data.local.room.GithubDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class GithubRepository(application: Application){
    private val executors: ExecutorService = Executors.newSingleThreadExecutor()
    private val favoriteDao: GithubDao

    init {
        val database = GithubDatabase.getDatabase(application)
        favoriteDao = database.githubDao()
    }

    fun allFavoriteUser(): LiveData<List<FavoriteUser>> = favoriteDao.getUserList()

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser> = favoriteDao.getFavoriteUserByUsername(username)

    fun insertFavorite(favoriteUser: FavoriteUser) {
        executors.execute {favoriteDao.insertFavorite(favoriteUser)}
    }

    fun deleteFavorite(favoriteUser: FavoriteUser) {
        executors.execute{favoriteDao.delete(favoriteUser)}
    }

}