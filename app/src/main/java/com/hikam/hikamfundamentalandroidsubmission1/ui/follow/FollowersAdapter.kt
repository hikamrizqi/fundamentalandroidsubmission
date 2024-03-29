package com.hikam.hikamfundamentalandroidsubmission1.ui.follow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hikam.hikamfundamentalandroidsubmission1.data.response.FollowersResponseItem
import com.hikam.hikamfundamentalandroidsubmission1.databinding.ItemGithubUserBinding

class FollowersAdapter : ListAdapter<FollowersResponseItem, FollowersAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemGithubUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val userFollower = getItem(position)
        holder.bind(userFollower)
    }

    inner class MyViewHolder(private val binding: ItemGithubUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userFollower: FollowersResponseItem) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(userFollower.avatarUrl)
                    .into(ivGihtubUser)
                tvUsername.text = userFollower.login
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FollowersResponseItem>() {
            override fun areItemsTheSame(
                oldItem: FollowersResponseItem,
                newItem: FollowersResponseItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: FollowersResponseItem,
                newItem: FollowersResponseItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}