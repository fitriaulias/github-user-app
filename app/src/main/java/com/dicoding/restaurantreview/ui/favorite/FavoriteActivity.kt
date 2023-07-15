package com.dicoding.restaurantreview.ui.favorite

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.restaurantreview.ItemsGithub
import com.dicoding.restaurantreview.data.local.datastore.SettingPreferences
import com.dicoding.restaurantreview.data.local.entity.FavoriteUser
import com.dicoding.restaurantreview.databinding.ActivityFavoriteBinding
import com.dicoding.restaurantreview.ui.ViewModelFactory
import com.dicoding.restaurantreview.ui.main.GithubAdapter


class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: FavoriteAdapter

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private val favoriteViewModel by viewModels<FavoriteViewModel> {
        ViewModelFactory.getInstance(application, SettingPreferences.getInstance(dataStore))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        adapter = FavoriteAdapter()

        favoriteViewModel.getFavoriteUser().observe(this) { users: List<FavoriteUser> ->
            val items = arrayListOf<ItemsGithub>()
            users.map {
                val item = ItemsGithub(id = it.id, login = it.username, avatarUrl = it.avatarUrl)
                items.add(item)
            }
            adapter.setListFavorite(users)
            binding.rvFavorite.adapter = GithubAdapter(items)
        }
    }

}