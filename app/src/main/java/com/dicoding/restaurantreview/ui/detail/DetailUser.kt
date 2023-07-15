package com.dicoding.restaurantreview.ui.detail

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.bumptech.glide.Glide
import com.dicoding.restaurantreview.R
import com.dicoding.restaurantreview.data.local.datastore.SettingPreferences
import com.dicoding.restaurantreview.data.local.entity.FavoriteUser
import com.dicoding.restaurantreview.data.remote.response.DetailUserResponse
import com.dicoding.restaurantreview.databinding.ActivityDetailUserBinding
import com.dicoding.restaurantreview.ui.ViewModelFactory
import com.dicoding.restaurantreview.ui.detail.follow.SectionsPagerAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator


class DetailUser : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private val detailViewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(application, SettingPreferences.getInstance(dataStore))
    }
    private var favoriteButton: MenuItem? = null
    private var favoriteUser: FavoriteUser? = null

    companion object{
        const val EXTRA_LOGIN = "key_login"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_LOGIN)
        if (username != null) {
            detailViewModel.detailGithub(username)

        }

        detailViewModel.detail.observe(this) {
            binding.tvDetailUsername.text = it.login
            binding.tvDetailName.text = it.name
            Glide.with(this)
                .load(it.avatarUrl)
                .into(binding.imgAvatar)
            binding.tvDetailFollowersNumber.text = it.followers.toString()
            binding.tvDetailFollwingNumber.text = it.following.toString()
        }


        detailViewModel.isLoading.observe(this){
            showLoading(it)
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        if (username != null) {
            sectionsPagerAdapter.username = username
            binding.viewPager.adapter = sectionsPagerAdapter
        }
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        if (username != null) {
            setupFavoriteButton(username)
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val username = intent.getStringExtra(EXTRA_LOGIN)
        when (item.itemId) {
            R.id.favorite -> {
                if (username != null) {
                    toggleFavorite(username)
                }
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupFavoriteButton(username: String) {
        detailViewModel.getFavoriteUserByUsername(username).observe(this) { it ->
            favoriteUser = it
            val favoriteButtonDrawable = if (it != null) {
                R.drawable.baseline_favorite_24
            } else {
                R.drawable.baseline_favorite_border_24
            }
            favoriteButton?.icon = AppCompatResources.getDrawable(this, favoriteButtonDrawable)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        favoriteButton = menu?.findItem(R.id.favorite) ?: return false
        return true
    }

    private fun toggleFavorite(username: String) {
        val detailUser = DetailUserResponse()
        if(favoriteUser != null) {
            detailViewModel.deleteFavorite(favoriteUser!!)
            favoriteUser = null
        } else {
            val newUser = FavoriteUser(username, detailUser.avatarUrl, detailUser.id ?: 0)
            detailViewModel.insertFavorite(newUser)
            favoriteUser = newUser
        }
    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}