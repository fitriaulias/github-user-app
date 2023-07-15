package com.dicoding.restaurantreview.ui.detail.follow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.restaurantreview.ItemsGithub
import com.dicoding.restaurantreview.databinding.FragmentFollowBinding
import com.dicoding.restaurantreview.ui.main.GithubAdapter

class FollowFragment : Fragment() {
    private lateinit var binding: FragmentFollowBinding
    private lateinit var viewModel: FollowViewModel

    companion object {
        const val ARG_POSITION = "section_number"
        const val ARG_USERNAME = "section_username"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvFragment.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFragment.setHasFixedSize(true)

        val position = arguments?.getInt(ARG_POSITION, 0) ?: 0
        val username = arguments?.getString(ARG_USERNAME, "") ?: ""

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowViewModel::class.java)

        if (position == 1){
            viewModel.followers.observe(viewLifecycleOwner) {
                showFollowData(it)
            }
            viewModel.detailFollowers(username)
        } else {
            viewModel.following.observe(viewLifecycleOwner) {
                showFollowData(it)
            }
            viewModel.detailFollowing(username)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun showFollowData(listGithub: List<ItemsGithub>) {
        val adapter = GithubAdapter(listGithub)
        binding.rvFragment.adapter = adapter

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}