package com.tech.maxclub.numfacts.feature.numfacts.presentation.numfactdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.tech.maxclub.numfacts.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumFactDetailScreen(
    onNavigateUp: () -> Unit,
    onDelete: (Int) -> Unit,
    viewModel: NumFactDetailViewModel = hiltViewModel()
) {
    val state by viewModel.uiState

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back_button),
                        )
                    }
                },
                title = {},
                actions = {
                    (state as? NumFactDetailUiState.Success)?.let { state ->
                        IconButton(
                            onClick = {
                                onDelete(state.numFact.id)
                                onNavigateUp()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.DeleteOutline,
                                contentDescription = stringResource(R.string.delete_item_button),
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            state.let { state ->
                when (state) {
                    is NumFactDetailUiState.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }

                    is NumFactDetailUiState.Success -> {
                        Column {
                            Text(text = state.numFact.fact)
                        }
                    }
                }
            }
        }
    }
}