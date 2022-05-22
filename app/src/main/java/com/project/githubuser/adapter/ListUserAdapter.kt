package com.project.githubuser.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.githubuser.databinding.UserListBinding
import com.project.githubuser.model.User

class ListUserAdapter :
    RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    private val listUser = ArrayList<User>()

    var onItemClickCallback: OnItemClickCallback? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setUser(users: ArrayList<User>) {
        listUser.clear()
        listUser.addAll(users)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            UserListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUser[position]
        holder.bind(user) {
            onItemClickCallback?.onItemClicked(user)
        }
    }

    override fun getItemCount(): Int = listUser.size

    class ListViewHolder(private val binding: UserListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User, itemClicked: () -> Unit) {
            Glide.with(itemView.context)
                .load(user.avatar_url)
                .circleCrop()
                .into(binding.imgItemPhoto)
            binding.tvItemName.text = user.login

            itemView.setOnClickListener { itemClicked.invoke() }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(user: User)
    }
}