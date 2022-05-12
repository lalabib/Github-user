package com.project.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.githubuser.databinding.UserListBinding
import com.project.githubuser.model.User

class ListUserAdapter(private val listUser: ArrayList<User>)
    : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    var onItemClickCallback: OnItemClickCallback? = null

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
            binding.tvItemName.text = user.name
            binding.tvItemCompany.text = user.company
            binding.tvItemLocation.text = user.location

            Glide.with(itemView.context)
                .load(user.photo)
                .circleCrop()
                .into(binding.imgItemPhoto)
            itemView.setOnClickListener { itemClicked.invoke() }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(list: User)
    }
}