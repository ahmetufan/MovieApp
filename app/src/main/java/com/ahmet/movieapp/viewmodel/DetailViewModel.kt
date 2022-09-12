package com.ahmet.movieapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.ahmet.movieapp.models.Movie
import com.ahmet.movieapp.models.Now
import com.ahmet.movieapp.models.Popular
import com.ahmet.movieapp.service.db.DaoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    application: Application,
    private val dbRepository: DaoRepository
) : BaseViewModel(application) {

    val movies=MutableLiveData<Movie>()
    val populars=MutableLiveData<Popular>()
    val now=MutableLiveData<Now>()

    fun getDetailMovieRoom(id:Int){
        launch {

            val dao=dbRepository.getMovieId(id)
            movies.value=dao
        }
    }

    fun getDetailPopularRoom(id: Int){
        launch {

            val dao2=dbRepository.getPopularId(id)
            populars.value=dao2
        }
    }

    fun getDetailNowRoom(id:Int){
        launch {
            val dao3=dbRepository.getNowId(id)
            now.value=dao3
        }
    }


}