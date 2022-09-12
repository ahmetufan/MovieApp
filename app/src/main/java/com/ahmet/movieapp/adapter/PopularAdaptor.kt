package com.ahmet.movieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ahmet.movieapp.databinding.RowMain2Binding
import com.ahmet.movieapp.models.Popular
import com.ahmet.movieapp.ui.HomeFragmentDirections
import com.ahmet.movieapp.ui.Type
import com.bumptech.glide.Glide

class PopularAdaptor(private val popularList: ArrayList<Popular>) :
    RecyclerView.Adapter<PopularAdaptor.MyViewHolder>() {

    class MyViewHolder(val binding: RowMain2Binding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RowMain2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.rowTitle.text = popularList[position].title
        holder.binding.rowAverage.text = popularList[position].vote_average.toString()

        Glide.with(holder.itemView.context)
            .load("https://image.tmdb.org/t/p/w500" + popularList[position].poster_path)
            .into(holder.binding.rowImage)

        holder.binding.linearPopular.setOnClickListener {
            val action=HomeFragmentDirections.actionHomeFragmentToDetailFragment(popularList[position].id,Type.Populer.ordinal)
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return popularList.size
    }

    fun updatePopularList(updatePopular: List<Popular>) {
        popularList.clear()
        popularList.addAll(updatePopular)
        notifyDataSetChanged()
    }
}
