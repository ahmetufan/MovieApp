package com.ahmet.movieapp.service.db

import com.ahmet.movieapp.models.Movie
import com.ahmet.movieapp.models.Now
import com.ahmet.movieapp.models.Popular
import javax.inject.Inject

class DaoRepository @Inject constructor(private val dao:MovieDeo) {


    suspend fun getALLMovie() = dao.getAllMovie()
    suspend fun getMovieId(id:Int) = dao.getMovieId(id)
    suspend fun insertAll(movies:Array<Movie>) = dao.insertAll(movies)
    suspend fun deleteMovies() = dao.deleteMovies()


    suspend fun getAllPopular()=dao.getAllPopular()
    suspend fun getPopularId(id:Int)=dao.getPopularId(id)
    suspend fun insertAllPopular(popular:Array<Popular>)=dao.insertAllPopular(popular)
    suspend fun deletePopulars()=dao.deletePopulars()


    suspend fun getAllNow()=dao.getAllNow()
    suspend fun getNowId(id: Int)=dao.getNowId(id)
    suspend fun insertAllNow(now:Array<Now>)=dao.insertAllNow(now)
    suspend fun deleteNow()=dao.deleteNows()


}