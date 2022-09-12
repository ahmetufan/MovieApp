package com.ahmet.movieapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.ahmet.movieapp.databinding.FragmentDetailBinding
import com.ahmet.movieapp.viewmodel.DetailViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel:DetailViewModel by viewModels()
    private val args by navArgs<DetailFragmentArgs>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imgbackHome.setOnClickListener {
            requireActivity().onBackPressed()
        }

        when(args.typ) {
            Type.Movie.ordinal -> {
                viewModel.getDetailMovieRoom(args.uUid)
                observeMovieData()
            }
            Type.Populer.ordinal -> {
                viewModel.getDetailPopularRoom(args.uUid)
                observePopularData()
            }
            Type.Now.ordinal -> {
                viewModel.getDetailNowRoom(args.uUid)
                observeNowData()
            }
        }

    }

    private fun observePopularData() {
        viewModel.populars.observe(viewLifecycleOwner, Observer { popular ->
            popular.let {
                binding.detailsTitle.text=popular.title
                binding.detailsOverview.text=popular.overview
                binding.rowDetailAverage.text=popular.vote_average.toString()
                binding.rowDetailVoteCount.text=popular.vote_count.toString()
                binding.rowDetailsRelease.text=popular.release_date

                Glide.with(requireContext())
                    .load("https://image.tmdb.org/t/p/w500"+popular.backdrop_path)
                    .into(binding.imageDetailsBackdropPath)

                Glide.with(requireContext())
                    .load("https://image.tmdb.org/t/p/w500"+popular.poster_path)
                    .into(binding.imageDetailsposterpath)
            }
        })
    }
    private fun observeNowData(){
        viewModel.now.observe(viewLifecycleOwner, Observer { now ->
            now.let {
                binding.detailsTitle.text=now.title
                binding.detailsOverview.text=now.overview
                binding.rowDetailAverage.text=now.vote_average.toString()
                binding.rowDetailVoteCount.text=now.vote_count.toString()
                binding.rowDetailsRelease.text=now.release_date

                Glide.with(requireContext())
                    .load("https://image.tmdb.org/t/p/w500"+now.backdrop_path)
                    .into(binding.imageDetailsBackdropPath)

                Glide.with(requireContext())
                    .load("https://image.tmdb.org/t/p/w500"+now.poster_path)
                    .into(binding.imageDetailsposterpath)
            }
        })
    }

    private fun observeMovieData() {
        viewModel.movies.observe(viewLifecycleOwner, Observer { movie ->
            movie.let {
                binding.detailsTitle.text=movie.title
                binding.detailsOverview.text=movie.overview
                binding.rowDetailAverage.text=movie.vote_average.toString()
                binding.rowDetailVoteCount.text=movie.vote_count.toString()
                binding.rowDetailsRelease.text=movie.release_date

                Glide.with(requireContext())
                    .load("https://image.tmdb.org/t/p/w500"+movie.backdrop_path)
                    .into(binding.imageDetailsBackdropPath)

                Glide.with(requireContext())
                    .load("https://image.tmdb.org/t/p/w500"+movie.poster_path)
                    .into(binding.imageDetailsposterpath)
            }
        })
    }


}