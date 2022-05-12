package com.project.githubuser.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.githubuser.R
import com.project.githubuser.adapter.ListUserAdapter
import com.project.githubuser.databinding.ActivityMainBinding
import com.project.githubuser.model.User
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private var doubleBackToExitPressedOnce: Boolean = false
    private lateinit var binding: ActivityMainBinding

    private val list = ArrayList<User>()
    private val onBackPressed:Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvUsers.setHasFixedSize(true)
        list.addAll(listUsers)
        showRecyclerList()
    }

    private val listUsers: ArrayList<User>
        get() {
            val dataPhoto = resources.obtainTypedArray(R.array.avatar)
            dataPhoto.recycle()
            val dataName = resources.getStringArray(R.array.name)
            val dataCompany = resources.getStringArray(R.array.company)
            val dataLocation = resources.getStringArray(R.array.location)
            val dataUsername = resources.getStringArray(R.array.username)
            val dataFollower = resources.getStringArray(R.array.followers)
            val dataFollowing = resources.getStringArray(R.array.following)
            val dataRepository = resources.getStringArray(R.array.repository)
            val listUser = ArrayList<User>()
            for (i in dataName.indices) {
                val user = User(
                    dataPhoto.getResourceId(i, -1), dataName[i], dataCompany[i],
                    dataLocation[i], dataUsername[i], dataFollower[i], dataFollowing[i],
                    dataRepository[i]
                )
                listUser.add(user)
            }
            return listUser
        }

    private fun showRecyclerList() {
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        val listUserAdapter = ListUserAdapter(list)
        binding.rvUsers.adapter = listUserAdapter


        listUserAdapter.onItemClickCallback = object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(list: User) {
                val moveDetail = Intent(this@MainActivity, DetailActivity::class.java)
                moveDetail.putExtra(DetailActivity.EXTRA_USER, list)
                startActivity(moveDetail)
            }
        }
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            finish()
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, R.string.press_again, Toast.LENGTH_SHORT).show()
        Handler(Looper.getMainLooper()).postDelayed({
            doubleBackToExitPressedOnce = false
        }, onBackPressed)
    }
}