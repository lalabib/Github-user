package com.project.githubuser.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var id: Int? = 0,
    var login: String? = null,
    var avatar_url: String? = null,
    var name: String? = null,
    var location: String? = null,
    var company: String? = null,
    var public_repos: String? = null,
    var html_url: String? = null
) : Parcelable