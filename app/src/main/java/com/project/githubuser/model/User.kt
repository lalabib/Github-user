package com.project.githubuser.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var photo: Int,
    var name: String,
    var company: String,
    var location: String,
    var username: String,
    var follower: String,
    var following: String,
    var repository: String
) : Parcelable
