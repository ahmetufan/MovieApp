package com.ahmet.movieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ahmet.movieapp.databinding.RowMainBinding
import com.ahmet.movieapp.databinding.RowSearchBinding
import com.ahmet.movieapp.models.Movie
import com.ahmet.movieapp.ui.SearchFragmentDirections
import com.ahmet.movieapp.ui.Type
import com.ahmet.movieapp.utils.downloadFromUrl
import com.ahmet.movieapp.utils.placeHolderProgressBar
import com.bumptech.glide.Glide

class SearchAdaptor(private val list: ArrayList<Movie>) :
    RecyclerView.Adapter<SearchAdaptor.MyViewHolder>() {

    class MyViewHolder(val binding: RowSearchBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RowSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.binding.rowSearchTitle.text = list[position].title
        holder.binding.rowsearchRelease.text=list[position].release_date

        holder.binding.rowSearchposterpath.downloadFromUrl(list[position].poster_path,
            placeHolderProgressBar(holder.itemView.context))

        holder.binding.rowSearchbackdroppatch.downloadFromUrl(list[position].backdrop_path,
            placeHolderProgressBar(holder.itemView.context))

        holder.binding.searchlayout.setOnClickListener {

            val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(
                list[position].id,
                Type.Movie.ordinal
            )
            Navigation.findNavController(it).navigate(action)

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
    fun updateMovieListe(newMovieList:List<Movie>) {

        list.clear()
        list.addAll(newMovieList)
        notifyDataSetChanged()
    }

}