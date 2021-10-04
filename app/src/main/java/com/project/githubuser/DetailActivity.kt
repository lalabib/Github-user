package com.project.githubuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.bumptech.glide.Glide
import com.project.githubuser.model.User

class DetailActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.title = "Detail User"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val imgAvatar = findViewById<ImageView>(R.id.img_avatar)
        val tvName = findViewById<TextView>(R.id.tv_text_name)
        val tvUname = findViewById<TextView>(R.id.tv_text_username)
        val tvCompany = findViewById<TextView>(R.id.tv_text_company)
        val tvLocation = findViewById<TextView>(R.id.tv_text_location)
        val tvRepo = findViewById<TextView>(R.id.tv_text_repo)
        val tvFollowing = findViewById<TextView>(R.id.tv_following)
        val tvFollowers = findViewById<TextView>(R.id.tv_follower)

        val user = intent.getParcelableExtra<User>(EXTRA_USER)

        Glide.with(this)
            .load(user?.photo)
            .circleCrop()
            .into(imgAvatar)
        tvName.text = user?.name
        tvUname.text = user?.username
        tvCompany.text = user?.company
        tvLocation.text = user?.location
        tvRepo.text = user?.repository
        tvFollowing.text = user?.following
        tvFollowers.text = user?.follower

        val shareIcon = findViewById<CheckBox>(R.id.share_icon)
        shareIcon.setOnClickListener {
            val sendIntent : Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Github User \n\nName : ${user?.name} \nUsername : ${user?.username} " +
                        "\nCompany : ${user?.company} \nLocation : ${user?.location} " +
                        "\n${user?.repository} \n${user?.following} \n${user?.follower}")
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

    companion object{
        const val EXTRA_USER = "extra_user"
    }
}