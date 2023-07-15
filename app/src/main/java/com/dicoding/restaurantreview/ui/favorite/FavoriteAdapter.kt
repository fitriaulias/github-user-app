package com.dicoding.restaurantreview.ui.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.restaurantreview.data.local.entity.FavoriteUser
import com.dicoding.restaurantreview.data.local.helper.FavoriteDiffCallback
import com.dicoding.restaurantreview.databinding.ItemGithubBinding
import com.dicoding.restaurantreview.ui.detail.DetailUser

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    private val listFavorite = ArrayList<FavoriteUser>()

    fun setListFavorite(listFavorite: List<FavoriteUser>) {
        val diffCallback = FavoriteDiffCallback(this.listFavorite, listFavorite)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavorite.clear()
        this.listFavorite.addAll(listFavorite)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGithubBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return FavoriteAdapter.ViewHolder(binding)
    }

    override fun getItemCount(): Int = listFavorite.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val favorite = listFavorite[position]
        Glide.with(viewHolder.itemView.context)
            .load(favorite.avatarUrl) // URL Gambar
            .into(viewHolder.binding.imgItemAvatar)
        viewHolder.binding.tvItemName.text = favorite.username

        viewHolder.itemView.setOnClickListener {
            val intent = Intent(viewHolder.itemView.context, DetailUser::class.java)
            intent.putExtra(DetailUser.EXTRA_LOGIN, favorite.username)
            viewHolder.itemView.context.startActivity(intent)
        }
    }

    class ViewHolder(var binding: ItemGithubBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}