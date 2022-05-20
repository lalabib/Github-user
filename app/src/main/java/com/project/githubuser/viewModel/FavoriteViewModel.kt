package com.project.githubuser.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.project.githubuser.db.FavoriteUser
import com.project.githubuser.db.UserDatabase
import com.project.githubuser.db.UserFavDao

class FavoriteViewModel(application: Application): AndroidViewModel(application) {

    private var userDao: UserFavDao?
    private var userDb: UserDatabase? = UserDatabase.getDatabase(application)

    init {
        userDao = userDb?.userFavDao()
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUser>>? {
        return userDao?.getFavoriteUser()
    }
}