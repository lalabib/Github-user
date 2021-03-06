package com.project.githubuser.ui.follower

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
import com.project.githubuser.model.User
import com.project.githubuser.databinding.FragmentFollowersBinding
import com.project.githubuser.ui.detail.DetailActivity

class FollowersFragment : Fragment() {

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowersBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(ARG_USERNAME).toString()

        binding.rvFollowers.layoutManager = LinearLayoutManager(activity)
        binding.rvFollowers.setHasFixedSize(true)

        val userAdapter = ListUserAdapter()
        userAdapter.notifyDataSetChanged()
        binding.rvFollowers.adapter = userAdapter

        val followersViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[FollowersViewModel::class.java]

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

        userAdapter.onItemClickCallback = object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: User) {
                val detail = Intent(activity, DetailActivity::class.java)
                detail.putExtra(DetailActivity.EXTRA_DETAIL, user)
                detail.putExtra(DetailActivity.EXTRA_ID,user.id)
                startActivity(detail)
            }
        }
    }

    private fun isFollower() {
        binding.rvFollowers.visibility = View.VISIBLE
        binding.ivNotFollower.visibility = View.INVISIBLE
    }

    private fun isNotFollower() {
        binding.rvFollowers.visibility = View.INVISIBLE
        binding.ivNotFollower.visibility = View.VISIBLE
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbFollowers.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val ARG_USERNAME = "username"

        fun getInstance(username: String): FollowersFragment {
            val fragment = FollowersFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }
}
