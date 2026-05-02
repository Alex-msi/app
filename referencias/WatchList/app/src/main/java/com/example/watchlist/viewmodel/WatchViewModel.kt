package com.example.watchlist.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.watchlist.WatchApplication
import com.example.watchlist.domain.Watch
import com.example.watchlist.repository.WatchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


sealed class CadastroState {
    data object InsertSuccess : CadastroState()
    data object ShowLoading : CadastroState()
}

sealed class DetalheState {
    data object UpdateSuccess : DetalheState()
    data object DeleteSuccess : DetalheState()
    data class GetByIdSuccess(val c: Watch) : DetalheState()
    data object ShowLoading : DetalheState()
}

sealed class ListaState {
    data class SearchAllSuccess(val watches: List<Watch>) : ListaState()
    data object ShowLoading : ListaState()
    data object EmptyState : ListaState()
}

class WatchViewModel(private val repository: WatchRepository) : ViewModel() {

    private val _stateCadastro = MutableStateFlow<CadastroState>(CadastroState.ShowLoading)
    val stateCadastro = _stateCadastro.asStateFlow()

    private val _stateDetail = MutableStateFlow<DetalheState>(DetalheState.ShowLoading)
    val stateDetail = _stateDetail.asStateFlow()

    private val _stateList = MutableStateFlow<ListaState>(ListaState.ShowLoading)
    val stateList = _stateList.asStateFlow()

    fun insert(watchEntity: Watch) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(watchEntity)
        _stateCadastro.value = CadastroState.InsertSuccess
    }

    fun update(watchEntity: Watch) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(watchEntity)
        _stateDetail.value = DetalheState.UpdateSuccess
    }

    fun delete(watchEntity: Watch) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(watchEntity)
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
                        (application as WatchApplication).repository
                    ) as T
                }
            }
    }
}


