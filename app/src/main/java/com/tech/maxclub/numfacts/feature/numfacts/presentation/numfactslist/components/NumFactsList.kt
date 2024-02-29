package com.tech.maxclub.numfacts.feature.numfacts.presentation.numfactslist.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tech.maxclub.numfacts.feature.numfacts.domain.models.NumFact
import com.tech.maxclub.numfacts.feature.numfacts.domain.models.NumFactPreview

@Composable
fun NumFactsList(
    isLoading: Boolean,
    numFactPreview: NumFactPreview?,
    numFacts: List<NumFact>,
    onClick: (Int) -> Unit,
    listState: LazyListState,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Recent Facts",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(8.dp)
        )
        Box(modifier = modifier.fillMaxSize()) {
            LazyColumn(
                state = listState,
                modifier = modifier.fillMaxSize()
            ) {
                item {
                    numFactPreview?.let {
                        NumFactPreviewItem(numFactPreview = numFactPreview)
                    }
                }

                items(
                    items = numFacts,
                    key = { it.id }
                ) { numFact ->
                    NumFactItem(
                        numFact = numFact,
                        onClick = onClick,
                    )
                }
            }

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}