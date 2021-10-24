package com.project.githubuser.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.project.githubuser.R
import com.project.githubuser.adapter.SectionsPagerAdapter
import com.project.githubuser.model.User
import com.project.githubuser.viewModel.DetailActViewModel
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.layout_description.*

class DetailActivity: AppCompatActivity() {

    private lateinit var detailActViewModel: DetailActViewModel

    companion object {
        private val TAB_TITLES = intArrayOf(
            R.string.tab_following,
            R.string.tab_follower
        )
        const val EXTRA_DETAIL = "extra_detail"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val user = intent.getParcelableExtra<User>(EXTRA_DETAIL)
        val username = user?.login.toString()

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        sectionsPagerAdapter.username = user?.login
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.hide()
        btn_back.setOnClickListener{ finish() }

        detailActViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(DetailActViewModel::class.java)

        detailActViewModel.setDetail(username)
        detailActViewModel.userDetail.observe(this, { users ->
            Glide.with(this).load(users.avatar_url).circleCrop().into(img_avatar)
            tv_text_name.text = users.name
            tv_text_username.text = users.login
            tv_text_company.text = users.company
            tv_text_location.text = users.location
            tv_text_repo.text = users.public_repos.toString()
        })

        detailActViewModel.isLoading.observe(this, {
            showLoading(it)
        })

        share_icon.setOnClickListener {
            val sendIntent : Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Github User : \n${user?.html_url} ")
                type = "text/html"
            }
            val shareIntent = Intent.createChooser(sendIntent, "Github User")
            startActivity(shareIntent)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        pb_detail.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}