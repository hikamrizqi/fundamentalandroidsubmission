package com.hikam.hikamfundamentalandroidsubmission1.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hikam.hikamfundamentalandroidsubmission1.ui.follow.FollowsFragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    var username: String = ""
    override fun createFragment(position: Int): Fragment {
        val fragment = FollowsFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollowsFragment.ARG_POSITION, position + 1)
            putString(FollowsFragment.ARG_USERNAME, username)
        }
        return fragment
    }
    override fun getItemCount(): Int {
        return 2
    }
}