package com.tech.maxclub.numfacts.feature.main.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tech.maxclub.numfacts.R
import com.tech.maxclub.numfacts.core.Screen
import com.tech.maxclub.numfacts.feature.numfacts.presentation.numfactdetail.NumFactDetailScreen
import com.tech.maxclub.numfacts.feature.numfacts.presentation.numfactslist.NumFactsScreen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MainScreenContainer(viewModel: MainViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val navController = rememberNavController()

    LaunchedEffect(key1 = true) {
        viewModel.uiAction.collectLatest { action ->
            when (action) {
                is MainUiAction.ShowNumFactDeletedMessage -> {
                    snackbarHostState.showSnackbar(
                        message = context.getString(R.string.item_deleted_message),
                        actionLabel = context.getString(R.string.undo_button),
                        withDismissAction = true,
                        duration = SnackbarDuration.Short,
                    ).let { result ->
                        if (result == SnackbarResult.ActionPerformed) {
                            viewModel.tryRestoreNumFact(action.numFactId)
                        }
                    }
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
        NavHost(
            navController = navController,
            startDestination = Screen.NumFacts.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(route = Screen.NumFacts.route) {
                NumFactsScreen(
                    onNavigateToNumFactDetail = { numFactId ->
                        navController.navigate("${Screen.NumFactDetail.route}/$numFactId")
                    },
                    onDeleteNumFact = viewModel::deleteNumFact
                )
            }

            composable(
                route = Screen.NumFactDetail.routeWithArgs,
                arguments = listOf(
                    navArgument(name = Screen.NumFactDetail.ARG_NUM_FACT_ID) {
                        type = NavType.IntType
                        defaultValue = Screen.NumFactDetail.DEFAULT_NUM_FACT_ID
                    }
                ),
            ) {
                NumFactDetailScreen(
                    onNavigateUp = navController::navigateUp,
                    onDelete = viewModel::deleteNumFact
                )
            }
        }
    }
}