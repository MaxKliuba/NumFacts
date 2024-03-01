package com.tech.maxclub.numfacts.feature.numfacts.presentation.numfactslist.components

import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tech.maxclub.numfacts.feature.numfacts.domain.models.NumType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumTypeDropdownMenu(
    value: NumType,
    values: List<NumType>,
    onChangeValue: (NumType) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    val onExpandedChange: (Boolean) -> Unit = { expanded = it }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        modifier = modifier
    ) {
        TextField(
            value = value.name,
            onValueChange = {},
            readOnly = true,
            singleLine = true,
            maxLines = 1,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .width(120.dp)
                .menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) },
        ) {
            values.forEach { numType ->
                DropdownMenuItem(
                    text = {
                        Text(text = numType.name)
                    },
                    onClick = {
                        onChangeValue(numType)
                        onExpandedChange(false)
                    }
                )
            }
        }
    }
}