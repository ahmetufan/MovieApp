package com.ahmet.movieapp.models

data class NowResult(
    val dates: Dates,
    val page: Int,
    val results: List<Now>,
    val total_pages: Int,
    val total_results: Int
)