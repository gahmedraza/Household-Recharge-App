package com.raza.householdrecharge.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDatePicker(
    modifier: Modifier,
    value: String?,
    onDateSelected: (Long) -> Unit,
    label: String
) {

    var showDatePicker by remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),

        value = getPrintableDate(value),

        onValueChange = {},

        readOnly = true,

        label = {
            Text(label)
        },

        trailingIcon = {
            IconButton(
                onClick = {
                    showDatePicker = true
                }
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "select date"
                )
            }
        },

        placeholder = {
            Text("select date")
        }
    )

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = getTodayInMillis()
        )

        DatePickerDialog(

            onDismissRequest = {
                showDatePicker = false
            },

            confirmButton = {
                TextButton(

                    onClick = {
                        datePickerState.selectedDateMillis?.let {
                            onDateSelected(it)
                        }

                        showDatePicker = false
                    }
                ) {
                    Text("OK")
                }
            },

            dismissButton = {
                TextButton(
                    onClick = {
                        showDatePicker = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        ) {

            DatePicker(
                state = datePickerState
            )
        }
    }
}

fun getTodayInMillis() = LocalDate
    .now()
    .atStartOfDay(ZoneId.systemDefault())
    .toInstant()
    .toEpochMilli()

fun getPrintableDate(dateStringInMillis: String?): String {
    var dateInMillis = 0L
    val isDateStringEmpty = dateStringInMillis?.isEmpty() ?: false

    if (isDateStringEmpty) {
        return ""

    } else {
        dateInMillis = dateStringInMillis?.toLong() ?: 0L
    }

    return SimpleDateFormat(
        "dd/MM/yyyy",
        Locale.getDefault()
    ).format(Date(dateInMillis))
}

fun getDateInMillis(dateString: String?): Long {
    if (dateString.isNullOrEmpty()) {
        return 0L
    }

    val dateInMillis = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        .parse(dateString)
        ?.time ?: 0

    return dateInMillis
}