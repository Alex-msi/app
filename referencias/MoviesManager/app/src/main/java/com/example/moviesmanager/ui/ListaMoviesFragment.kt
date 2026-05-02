package com.example.moviesmanager.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.moviesmanager.R
import com.example.moviesmanager.adapter.MoviesAdapter
import com.example.moviesmanager.databinding.FragmentListaMoviesBinding
import com.example.moviesmanager.domain.Movies
import com.example.moviesmanager.viewmodel.ListaState
import com.example.moviesmanager.viewmodel.WatchViewModel
import kotlinx.coroutines.launch


class ListaMoviesFragment : Fragment() {

    private var _binding: FragmentListaMoviesBinding? = null
    private val binding get() = _binding!!

    lateinit var moviesAdapter: MoviesAdapter

    val viewModel: WatchViewModel by viewModels { WatchViewModel.watchViewModelFactory() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListaMoviesBinding.inflate(inflater, container, false)
        binding.fab.setOnClickListener { findNavController().navigate(R.id.action_listaMoviesFragment_to_cadastroFragment) }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllTitles()
        setupViewModel()

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {

                menuInflater.inflate(R.menu.main_menu, menu)

                val searchView = menu.findItem(R.id.action_search).actionView as SearchView
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(p0: String?): Boolean {
                        TODO("Not yet implemented")
                    }

                    override fun onQueryTextChange(p0: String?): Boolean {
                        moviesAdapter.filter.filter(p0)
                        return true
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {

                return when (menuItem.itemId) {
                    R.id.sort_title -> {
                        viewModel.getAllTitlesSorted()
                        true
                    }
                    R.id.sort_rating -> {
                        viewModel.getAllRatingsSorted()
                        true
                    }
                    R.id.sort_watched -> {
                        // Carrega filmes assistidos
                        viewModel.loadWatchedMovies()

                        true
                    }
                    R.id.sort_unwatched -> {
                        // Carrega filmes não assistidos
                        viewModel.loadUnwatchedMovies()

                        true
                    }
                    else -> false
                }
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }


    private fun setupViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.stateList.collect {
                when (it) {
                    is ListaState.SearchAllSuccess -> {
                        setupRecyclerView(it.movies)
                    }

                    ListaState.ShowLoading -> {}
                    ListaState.EmptyState -> {
                        binding.textEmptyList.visibility = View.VISIBLE
                    }
                    is ListaState.ShowWatchedMovies -> {
                        setupRecyclerView(it.movies)
                    }

                    is ListaState.ShowUnwatchedMovies -> {
                        setupRecyclerView(it.movies)
                    }
                }
            }
        }
    }

    private fun setupRecyclerView(contactList: List<Movies>) {

        moviesAdapter = MoviesAdapter().apply { updateList(contactList) }
        binding.recyclerview.adapter = moviesAdapter
        moviesAdapter.onItemClick = { it ->
            val bundle = Bundle()
            bundle.putInt("idWatch", it.id)
            findNavController().navigate(
                R.id.action_listaMoviesFragment_to_detalheFragment,
                bundle
            )
        }
    }
}