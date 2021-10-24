package com.project.githubuser.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.project.githubuser.ui.fragment.FollowersFragment
import com.project.githubuser.ui.fragment.FollowingFragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    var username: String? = null

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowingFragment.getUsername(username.toString())
            1 -> fragment = FollowersFragment.getUsername(username.toString())
       }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}
