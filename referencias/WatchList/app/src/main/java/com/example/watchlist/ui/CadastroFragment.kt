package com.example.watchlist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.watchlist.R
import com.example.watchlist.databinding.FragmentCadastroBinding
import com.example.watchlist.domain.Watch
import com.example.watchlist.viewmodel.CadastroState
import com.example.watchlist.viewmodel.WatchViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class CadastroFragment : Fragment() {
    private var _binding: FragmentCadastroBinding? = null
    private val binding get() = _binding!!

    val viewModel: WatchViewModel by viewModels { WatchViewModel.watchViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCadastroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinner = view.findViewById<Spinner>(R.id.spinnerStatus)

        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.status_options,
            R.layout.spinner_item
        )

        adapter.setDropDownViewResource(R.layout.spinner_item)
        spinner.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.stateCadastro.collect {
                when (it) {
                    CadastroState.InsertSuccess -> {
                        Snackbar.make(
                            binding.root,
                            "Filme inserido com sucesso",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        findNavController().popBackStack()
                    }

                    CadastroState.ShowLoading -> {}
                }
            }
        }

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {

                menuInflater.inflate(R.menu.cadastro_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {

                return when (menuItem.itemId) {
                    R.id.action_salvarTitle -> {
                        val title = binding.commonLayout.editTextTitle.text.toString()
                        val description = binding.commonLayout.editTextDescription.text.toString()
                        val status = binding.commonLayout.spinnerStatus.selectedItem.toString()

                        val watch = Watch(title = title, description = description, status = status)
                        viewModel.insert(watch)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}