package com.project.githubuser.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.githubuser.adapter.ListUserAdapter
import com.project.githubuser.databinding.ActivityFavoriteBinding
import com.project.githubuser.db.FavoriteUser
import com.project.githubuser.model.User
import com.project.githubuser.viewModel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var favViewModel: FavoriteViewModel

    private val listUser = ArrayList<User>()
    private val listUserAdapter = ListUserAdapter(listUser)

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        binding.btnBack.setOnClickListener { finish() }

        binding.rvFav.layoutManager = LinearLayoutManager(this)
        binding.rvFav.setHasFixedSize(true)

        listUserAdapter.notifyDataSetChanged()
        binding.rvFav.adapter = listUserAdapter

        favViewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]

        listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: User) {
                val detailAct = Intent(this@FavoriteActivity, DetailActivity::class.java)
                detailAct.putExtra(DetailActivity.EXTRA_DETAIL, user)
                startActivity(detailAct)
            }
        })

        favViewModel.getFavoriteUser()?.observe(this) {
            if ((it != null) && (it.isNotEmpty())) {
                val list = mapList(it)
                listUserAdapter.setUser(list)
                favUser()
            } else {
                notFavUser()
            }
        }
    }

    private fun mapList(users: List<FavoriteUser>): ArrayList<User>{
        val listUsers = ArrayList<User>()
        for (user in users) {
            val userMapped = User(
                user.id,
                user.login,
                user.avatar_url
            )
            listUsers.add(userMapped)
        }
        return listUsers
    }

    private fun favUser() {
        binding.ivFavUser.visibility = View.INVISIBLE
        binding.rvFav.visibility = View.VISIBLE
    }

    private fun notFavUser() {
        binding.ivFavUser.visibility = View.VISIBLE
        binding.rvFav.visibility = View.INVISIBLE
    }
}