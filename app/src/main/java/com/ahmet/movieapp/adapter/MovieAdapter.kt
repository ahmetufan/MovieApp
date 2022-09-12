package com.ahmet.movieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ahmet.movieapp.databinding.RowMainBinding
import com.ahmet.movieapp.models.Movie
import com.ahmet.movieapp.ui.HomeFragmentDirections
import com.ahmet.movieapp.ui.Type
import com.bumptech.glide.Glide

class MovieAdapter(private val movieList:ArrayList<Movie>):RecyclerView.Adapter<MovieAdapter.MyViewHolder>() {

    class MyViewHolder(val binding:RowMainBinding):RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding=RowMainBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.rowTitle.text=movieList[position].title
        //holder.binding.rowRelease.text=movieList[position].release_date
        holder.binding.rowAverage.text=movieList[position].vote_average.toString()

        Glide.with(holder.itemView.context)
            .load("https://image.tmdb.org/t/p/w500"+ movieList[position].poster_path)
            .into(holder.binding.rowImage)

        holder.binding.linearMovie.setOnClickListener {

            val action=HomeFragmentDirections.actionHomeFragmentToDetailFragment(movieList[position].id,Type.Movie.ordinal)
            Navigation.findNavController(it).navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun updateMovieListe(newMovieList:List<Movie>) {

        movieList.clear()
        movieList.addAll(newMovieList)
        notifyDataSetChanged()
    }
}