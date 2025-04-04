package com.example.imageviewapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imageviewapp.domain.model.Photo
import com.example.imageviewapp.domain.model.PhotoDetail
import com.example.imageviewapp.domain.usecase.CheckFavoriteStatusUseCase
import com.example.imageviewapp.domain.usecase.GetPhotoDetailUseCase
import com.example.imageviewapp.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getPhotoDetailUseCase: GetPhotoDetailUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val checkFavoriteStatusUseCase: CheckFavoriteStatusUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(DetailScreenState())
    val state: StateFlow<DetailScreenState> = _state.asStateFlow()

    fun loadPhotoDetail(id: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            try {
                val detail = getPhotoDetailUseCase(id)
                _state.update {
                    it.copy(
                        photoDetail = detail,
                        isLoading = false
                    )
                }

                // Check if the photo is in favorites
                checkFavoriteStatusUseCase(id).collect { isFavorite ->
                    _state.update { it.copy(isFavorite = isFavorite) }
                }

            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Unknown error occurred"
                    )
                }
            }
        }
    }

    fun toggleFavorite() {
        val currentState = _state.value
        val photoDetail = currentState.photoDetail ?: return

        viewModelScope.launch {
            val photo = Photo(
                id = photoDetail.id,
                url = photoDetail.url,
                regularUrl = photoDetail.regularUrl,
                photographerName = photoDetail.photographerName,
                username = photoDetail.username,
                profileImageUrl = photoDetail.profileImageUrl,
                likes = photoDetail.likes,
                description = photoDetail.description
            )

            toggleFavoriteUseCase(photo, currentState.isFavorite)
        }
    }
}

data class DetailScreenState(
    val photoDetail: PhotoDetail? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isFavorite: Boolean = false
)