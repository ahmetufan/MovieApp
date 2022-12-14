package com.ahmet.movieapp.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmet.movieapp.BuildConfig
import com.ahmet.movieapp.R
import com.ahmet.movieapp.adapter.MovieAdapter
import com.ahmet.movieapp.adapter.NowAdapter
import com.ahmet.movieapp.adapter.PopularAdaptor
import com.ahmet.movieapp.databinding.FragmentHomeBinding
import com.ahmet.movieapp.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var popularAdaptor: PopularAdaptor
    private lateinit var nowAdapter: NowAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.getLiveData()

        viewModel.getPopularLiveData()

        viewModel.getNowPlayingLiveData()

        swipeRefresh()

        initRecycler()

        observeLiveData()


        binding.ikonSearch.setOnClickListener{
            val action=HomeFragmentDirections.actionHomeFragmentToSearchFragment()
            Navigation.findNavController(it).navigate(action)
        }


    }

    private fun swipeRefresh() {

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshData()
            binding.swipeRefreshLayout.isRefreshing=false
        }
    }


    private fun observeLiveData() {

        viewModel.moviedata.observe(viewLifecycleOwner, Observer { movies ->

            movies.let {

                movieAdapter.updateMovieListe(movies)
            }
        })

        viewModel.popularData.observe(viewLifecycleOwner, Observer { popular ->

            popular.let {
                popularAdaptor.updatePopularList(popular)
            }
        })

        viewModel.nowPlayingData.observe(viewLifecycleOwner, Observer { now ->

            now.let {
                nowAdapter.updateNowList(now)
            }
        })
    }

    private fun initRecycler() {

        binding.recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        movieAdapter = MovieAdapter(arrayListOf())
        binding.recyclerView.adapter = movieAdapter
        binding.recyclerView.set3DItem(true)
        binding.recyclerView.setAlpha(true)
        binding.recyclerView.setInfinite(true)



        binding.popularRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        popularAdaptor = PopularAdaptor(arrayListOf())
        binding.popularRecyclerView.adapter = popularAdaptor
        binding.popularRecyclerView.set3DItem(true)
        binding.popularRecyclerView.setAlpha(true)
        binding.popularRecyclerView.setInfinite(true)

        binding.nowRecyclerView.layoutManager = LinearLayoutManager(context)
        nowAdapter = NowAdapter(arrayListOf())
        binding.nowRecyclerView.adapter = nowAdapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}