package com.example.training.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id: String? = null,
    val email: String? = null,
    val password: String? = null,
    val token: String? = null
    ): Parcelable