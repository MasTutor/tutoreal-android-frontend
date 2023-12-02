package com.mastutor.tutoreal.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldDialog(
    data: String,
    openDialog: MutableState<Boolean>,
    value: String,
    onValueChanged: (String) -> Unit,
    onSubmitClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = "Masukkan $data Baru")
            },
            text = {
                Column(modifier = modifier.padding(top = 10.dp)) {
                    TextField(
                        value = value,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        onValueChange = onValueChanged,
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = onSubmitClicked
                ) {
                    Text("Confirm")
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