package com.project.githubuser.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "favorite_user")
@Parcelize
data class FavoriteUser (
    @PrimaryKey
    var id: Int,
    var login: String,
    var avatar_url: String,
    var name: String,
    var location: String,
    var company: String,
    var public_repos: String,
    var html_url: String
    ):Parcelable
