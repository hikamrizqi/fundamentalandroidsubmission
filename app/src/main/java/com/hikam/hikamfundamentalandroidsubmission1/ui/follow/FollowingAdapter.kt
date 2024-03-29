package com.hikam.hikamfundamentalandroidsubmission1.ui.follow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hikam.hikamfundamentalandroidsubmission1.data.response.FollowingResponseItem
import com.hikam.hikamfundamentalandroidsubmission1.databinding.ItemGithubUserBinding

class FollowingAdapter : ListAdapter<FollowingResponseItem, FollowingAdapter.MyViewHolder>(
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
        fun bind(userFollower: FollowingResponseItem) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(userFollower.avatarUrl)
                    .into(ivGihtubUser)
                tvUsername.text = userFollower.login
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FollowingResponseItem>() {
            override fun areItemsTheSame(
                oldItem: FollowingResponseItem,
                newItem: FollowingResponseItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: FollowingResponseItem,
                newItem: FollowingResponseItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}