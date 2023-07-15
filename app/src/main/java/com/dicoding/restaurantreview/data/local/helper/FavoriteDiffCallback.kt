package com.dicoding.restaurantreview.data.local.helper

import androidx.recyclerview.widget.DiffUtil
import com.dicoding.restaurantreview.data.local.entity.FavoriteUser

class FavoriteDiffCallback(private val oldFavoriteList: List<FavoriteUser>, private val newFavoriteList: List<FavoriteUser>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldFavoriteList.size

    override fun getNewListSize(): Int = newFavoriteList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFavoriteList[oldItemPosition].id == newFavoriteList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFavorite = oldFavoriteList[oldItemPosition]
        val newFavorite = newFavoriteList[newItemPosition]
        return oldFavorite.username == newFavorite.username && oldFavorite.avatarUrl == newFavorite.avatarUrl
    }
}