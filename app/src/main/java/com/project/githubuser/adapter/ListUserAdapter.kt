package com.project.githubuser.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.githubuser.R
import com.project.githubuser.model.User
import kotlinx.android.synthetic.main.user_list.view.*

class ListUserAdapter(private val listUser: ArrayList<User>):
    RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setUser(users: ArrayList<User>) {
        listUser.clear()
        listUser.addAll(users)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        return (ListViewHolder(
            LayoutInflater.from(viewGroup.context).inflate(R.layout.user_list, viewGroup, false)
        ))
    }
    override fun getItemCount(): Int = listUser.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listUser[position])
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listUser[holder.adapterPosition])
        }
    }

    class ListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(user: User) {
            with(itemView) {
                Glide.with(itemView.context).load(user.avatar_url).circleCrop().into(img_item_photo)
                tv_item_name.text = user.login
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(user: User)
    }
}