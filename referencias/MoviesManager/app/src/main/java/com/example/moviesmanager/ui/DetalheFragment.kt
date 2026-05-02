package com.example.moviesmanager.ui

import android.media.Rating
import android.os.Bundle
import android.provider.MediaStore.Audio.Genres
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.moviesmanager.R
import com.example.moviesmanager.databinding.FragmentDetalheBinding
import com.example.moviesmanager.domain.Movies
import com.example.moviesmanager.viewmodel.DetalheState
import com.example.moviesmanager.viewmodel.WatchViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import java.time.Year
import kotlin.time.Duration


class DetalheFragment : Fragment() {
    private var _binding: FragmentDetalheBinding? = null
    private val binding get() = _binding!!

    lateinit var movies: Movies
    lateinit var titleEditText: EditText
    lateinit var yearEditText: EditText
    lateinit var studioEditText: EditText
    lateinit var durationEditText: EditText
    lateinit var watchedCheckBox: CheckBox
    lateinit var ratingEditText: EditText
    lateinit var genreSpinner: Spinner

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

        val spinner = view.findViewById<Spinner>(R.id.spinnerGenre)

        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.genre_options,
            R.layout.spinner_item
        )

        adapter.setDropDownViewResource(R.layout.spinner_item)
        spinner.adapter = adapter

        titleEditText = binding.commonLayout.editTextTitle
        yearEditText = binding.commonLayout.editTextYear
        studioEditText =binding.commonLayout.editTextStudio
        durationEditText =binding.commonLayout.editTextDuration
        watchedCheckBox =binding.commonLayout.CheckBoxWatched
        ratingEditText =binding.commonLayout.editTextRating
        genreSpinner = binding.commonLayout.spinnerGenre


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
                        binding.commonLayout.editTextTitle.isEnabled = false
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

                        movies.title = titleEditText.text.toString()
                        movies.year = yearEditText.text.toString().toInt()
                        movies.studio = studioEditText.text.toString()
                        movies.duration = durationEditText.text.toString().toInt()
                        movies.watched = watchedCheckBox.isChecked
                        movies.rating = ratingEditText.text.toString().toFloatOrNull()?: 0.0f
                        movies.genre = genreSpinner.selectedItem.toString()

                        viewModel.update(movies)

                        true
                    }

                    R.id.action_excluirTitle -> {
                        viewModel.delete(movies)
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun fillFields(c: Movies) {
        movies = c
        titleEditText.setText(movies.title)
        yearEditText.setText(movies.year.toString())
        studioEditText.setText(movies.studio)
        durationEditText.setText(movies.duration.toString())
        watchedCheckBox.isChecked = movies.watched
        ratingEditText.setText(movies.rating.toString())
        val statusOptions = resources.getStringArray(R.array.genre_options)
        val statusIndex = statusOptions.indexOf(movies.genre)
        if (statusIndex >= 0) {
            genreSpinner.setSelection(statusIndex)
        }
    }
}