package com.tech.maxclub.numfacts.feature.numfacts.presentation.numfactslist.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.tech.maxclub.numfacts.R
import com.tech.maxclub.numfacts.feature.numfacts.domain.models.NumType

@Composable
fun GetNumFactSection(
    numTypeValue: NumType,
    onChangeNumTypeValue: (NumType) -> Unit,
    numberValue: String,
    onChangeNumberValue: (String) -> Unit,
    onGetNumFact: (String, NumType) -> Unit,
    randomNumTypeValue: NumType,
    onChangeRandomNumTypeValue: (NumType) -> Unit,
    onGetRandomNumFact: (NumType) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.get_fact_title),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            NumTypeDropdownMenu(
                value = numTypeValue,
                values = NumType.entries.toList(),
                onChangeValue = onChangeNumTypeValue,
            )

            Spacer(modifier = Modifier.width(8.dp))

            TextField(
                value = numberValue,
                onValueChange = onChangeNumberValue,
                label = {
                    Text(text = stringResource(R.string.number_placeholder))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Send,
                ),
                keyboardActions = KeyboardActions(
                    onSend = { onGetNumFact(numberValue, numTypeValue) }
                ),
                singleLine = true,
                maxLines = 1,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = { onGetNumFact(numberValue, numTypeValue) },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.height(56.dp)
            ) {
                Text(text = stringResource(R.string.get_fact_button))
            }
        }

        Text(
            text = stringResource(R.string.or_label),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            NumTypeDropdownMenu(
                value = randomNumTypeValue,
                values = NumType.entries.toList(),
                onChangeValue = onChangeRandomNumTypeValue,
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = { onGetRandomNumFact(randomNumTypeValue) },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .height(56.dp)
                    .weight(1f)
            ) {
                Text(text = stringResource(R.string.get_random_fact_button))
            }
        }
    }
}