package com.example.watchlist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.watchlist.R
import com.example.watchlist.databinding.FragmentDetalheBinding
import com.example.watchlist.domain.Watch
import com.example.watchlist.viewmodel.DetalheState
import com.example.watchlist.viewmodel.WatchViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


class DetalheFragment : Fragment() {
    private var _binding: FragmentDetalheBinding? = null
    private val binding get() = _binding!!

    lateinit var watch: Watch
    lateinit var titleEditText: EditText
    lateinit var descriptionEditText: EditText
    lateinit var spinnerStatus: Spinner

    val viewModel: WatchViewModel by viewModels { WatchViewModel.watchViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetalheBinding.inflate(inflater, container, false)
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

        titleEditText = binding.commonLayout.editTextTitle
        descriptionEditText = binding.commonLayout.editTextDescription
        spinnerStatus = binding.commonLayout.spinnerStatus


        val idWatch = requireArguments().getInt("idWatch")
        viewModel.getWatchById(idWatch)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.stateDetail.collect {
                when (it) {
                    DetalheState.DeleteSuccess -> {
                        Snackbar.make(
                            binding.root,
                            "Filme removido com sucesso",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        findNavController().popBackStack()
                    }

                    is DetalheState.GetByIdSuccess -> {
                        fillFields(it.c)
                    }

                    DetalheState.ShowLoading -> {}
                    DetalheState.UpdateSuccess -> {
                        Snackbar.make(
                            binding.root,
                            "Filme alterado com sucesso",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        findNavController().popBackStack()
                    }
                }
            }
        }

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {

                menuInflater.inflate(R.menu.detalhe_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {

                return when (menuItem.itemId) {
                    R.id.action_alterarTitle -> {

                        watch.title = titleEditText.text.toString()
                        watch.description = descriptionEditText.text.toString()
                        watch.status = spinnerStatus.selectedItem.toString()

                        viewModel.update(watch)

                        true
                    }

                    R.id.action_excluirTitle -> {
                        viewModel.delete(watch)
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun fillFields(c: Watch) {
        watch = c
        titleEditText.setText(watch.title)
        descriptionEditText.setText(watch.description)
        val statusOptions = resources.getStringArray(R.array.status_options)
        val statusIndex = statusOptions.indexOf(watch.status)
        if (statusIndex >= 0) {
            spinnerStatus.setSelection(statusIndex)
        }
    }
}