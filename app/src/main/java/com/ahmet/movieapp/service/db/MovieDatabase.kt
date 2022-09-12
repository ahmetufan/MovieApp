package com.ahmet.movieapp.service.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ahmet.movieapp.models.Movie
import com.ahmet.movieapp.models.Now
import com.ahmet.movieapp.models.Popular

@Database(entities = [Movie::class,Popular::class,Now::class], version = 4)
abstract class MovieDatabase: RoomDatabase() {

    abstract fun movieDao():MovieDeo


}