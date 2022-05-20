package com.project.githubuser.ui.activity

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.githubuser.adapter.ListUserAdapter
import com.project.githubuser.databinding.ActivityMainBinding
import com.project.githubuser.model.User
import com.project.githubuser.viewModel.UserSearchViewModel

class MainActivity : AppCompatActivity() {

    private var doubleBackToExitPressedOnce: Boolean = false
    private val pressed: Long = 2000

    private lateinit var binding: ActivityMainBinding
    private lateinit var userSearchViewModel: UserSearchViewModel

    private val listUser = ArrayList<User>()
    private val listUserAdapter = ListUserAdapter(listUser)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: User) {
                val detailAct = Intent(this@MainActivity, DetailActivity::class.java)
                detailAct.putExtra(DetailActivity.EXTRA_DETAIL, user)
                startActivity(detailAct)
            }
        })

        binding.btnFavorite.setOnClickListener {
            startActivity(Intent(this, FavoriteActivity::class.java))
        }

        binding.btnSetting.setOnClickListener {
            startActivity(Intent(this, SettingActivity::class.java))
        }

        userSearchViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[UserSearchViewModel::class.java]

        userSearchViewModel.userSearch.observe(this) { users ->
            if ((users != null) && (users.size != 0)) {
                listUserAdapter.setUser(users)
                isFound()
            } else {
                isNotFound()
            }
        }

        userSearchViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val search = binding.search
        search.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                userSearchViewModel.setUser(query)
                binding.search.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        showRecyclerList()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showRecyclerList() {
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.setHasFixedSize(true)
        listUserAdapter.notifyDataSetChanged()
        binding.rvUsers.adapter = listUserAdapter
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            finish()
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show()
        Handler(Looper.getMainLooper()).postDelayed({
            doubleBackToExitPressedOnce = false
        }, pressed)
    }

    private fun isNotFound() {
        binding.rvUsers.visibility = View.INVISIBLE
        binding.ivFind.visibility = View.INVISIBLE
        binding.ivNotFound.visibility = View.VISIBLE
    }

    private fun isFound() {
        binding.rvUsers.visibility = View.VISIBLE
        binding.ivFind.visibility = View.INVISIBLE
        binding.ivNotFound.visibility = View.INVISIBLE
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbMain.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}