package com.example.training.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseModel(
    val message: String? = null,
    val status: String? = null
) : Parcelable