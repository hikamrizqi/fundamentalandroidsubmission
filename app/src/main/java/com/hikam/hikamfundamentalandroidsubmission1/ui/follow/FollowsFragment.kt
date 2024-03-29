package com.hikam.hikamfundamentalandroidsubmission1.ui.follow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hikam.hikamfundamentalandroidsubmission1.data.response.FollowersResponseItem
import com.hikam.hikamfundamentalandroidsubmission1.data.response.FollowingResponseItem
import com.hikam.hikamfundamentalandroidsubmission1.databinding.FragmentFollowsBinding

class FollowsFragment : Fragment() {

    private var _binding: FragmentFollowsBinding? = null
    private val binding get() = _binding

    private lateinit var viewModel: FollowsViewModel
    private lateinit var adapter: FollowersAdapter
    private lateinit var adapter2: FollowingAdapter

    private var position: Int = 0
    private var username: String? = null

    companion object {
        const val ARG_POSITION = "arg_position"
        const val ARG_USERNAME = "arg_username"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowsBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FollowersAdapter()
        adapter2 = FollowingAdapter()
        viewModel = ViewModelProvider(requireActivity()).get(FollowsViewModel::class.java)

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }

        binding?.apply {
            val layoutManager = LinearLayoutManager(requireContext())
            val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
            rvFollow.layoutManager = layoutManager
            rvFollow.addItemDecoration(itemDecoration)
            rvFollow.setHasFixedSize(true)
            rvFollow.adapter = adapter
        }

        if (position == 1) {
            viewModel.isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }
            username?.let {
                viewModel.setListFollowers(it)
            }
            viewModel.followers.observe(viewLifecycleOwner) {
                setFollowersData(it)
            }
        } else {
            viewModel.isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }
            username?.let {
                viewModel.setListFollowing(it)
            }
            viewModel.following.observe(viewLifecycleOwner) {
                setFollowingData(it)
            }
        }
    }

    private fun setFollowersData(username: List<FollowersResponseItem>) {
        adapter.submitList(username)
        binding?.rvFollow?.adapter = adapter
    }

    private fun setFollowingData(username: List<FollowingResponseItem>) {
        adapter2.submitList(username)
        binding?.rvFollow?.adapter = adapter2
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding?.progressBar?.visibility = View.VISIBLE
        } else {
            binding?.progressBar?.visibility = View.GONE
        }
    }
}