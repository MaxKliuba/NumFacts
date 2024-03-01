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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.tech.maxclub.numfacts.R
import com.tech.maxclub.numfacts.feature.numfacts.domain.models.NumType

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GetNumFactSection(
    numTypeValue: NumType,
    numberValue: String,
    onChangeNumberValue: (String) -> Unit,
    onGetNumFact: (String, NumType) -> Unit,
    randomNumTypeValue: NumType,
    onGetRandomNumFact: (NumType) -> Unit,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current

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
            TextField(
                value = numberValue,
                onValueChange = onChangeNumberValue,
                label = {
                    Text(text = stringResource(R.string.number_placeholder))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onGetNumFact(numberValue, numTypeValue)
                        keyboardController?.hide()
                    }
                ),
                singleLine = true,
                maxLines = 1,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {
                    onGetNumFact(numberValue, numTypeValue)
                    keyboardController?.hide()
                },
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

        Button(
            onClick = {
                onGetRandomNumFact(randomNumTypeValue)
                keyboardController?.hide()
            },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(56.dp)
        ) {
            Text(text = stringResource(R.string.get_random_fact_button))
        }
    }
}