package com.ahmet.movieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ahmet.movieapp.databinding.RowVerticalBinding
import com.ahmet.movieapp.models.Now
import com.ahmet.movieapp.models.Popular
import com.ahmet.movieapp.ui.HomeFragmentDirections
import com.ahmet.movieapp.ui.Type
import com.ahmet.movieapp.utils.downloadFromUrl
import com.ahmet.movieapp.utils.placeHolderProgressBar
import com.bumptech.glide.Glide


class NowAdapter(private val nowList: ArrayList<Now>) : RecyclerView.Adapter<NowAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: RowVerticalBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RowVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.rowTitle.text = nowList[position].title
        holder.binding.rowAverage.text = nowList[position].vote_average.toString()
        holder.binding.rowRelease.text=nowList[position].release_date

        holder.binding.rowImage.downloadFromUrl(nowList[position].poster_path,
            placeHolderProgressBar(holder.itemView.context))

        holder.binding.linearNow.setOnClickListener {
            val action=HomeFragmentDirections.actionHomeFragmentToDetailFragment(nowList[position].id,Type.Now.ordinal)
            Navigation.findNavController(it).navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return nowList.size
    }

    fun updateNowList(updateNowPlaying:List<Now>) {
        nowList.clear()
        nowList.addAll(updateNowPlaying)
        notifyDataSetChanged()
    }
}
