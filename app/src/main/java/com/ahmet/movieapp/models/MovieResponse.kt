package com.ahmet.movieapp.models

import android.os.Parcelable

data class MovieResponse(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)