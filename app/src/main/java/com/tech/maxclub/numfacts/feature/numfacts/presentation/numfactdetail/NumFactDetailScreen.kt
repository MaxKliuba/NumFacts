package com.tech.maxclub.numfacts.feature.numfacts.presentation.numfactdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.tech.maxclub.numfacts.feature.numfacts.presentation.numfactdetail.components.NumFactDetailSection
import com.tech.maxclub.numfacts.feature.numfacts.presentation.numfactdetail.components.NumFactDetailTopBar

@Composable
fun NumFactDetailScreen(
    onNavigateUp: () -> Unit,
    onDelete: (Int) -> Unit,
    viewModel: NumFactDetailViewModel = hiltViewModel()
) {
    val state by viewModel.uiState

    Scaffold(
        topBar = {
            NumFactDetailTopBar(
                onNavigateUp = onNavigateUp,
                onDelete = (state as? NumFactDetailUiState.Success)?.let { state ->
                    {
                        onDelete(state.numFact.id)
                        onNavigateUp()
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
                        NumFactDetailSection(
                            numFact = state.numFact,
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                        )
                    }
                }
            }
        }
    }
}