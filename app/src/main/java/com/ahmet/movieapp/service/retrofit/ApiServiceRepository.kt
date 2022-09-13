package com.ahmet.movieapp.service.retrofit

import com.ahmet.movieapp.models.MovieResponse
import com.ahmet.movieapp.models.NowResult
import com.ahmet.movieapp.models.PopularResponse
import com.ahmet.movieapp.service.retrofit.ApiService
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ApiServiceRepository @Inject constructor(private val apiService: ApiService) {



    fun getDataService(): Single<MovieResponse> {
        return apiService.getMovies("d98c5041b9385334b1843046ff779213",5,"tr")
    }

    fun getPopularService():Single<PopularResponse> {
        return apiService.getPopular("d98c5041b9385334b1843046ff779213","tr")
    }

    fun getNowPlayingService():Single<NowResult> {
        return apiService.getNowPlaying("d98c5041b9385334b1843046ff779213","tr")
    }
}