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
import com.project.githubuser.adapter.ListUserAdapter
import com.project.githubuser.databinding.FragmentFollowingBinding
import com.project.githubuser.model.User
import com.project.githubuser.ui.activity.DetailActivity
import com.project.githubuser.viewModel.FollowingViewModel

class FollowingFragment : Fragment() {

    private lateinit var binding: FragmentFollowingBinding

    companion object {
        private const val ARG_USERNAME = "username"

        fun getUsername(username: String) : FollowingFragment {
            val fragment = FollowingFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }

    private val listUser = ArrayList<User>()
    private lateinit var followingViewModel: FollowingViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentFollowingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(ARG_USERNAME).toString()

        binding.rvFollowing.layoutManager = LinearLayoutManager(activity)
        binding.rvFollowing.setHasFixedSize(true)

        val userAdapter = ListUserAdapter(listUser)
        userAdapter.notifyDataSetChanged()
        binding.rvFollowing.adapter = userAdapter

        followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(FollowingViewModel::class.java)

        followingViewModel.setFollowing(username)
        followingViewModel.listFollowing.observe(requireActivity(), { users ->
            if ((users != null) && (users.size != 0)) {
                userAdapter.setUser(users)
                isFollowing()
            } else {
                isNotFollowing()
            }
        })

        followingViewModel.isLoading.observe(requireActivity(), {
            showLoading(it)
        })

        userAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: User) {
                val detail = Intent(activity, DetailActivity::class.java)
                detail.putExtra(DetailActivity.EXTRA_DETAIL, user)
                startActivity(detail)
            }
        })
    }

    private fun isFollowing() {
        binding.rvFollowing.visibility = View.VISIBLE
        binding.ivNotFollowing.visibility = View.INVISIBLE
    }

    private fun isNotFollowing() {
        binding.rvFollowing.visibility = View.INVISIBLE
        binding.ivNotFollowing.visibility = View.VISIBLE
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbFollowing.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}