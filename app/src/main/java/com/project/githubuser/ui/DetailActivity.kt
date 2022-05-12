package com.project.githubuser.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.project.githubuser.R
import com.project.githubuser.databinding.ActivityDetailBinding
import com.project.githubuser.databinding.LayoutDescriptionBinding
import com.project.githubuser.databinding.LayoutStatisticBinding
import com.project.githubuser.model.User

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var bindingDesc: LayoutDescriptionBinding
    private lateinit var bindingStatistic: LayoutStatisticBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindingDesc = binding.includeDescription
        bindingStatistic = binding.includeStatistic

        supportActionBar?.title = resources.getString(R.string.title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val user = intent.getParcelableExtra<User>(EXTRA_USER)

        Glide.with(this)
            .load(user?.photo)
            .circleCrop()
            .into(binding.imgAvatar)
        binding.tvTextName.text = user?.name
        binding.tvTextUsername.text = user?.username
        bindingDesc.tvTextCompany.text = user?.company
        bindingDesc.tvTextLocation.text = user?.location
        bindingDesc.tvTextRepo.text = user?.repository
        bindingStatistic.tvFollowing.text = user?.following
        bindingStatistic.tvFollower.text = user?.follower

        bindingDesc.shareIcon.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT,
                    "Github User \n\nName : ${user?.name} \nUsername : ${user?.username} " +
                            "\nCompany : ${user?.company} \nLocation : ${user?.location} " +
                            "\n${user?.repository} \n${user?.following} \n${user?.follower}"
                )
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, "Github User")
            startActivity(shareIntent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val EXTRA_USER = "extra_user"
    }
}