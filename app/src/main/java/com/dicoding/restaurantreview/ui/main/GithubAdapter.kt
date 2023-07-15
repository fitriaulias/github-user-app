package com.dicoding.restaurantreview.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.restaurantreview.ItemsGithub
import com.dicoding.restaurantreview.ui.detail.DetailUser.Companion.EXTRA_LOGIN
import com.dicoding.restaurantreview.databinding.ItemGithubBinding
import com.dicoding.restaurantreview.ui.detail.DetailUser

class GithubAdapter(
    private val listGithub: List<ItemsGithub>,
) : RecyclerView.Adapter<GithubAdapter.ViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGithubBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val github = listGithub[position]
        Glide.with(viewHolder.itemView.context)
            .load(github.avatarUrl) // URL Gambar
            .into(viewHolder.binding.imgItemAvatar)
        viewHolder.binding.tvItemName.text = github.login

        viewHolder.itemView.setOnClickListener {
            val intent = Intent(viewHolder.itemView.context, DetailUser::class.java)
            intent.putExtra(EXTRA_LOGIN, github.login)
            viewHolder.itemView.context.startActivity(intent)
        }

    }
    override fun getItemCount() = listGithub.size

    class ViewHolder(var binding: ItemGithubBinding) : RecyclerView.ViewHolder(binding.root) {
    }
}