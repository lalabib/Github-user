package com.project.githubuser.ui

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
import com.project.githubuser.R
import com.project.githubuser.adapter.ListUserAdapter
import com.project.githubuser.model.User
import com.project.githubuser.viewModel.UserSearchViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var doubleBackToExitPressedOnce: Boolean = false
    private val pressed:Long = 2000
    private lateinit var userSearchViewModel: UserSearchViewModel
    private val listUser = ArrayList<User>()
    private val listUserAdapter = ListUserAdapter(listUser)

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        rv_users.layoutManager = LinearLayoutManager(this)
        rv_users.setHasFixedSize(true)
        listUserAdapter.notifyDataSetChanged()
        rv_users.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: User) {
                val detailAct = Intent(this@MainActivity, DetailActivity::class.java)
                detailAct.putExtra(DetailActivity.EXTRA_DETAIL, user)
                startActivity(detailAct)
            }
        })

        userSearchViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[UserSearchViewModel::class.java]

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
        search.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                userSearchViewModel.setUser(query)
                search.clearFocus()
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
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
        rv_users.visibility = View.INVISIBLE
        iv_find.visibility = View.INVISIBLE
        iv_notFound.visibility = View.VISIBLE
    }

    private fun isFound() {
        rv_users.visibility = View.VISIBLE
        iv_find.visibility = View.INVISIBLE
        iv_notFound.visibility = View.INVISIBLE
    }

    private fun showLoading(isLoading: Boolean) {
        pb_main.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}