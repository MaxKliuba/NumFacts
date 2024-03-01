package com.tech.maxclub.numfacts.feature.numfacts.presentation.numfactslist

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.hilt.navigation.compose.hiltViewModel
import com.tech.maxclub.numfacts.feature.numfacts.presentation.numfactslist.components.GetNumFactSection
import com.tech.maxclub.numfacts.feature.numfacts.presentation.numfactslist.components.NumFactsList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@Composable
fun NumFactsScreen(
    onNavigateToNumFactDetail: (Int) -> Unit,
    onDeleteNumFact: (Int) -> Unit,
    viewModel: NumFactsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState
    val listState = rememberLazyListState()

    val configuration = LocalConfiguration.current
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = state.isListLoading) {
        delay(100)
        listState.scrollToItem(0)
    }

    LaunchedEffect(key1 = true) {
        viewModel.uiAction.collectLatest { action ->
            when (action) {
                is NumFactsUiAction.ShowErrorMessage -> {
                    snackbarHostState.showSnackbar(
                        message = action.errorMessage,
                        withDismissAction = true,
                        duration = SnackbarDuration.Short,
                    )
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    actionColor = MaterialTheme.colorScheme.primary,
                    dismissActionContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    snackbarData = data,
                )
            }
        }
    ) { paddingValues ->
        when (configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    GetNumFactSection(
                        numTypeValue = state.numTypeValue,
                        numberValue = state.numberValue,
                        onChangeNumberValue = viewModel::tryChangeNumberValue,
                        onGetNumFact = viewModel::fetchNumFact,
                        randomNumTypeValue = state.randomNumTypeValue,
                        onGetRandomNumFact = viewModel::fetchRandomNumFact,
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                    )

                    NumFactsList(
                        isLoading = state.isListLoading,
                        numFacts = state.numFacts,
                        onClickItem = onNavigateToNumFactDetail,
                        onDeleteItem = onDeleteNumFact,
                        listState = listState,
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                    )
                }
            }

            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    GetNumFactSection(
                        numTypeValue = state.numTypeValue,
                        numberValue = state.numberValue,
                        onChangeNumberValue = viewModel::tryChangeNumberValue,
                        onGetNumFact = viewModel::fetchNumFact,
                        randomNumTypeValue = state.randomNumTypeValue,
                        onGetRandomNumFact = viewModel::fetchRandomNumFact,
                        modifier = Modifier.fillMaxWidth()
                    )

                    NumFactsList(
                        isLoading = state.isListLoading,
                        numFacts = state.numFacts,
                        onClickItem = onNavigateToNumFactDetail,
                        onDeleteItem = onDeleteNumFact,
                        listState = listState,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}