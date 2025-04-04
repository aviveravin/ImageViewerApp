package com.example.imageviewapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imageviewapp.domain.model.Photo
import com.example.imageviewapp.domain.usecase.GetFavoritesUseCase
import com.example.imageviewapp.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(FavoritesScreenState())
    val state: StateFlow<FavoritesScreenState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getFavoritesUseCase().collect { favorites ->
                _state.update { it.copy(favorites = favorites) }
            }
        }
    }

    fun removeFavorite(photo: Photo) {
        viewModelScope.launch {
            toggleFavoriteUseCase(photo, true)
        }
    }
}

data class FavoritesScreenState(
    val favorites: List<Photo> = emptyList()
)