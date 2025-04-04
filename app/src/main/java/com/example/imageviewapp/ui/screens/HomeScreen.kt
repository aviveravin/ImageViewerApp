package com.example.imageviewapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.imageviewapp.ui.components.ImageCard
import com.example.imageviewapp.ui.theme.ShimmerEffect
import com.example.imageviewapp.ui.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    onNavigateToDetail: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val searchQuery = remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        // Search Bar
        OutlinedTextField(
            value = searchQuery.value,
            onValueChange = { newQuery ->
                searchQuery.value = newQuery
                if (newQuery.isBlank()) {
                    viewModel.resetSearch()
                } else {
                    viewModel.searchPhotos(newQuery)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = { Text("Search photos...") },
            trailingIcon = {
                IconButton(onClick = {
                    if (searchQuery.value.isNotBlank()) {
                        viewModel.searchPhotos(searchQuery.value)
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    if (searchQuery.value.isNotBlank()) {
                        viewModel.searchPhotos(searchQuery.value)
                    }
                }
            ),
            singleLine = true
        )


        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(state.photos) { photo ->
                ImageCard(
                    photo = photo,
                    onClick = { onNavigateToDetail(photo.id) },
                    modifier = Modifier.padding(8.dp)
                )
            }

            if (state.isLoading) {
                items(4) {
                    ShimmerEffect(
                        modifier = Modifier
                            .height(220.dp)
                            .padding(8.dp)
                    )
                }
            }

            // Load more when reached end
            item(span = { GridItemSpan(2) }) {
                if (state.photos.isNotEmpty()) {
                    LaunchedEffect(key1 = state.photos.size) {
                        viewModel.loadPhotos()
                    }
                }
            }
        }

        // Error message
        if (state.error != null) {
            Text(
                text = state.error!!,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}