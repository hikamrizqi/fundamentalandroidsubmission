package com.hikam.hikamfundamentalandroidsubmission1.ui.detail

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.hikam.hikamfundamentalandroidsubmission1.R
import com.hikam.hikamfundamentalandroidsubmission1.databinding.ActivityDetailUserBinding

class DetailGithubUserActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USERNAME = "extra_username"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.table_text_left,
            R.string.table_text_right
        )
    }

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailViewModel
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tabLayout = binding.tabs
        val username = intent.getStringExtra(EXTRA_USERNAME)
        val colorStateList = ColorStateList.valueOf(Color.BLACK)
        tabLayout.setTabTextColors(colorStateList)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)

        username?.let { viewModel.setDetailUser(it) }
        viewModel.getDetailUser().observe(this) {
            if (it != null) {
                binding.apply {
                    tvGithubUsername.text = it.name
                    tvGithubNickname.text = it.login
                    tvFollowers.text = "${it.followers} Followers"
                    tvFollowing.text = "${it.following} Following"
                    Glide.with(this@DetailGithubUserActivity)
                        .load(it.avatar_url)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(ivGithubUser)
                }
            }
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        username?.let {
            sectionsPagerAdapter.username = it
        }
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}