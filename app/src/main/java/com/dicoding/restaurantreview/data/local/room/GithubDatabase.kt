package com.dicoding.restaurantreview.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.restaurantreview.data.local.entity.FavoriteUser

@Database(entities = [FavoriteUser::class], version = 3, exportSchema = false)
abstract class GithubDatabase : RoomDatabase() {
    abstract fun githubDao(): GithubDao

    companion object {
        @Volatile
        private var INSTANCE: GithubDatabase? = null

        fun getDatabase(context: Context): GithubDatabase {
            if (INSTANCE == null) {
                synchronized(GithubDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        GithubDatabase::class.java, "github_database")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE as GithubDatabase
        }
    }
}