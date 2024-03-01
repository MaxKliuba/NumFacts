package com.tech.maxclub.numfacts.feature.numfacts.presentation.numfactslist.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tech.maxclub.numfacts.R
import com.tech.maxclub.numfacts.feature.numfacts.domain.models.NumFact

@Composable
fun NumFactsList(
    isLoading: Boolean,
    numFacts: List<NumFact>,
    onClick: (Int) -> Unit,
    listState: LazyListState,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.recent_facts_title),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )
        Box(modifier = modifier.fillMaxSize()) {
            if (numFacts.isEmpty() && !isLoading) {
                Icon(
                    imageVector = Icons.Default.Notes,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                        .size(160.dp)
                        .padding(16.dp)
                        .align(Alignment.Center)
                )
            } else {
                LazyColumn(
                    state = listState,
                    modifier = modifier.fillMaxSize()
                ) {
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
            }

            if (isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
        }
    }
}