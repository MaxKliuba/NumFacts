package com.tech.maxclub.numfacts.feature.numfacts.presentation.numfactdetail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tech.maxclub.numfacts.feature.numfacts.domain.models.NumFact

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumFactDetailSection(
    numFact: NumFact,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = numFact.number,
            style = MaterialTheme.typography.headlineLarge.copy(
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
            ),
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        )

        FilterChip(
            selected = false,
            onClick = {},
            label = {
                Text(text = numFact.type.label)
            },
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .align(Alignment.CenterHorizontally)
        )

        Text(
            text = numFact.fact,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}