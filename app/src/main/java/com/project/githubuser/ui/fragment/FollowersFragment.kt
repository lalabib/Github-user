package com.project.githubuser.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.githubuser.R
import com.project.githubuser.adapter.ListUserAdapter
import com.project.githubuser.model.User
import com.project.githubuser.ui.DetailActivity
import com.project.githubuser.viewModel.FollowersViewModel
import kotlinx.android.synthetic.main.fragment_followers.*

class FollowersFragment : Fragment() {

    companion object {
        private const val ARG_USERNAME = "username"

        fun getUsername(username: String) : FollowersFragment {
            val fragment = FollowersFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }

    private val listUser = ArrayList<User>()
    private lateinit var followersViewModel: FollowersViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(ARG_USERNAME).toString()

        rv_followers.layoutManager = LinearLayoutManager(activity)
        rv_followers.setHasFixedSize(true)

        val userAdapter = ListUserAdapter(listUser)
        userAdapter.notifyDataSetChanged()
        rv_followers.adapter = userAdapter

        followersViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FollowersViewModel::class.java]

        followersViewModel.setFollower(username)
        followersViewModel.listFollowers.observe(requireActivity()) { users ->
            if ((users != null) && (users.size != 0)) {
                userAdapter.setUser(users)
                isFollower()
            } else {
                isNotFollower()
            }
        }

        followersViewModel.isLoading.observe(requireActivity()) {
            showLoading(it)
        }

        userAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: User) {
                val detail = Intent(activity, DetailActivity::class.java)
                detail.putExtra(DetailActivity.EXTRA_DETAIL, user)
                startActivity(detail)
            }
        })
    }

    private fun isFollower() {
        rv_followers.visibility = View.VISIBLE
        iv_notFollower.visibility = View.INVISIBLE
    }

    private fun isNotFollower() {
        rv_followers.visibility = View.INVISIBLE
        iv_notFollower.visibility = View.VISIBLE
    }

    private fun showLoading(isLoading: Boolean) {
        pb_followers.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}