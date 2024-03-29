package com.hikam.hikamfundamentalandroidsubmission1.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hikam.hikamfundamentalandroidsubmission1.data.response.ItemsItem
import com.hikam.hikamfundamentalandroidsubmission1.databinding.ActivityMainBinding
import com.hikam.hikamfundamentalandroidsubmission1.ui.detail.DetailGithubUserActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: GithubUserAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = GithubUserAdapter()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.apply {
            rvGithubUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvGithubUser.setHasFixedSize(true)
            rvGithubUser.adapter = adapter

            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { textView, actionId, event ->
                searchBar.setText(searchView.text)
                viewModel.searchUsers(searchView.text.toString())
                searchView.hide()
                false
            }
        }

        viewModel.listUser.observe(this) {
            setUsersData(it)
        }
        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        adapter.setOnItemClickCallback(object : GithubUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                Intent(this@MainActivity, DetailGithubUserActivity::class.java).also {
                    it.putExtra(DetailGithubUserActivity.EXTRA_USERNAME, data.login)
                    startActivity(it)
                }
            }
        })
    }

    private fun setUsersData(userdata: List<ItemsItem>) {
        adapter.submitList(userdata)
        binding.rvGithubUser.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}