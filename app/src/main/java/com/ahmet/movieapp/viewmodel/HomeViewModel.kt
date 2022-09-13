package com.ahmet.movieapp.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.ahmet.movieapp.models.*
import com.ahmet.movieapp.service.db.DaoRepository
import com.ahmet.movieapp.service.db.MovieDatabase
import com.ahmet.movieapp.service.retrofit.ApiServiceRepository
import com.ahmet.movieapp.utils.CustomSharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val apiRepository: ApiServiceRepository,
    private val dbRepository: DaoRepository,
    application: Application
) : BaseViewModel(application) {

    private val compositeDisposable = CompositeDisposable()

    private var customPreferences = CustomSharedPreferences(getApplication())
    private var refreshTime = 10 * 60 * 1000 * 1000 * 1000L


    val moviedata = MutableLiveData<List<Movie>>()

    val popularData = MutableLiveData<List<Popular>>()

    val nowPlayingData = MutableLiveData<List<Now>>()

    fun refreshData(){
        getDataFromAPI()
        getDataPopularFromAPI()
        getDataNowFromAPI()
    }


    fun getLiveData() {

        val updateTime = customPreferences.getTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            getMovieSQLite()

        } else {
            getDataFromAPI()
        }
    }

    fun getPopularLiveData() {
        val updateTime2 = customPreferences.getTime()
        if (updateTime2 != null && updateTime2 != 0L && System.nanoTime() - updateTime2 < refreshTime) {
            getPopularSQLite()

        } else {
            getDataPopularFromAPI()
        }
    }

    fun getNowPlayingLiveData() {
        val updateTime4 = customPreferences.getTime()
        if (updateTime4 != null && updateTime4 != 0L && System.nanoTime() - updateTime4 < refreshTime) {
            getNowSQLite()

        } else {
            getDataNowFromAPI()
        }
    }

    fun searchDatabse(search:String):LiveData<List<Movie>>{
        val searchDao=dbRepository.searchDatabaseMovie(search)
        return searchDao.asLiveData()
    }


    private fun getDataFromAPI() {

        compositeDisposable.add(
            apiRepository.getDataService()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MovieResponse>() {
                    override fun onSuccess(t: MovieResponse) {
                        movieInSQLite(t.results)
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
        )
    }

    private fun showMovie(movieList: List<Movie>) {
        moviedata.value = movieList
    }

    private fun movieInSQLite(movieList: List<Movie>) {
        launch {

            dbRepository.deleteMovies()
            val listlong = dbRepository.insertAll(*movieList.toTypedArray())
            var i = 0
            while (i < movieList.size) {
                movieList[i].id = listlong[i].toInt()
                i++
            }
            showMovie(movieList)
        }
        customPreferences.saveTime(System.nanoTime())
    }

    private fun getMovieSQLite() {
        launch {
            val movies = dbRepository.getALLMovie()
            showMovie(movies)
        }
    }

    private fun getDataPopularFromAPI() {

        compositeDisposable.add(
            apiRepository.getPopularService()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<PopularResponse>() {
                    override fun onSuccess(t: PopularResponse) {
                        popularInSQLite(t.results)
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
        )
    }

    private fun showPopular(popular: List<Popular>) {
        popularData.value = popular
    }

    private fun popularInSQLite(popular: List<Popular>) {
        launch {
            dbRepository.deletePopulars()
            val listlong = dbRepository.insertAllPopular(*popular.toTypedArray())
            var i = 0
            while (i < popular.size) {
                popular[i].id = listlong[i].toInt()
                i++
            }
            showPopular(popular)
        }
        customPreferences.saveTime(System.nanoTime())
    }

    private fun getPopularSQLite() {
        launch {
            val populars = dbRepository.getAllPopular()
            showPopular(populars)
        }
    }

    private fun getDataNowFromAPI() {

        compositeDisposable.add(
            apiRepository.getNowPlayingService()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<NowResult>() {
                    override fun onSuccess(t: NowResult) {
                        nowInSQLite(t.results)
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }

                })
        )
    }

    private fun showNow(now: List<Now>) {
        nowPlayingData.value = now
    }

    private fun nowInSQLite(now: List<Now>) {
        launch {
            dbRepository.deleteNow()
            val listlong = dbRepository.insertAllNow(*now.toTypedArray())
            var i = 0
            while (i < now.size) {
                now[i].id = listlong[i].toInt()
                i++
            }
            showNow(now)
        }
        customPreferences.saveTime(System.nanoTime())
    }

    private fun getNowSQLite() {
        launch {
            val now = dbRepository.getAllNow()
            showNow(now)
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}