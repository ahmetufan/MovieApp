package com.ahmet.movieapp.service.retrofit

import com.ahmet.movieapp.models.MovieResponse
import com.ahmet.movieapp.models.NowResult
import com.ahmet.movieapp.models.PopularResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    //https://api.themoviedb.org/  3/movie/top_rated  ?api_key=d98c5041b9385334b1843046ff779213

    @GET("3/movie/top_rated")
    fun getMovies(
        @Query("api_key") apikey: String,
        @Query("page") page:Int,
        @Query("language") language: String
    ): Single<MovieResponse>

    //https://api.themoviedb.org/  3/movie/popular  ?api_key=d98c5041b9385334b1843046ff779213&language=tr

    @GET("3/movie/popular")
    fun getPopular(
        @Query("api_key") apikey:String,
        @Query("language") language: String
    ) : Single<PopularResponse>

    //https://api.themoviedb.org/  3/movie/now_playing  ?api_key=d98c5041b9385334b1843046ff779213&language=tr

    @GET("3/movie/now_playing")
    fun getNowPlaying(
        @Query("api_key") apikey:String,
        @Query("language") language:String
    ) : Single<NowResult>
}