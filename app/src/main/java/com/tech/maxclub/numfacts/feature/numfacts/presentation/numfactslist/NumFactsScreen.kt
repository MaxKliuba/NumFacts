package com.tech.maxclub.numfacts.feature.numfacts.presentation.numfactslist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.tech.maxclub.numfacts.feature.numfacts.presentation.numfactslist.components.NumFactsList

@Composable
fun NumFactsScreen(
    onNavigateToNumFactDetail: (Int) -> Unit,
    viewModel: NumFactsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState
    val listState = rememberLazyListState()

    LaunchedEffect(key1 = state.numFactPreview) {
        if (state.numFactPreview != null) {
            listState.scrollToItem(0)
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Button(onClick = viewModel::fetchNumFact) {
            Text(text = "Get fact")
        }

        Button(onClick = viewModel::fetchRandomNumFact) {
            Text(text = "Get fact about random number")
        }

        NumFactsList(
            isLoading = state.isListLoading,
            numFactPreview = state.numFactPreview,
            numFacts = state.numFacts,
            onClick = onNavigateToNumFactDetail,
            listState = listState,
            modifier = Modifier.weight(1f)
        )
    }
}