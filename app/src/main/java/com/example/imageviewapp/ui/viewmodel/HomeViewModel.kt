package com.example.imageviewapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imageviewapp.domain.model.Photo
import com.example.imageviewapp.domain.usecase.GetPhotosUseCase
import com.example.imageviewapp.domain.usecase.SearchPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPhotosUseCase: GetPhotosUseCase,
    private val searchPhotosUseCase: SearchPhotosUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeScreenState())
    val state: StateFlow<HomeScreenState> = _state.asStateFlow()

    private var currentPage = 1
    private var canLoadMore = true
    private var currentSearchQuery = ""

    init {
        loadPhotos()
    }

    fun loadPhotos() {
        if (_state.value.isLoading || !canLoadMore) return

        viewModelScope.launch {
            _state.update { it.copy(photos = emptyList(), isLoading = true) }

            try {
                val newPhotos = if (currentSearchQuery.isBlank()) {
                    getPhotosUseCase(currentPage)
                } else {
                    searchPhotosUseCase(currentSearchQuery, currentPage)
                }

                if (newPhotos.isEmpty()) {
                    canLoadMore = false
                } else {
                    currentPage++
                    _state.update {
                        it.copy(
                            photos = it.photos + newPhotos,
                            isLoading = false
                        )
                    }
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

    fun searchPhotos(query: String) {
        currentSearchQuery = query
        currentPage = 1
        canLoadMore = true

        _state.update { it.copy(photos = emptyList(), isLoading = true) }

        viewModelScope.launch {
            try {
                val newPhotos = searchPhotosUseCase(query, currentPage)
                _state.update {
                    it.copy(
                        photos = newPhotos,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(isLoading = false, error = e.message ?: "Unknown error")
                }
            }
        }
    }


    fun resetSearch() {
        if (currentSearchQuery.isBlank()) return

        currentSearchQuery = ""
        currentPage = 1
        canLoadMore = true

        _state.update { it.copy(photos = emptyList(), isLoading = true) }

        viewModelScope.launch {
            try {
                val newPhotos = getPhotosUseCase(currentPage)
                currentPage++
                _state.update {
                    it.copy(
                        photos = newPhotos,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(isLoading = false, error = e.message ?: "Error occurred")
                }
            }
        }
        loadPhotos()
    }

}

data class HomeScreenState(
    val photos: List<Photo> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)