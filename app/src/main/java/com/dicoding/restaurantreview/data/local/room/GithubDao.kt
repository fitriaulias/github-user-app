package com.dicoding.restaurantreview.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dicoding.restaurantreview.data.local.entity.FavoriteUser

@Dao
interface GithubDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavorite(favoriteUser: FavoriteUser)

    @Delete
    fun delete(favoriteUser: FavoriteUser)

    @Query("SELECT * FROM FavoriteUser")
    fun getUserList(): LiveData<List<FavoriteUser>>

    @Query("SELECT * FROM FavoriteUser WHERE FavoriteUser.username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser>

}