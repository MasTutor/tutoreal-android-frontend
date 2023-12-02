package com.mastutor.tutoreal.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SelectionDialog(
    openDialog: MutableState<Boolean>,
    options: List<String>,
    selectedOptions: MutableState<String>,
    onSubmitClicked: () -> Unit
) {
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = "Pilih Data Baru")
            },
            text = {
                Row {
                    options.forEach { option ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .selectable(
                                    selected = selectedOptions.value == option,
                                    onClick = { selectedOptions.value = option },
                                    role = Role.RadioButton
                                )
                        ) {
                            RadioButton(
                                selected = selectedOptions.value == option,
                                onClick = null,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Text(option.replaceFirstChar { it.uppercaseChar() }, style = TextStyle(fontSize = 16.sp))
                        }
                        Spacer(Modifier.weight(1F))
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = onSubmitClicked
                ) {
                    Text("Confirm ")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        openDialog.value = false
                    }) {
                    Text("Dismiss")
                }
            }
        )
    }
}
