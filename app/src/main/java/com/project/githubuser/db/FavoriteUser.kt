package com.project.githubuser.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "favorite_user")
@Parcelize
data class FavoriteUser (
    val login: String,
    @PrimaryKey
    var id: Int,
    val avatar_url: String
    ):Parcelable
