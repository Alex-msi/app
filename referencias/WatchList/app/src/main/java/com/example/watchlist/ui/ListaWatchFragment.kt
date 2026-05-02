package com.example.watchlist.ui

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
import com.example.watchlist.R
import com.example.watchlist.adapter.WatchAdapter
import com.example.watchlist.databinding.FragmentListaWatchBinding
import com.example.watchlist.domain.Watch
import com.example.watchlist.viewmodel.ListaState
import com.example.watchlist.viewmodel.WatchViewModel
import kotlinx.coroutines.launch


class ListaWatchFragment : Fragment() {

    private var _binding: FragmentListaWatchBinding? = null
    private val binding get() = _binding!!

    lateinit var watchAdapter: WatchAdapter

    val viewModel: WatchViewModel by viewModels { WatchViewModel.watchViewModelFactory() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListaWatchBinding.inflate(inflater, container, false)
        binding.fab.setOnClickListener { findNavController().navigate(R.id.action_listaWatchFragment_to_cadastroFragment) }
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
                        watchAdapter.filter.filter(p0)
                        return true
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                TODO("Not yet implemented")
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }


    private fun setupViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.stateList.collect {
                when (it) {
                    is ListaState.SearchAllSuccess -> {
                        setupRecyclerView(it.watches)
                    }

                    ListaState.ShowLoading -> {}
                    ListaState.EmptyState -> {
                        binding.textEmptyList.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun setupRecyclerView(contactList: List<Watch>) {

        watchAdapter = WatchAdapter().apply { updateList(contactList) }
        binding.recyclerview.adapter = watchAdapter
        watchAdapter.onItemClick = { it ->
            val bundle = Bundle()
            bundle.putInt("idWatch", it.id)
            findNavController().navigate(
                R.id.action_listaWatchFragment_to_detalheFragment,
                bundle
            )
        }
    }
}