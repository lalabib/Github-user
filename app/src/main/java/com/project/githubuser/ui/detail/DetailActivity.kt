package com.project.githubuser.ui.detail

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
import com.project.githubuser.databinding.ActivityDetailBinding
import com.project.githubuser.databinding.LayoutDescriptionBinding
import com.project.githubuser.model.User

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var bindingDesc: LayoutDescriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindingDesc = binding.incDesc

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

        binding.btnBack.setOnClickListener { finish() }

        val detailActViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[DetailActViewModel::class.java]

        detailActViewModel.setDetail(username)
        detailActViewModel.userDetail.observe(this) { users ->
            Glide.with(this)
                .load(users.avatar_url)
                .circleCrop()
                .into(binding.imgAvatar)
            binding.apply {
                tvTextName.text = users.name
                tvTextUsername.text = users.login
            }
            bindingDesc.apply {
                tvTextCompany.text = users.company
                tvTextLocation.text = users.location
                tvTextRepo.text = users.public_repos.toString()
            }
        }

        detailActViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        bindingDesc.shareIcon.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Github User : \n${user?.html_url} ")
                type = "text/html"
            }
            val shareIntent = Intent.createChooser(sendIntent, "Github User")
            startActivity(shareIntent)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbDetail.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        private val TAB_TITLES = intArrayOf(
            R.string.tab_following,
            R.string.tab_follower
        )
        const val EXTRA_DETAIL = "extra_detail"
    }
}