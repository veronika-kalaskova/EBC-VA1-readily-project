@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.readily.ui.elements

import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.readily.R

@Composable
fun CustomDatePickerDialog(
    modifier: Modifier = Modifier,
    date: Long?,
    onDismiss: () -> Unit,
    onDateSelected: (Long) -> Unit
){
    val state = rememberDatePickerState(initialSelectedDateMillis = date)

    DatePickerDialog(
        modifier = modifier,
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(
                enabled = state.selectedDateMillis != null,
                onClick = { onDateSelected(state.selectedDateMillis!!) }) {
                Text(text = stringResource(id = R.string.pick_a_date))
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text(text = stringResource(id = R.string.cancel))
            }
        }
    ) {
        DatePicker(state = state)
    }


}
