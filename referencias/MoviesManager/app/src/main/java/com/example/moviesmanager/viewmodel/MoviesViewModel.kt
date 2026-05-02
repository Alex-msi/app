package com.example.moviesmanager.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.moviesmanager.MoviesApplication
import com.example.moviesmanager.domain.Movies
import com.example.moviesmanager.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


sealed class CadastroState {
    data object InsertSuccess : CadastroState()
    data object ShowLoading : CadastroState()
    data object TitleAlreadyExists : CadastroState()
}

sealed class DetalheState {
    data object UpdateSuccess : DetalheState()
    data object DeleteSuccess : DetalheState()
    data class GetByIdSuccess(val c: Movies) : DetalheState()
    data object ShowLoading : DetalheState()
}

sealed class ListaState {
    data class SearchAllSuccess(val movies: List<Movies>) : ListaState()
    data object ShowLoading : ListaState()
    data object EmptyState : ListaState()
    data class ShowWatchedMovies(val movies: List<Movies>) : ListaState()
    data class ShowUnwatchedMovies(val movies: List<Movies>) : ListaState()
}

class WatchViewModel(private val repository: MoviesRepository) : ViewModel() {

    private val _stateCadastro = MutableStateFlow<CadastroState>(CadastroState.ShowLoading)
    val stateCadastro = _stateCadastro.asStateFlow()

    private val _stateDetail = MutableStateFlow<DetalheState>(DetalheState.ShowLoading)
    val stateDetail = _stateDetail.asStateFlow()

    private val _stateList = MutableStateFlow<ListaState>(ListaState.ShowLoading)
    val stateList = _stateList.asStateFlow()



    fun insert(moviesEntity: Movies) = viewModelScope.launch(Dispatchers.IO) {
        val success = repository.insert(moviesEntity)
        _stateCadastro.value = if (success) {
            CadastroState.InsertSuccess
        } else {
            CadastroState.TitleAlreadyExists
        }
    }

    fun update(moviesEntity: Movies) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(moviesEntity)
        _stateDetail.value = DetalheState.UpdateSuccess
    }

    fun delete(moviesEntity: Movies) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(moviesEntity)
        _stateDetail.value = DetalheState.DeleteSuccess
    }

    fun getAllTitles() {
        viewModelScope.launch {
            repository.getAllTitles().collect { result ->
                if (result.isEmpty())
                    _stateList.value = ListaState.EmptyState
                else
                    _stateList.value = ListaState.SearchAllSuccess(result)
            }
        }
    }

    fun getWatchById(id: Int) {
        viewModelScope.launch {
            repository.getTitlesById(id).collect { result ->
                _stateDetail.value = DetalheState.GetByIdSuccess(result)
            }
        }
    }

    fun getAllTitlesSorted() {
        viewModelScope.launch {
            repository.getAllTitlesSorted().collect { result ->
                if (result.isEmpty())
                    _stateList.value = ListaState.EmptyState
                else
                    _stateList.value = ListaState.SearchAllSuccess(result)
            }
        }
    }

    fun getAllRatingsSorted() {
        viewModelScope.launch {
            repository.getAllRatingsSorted().collect { result ->
                if (result.isEmpty())
                    _stateList.value = ListaState.EmptyState
                else
                    _stateList.value = ListaState.SearchAllSuccess(result)
            }
        }
    }

    fun loadWatchedMovies() {
        viewModelScope.launch {
            repository.getWatchedMovies().collect { movies ->
                _stateList.value = ListaState.ShowWatchedMovies(movies)
            }
        }
    }

    fun loadUnwatchedMovies() {
        viewModelScope.launch {
            repository.getUnwatchedMovies().collect { movies ->
                _stateList.value = ListaState.ShowUnwatchedMovies(movies)
            }
        }
    }

    companion object {
        fun watchViewModelFactory(): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras
                ): T {
                    val application = checkNotNull(
                        extras[APPLICATION_KEY]
                    )
                    return WatchViewModel(
                        (application as MoviesApplication).repository
                    ) as T
                }
            }
    }
}


