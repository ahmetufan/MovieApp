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
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmet.movieapp.adapter.SearchAdaptor
import com.ahmet.movieapp.databinding.FragmentHomeBinding
import com.ahmet.movieapp.databinding.FragmentSearchBinding
import com.ahmet.movieapp.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var searchAdaptor: SearchAdaptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getLiveData()

        binding.recyclerView.layoutManager =
            LinearLayoutManager(context)
        searchAdaptor = SearchAdaptor(arrayListOf())
        binding.recyclerView.adapter = searchAdaptor
        binding.recyclerView.visibility=View.GONE

        observeLiveData()

        binding.backImage.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.editSearch2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0 != null) {
                    searchDatabase(p0.toString())
                    binding.recyclerView.visibility=View.VISIBLE
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })


    }
    private fun searchDatabase(deger:CharSequence){
        val searchQuery = "%$deger%"
        viewModel.searchDatabse(searchQuery).observe(viewLifecycleOwner, Observer { search ->
            search.let {
                searchAdaptor.updateMovieListe(search)
            }
        })

    }

    private fun observeLiveData() {

        viewModel.moviedata.observe(viewLifecycleOwner, Observer { movie ->
            movie.let {
                searchAdaptor.updateMovieListe(movie)
            }
        })
    }


    }