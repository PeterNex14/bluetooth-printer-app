package com.gabsee.printtokenapp.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gabsee.printtokenapp.viewmodel.PrintViewModel

@Composable
fun PrintTokenApp(
    modifier: Modifier = Modifier,
    viewModel: PrintViewModel = viewModel()
)  {
    var userInput by rememberSaveable { mutableStateOf("") }
    var showPrinterSheet by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(12.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            OutlinedTextField(
                value = userInput,
                onValueChange = { userInput = it },
                label = { Text("Masukkan Kode") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { showPrinterSheet = true },
                modifier = Modifier.width(200.dp)
            ) {
                Text("Print")
            }
        }

        if (showPrinterSheet) {
            PrinterSelectionSheet(
                onDismiss = { showPrinterSheet = false },
                onDeviceSelected = { device ->
                    viewModel.selectDevice(device)
                    viewModel.printToken(
                        userInput,
                        onError = { msg ->
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                        },
                        onSuccess = {
                            Toast.makeText(context, "Printed successfully", Toast.LENGTH_SHORT).show()
                        }
                    )
                    showPrinterSheet = false
                }
            )
        }
    }

}