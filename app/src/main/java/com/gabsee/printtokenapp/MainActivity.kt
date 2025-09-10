package com.gabsee.printtokenapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.gabsee.printtokenapp.ui.PrintTokenApp
import com.gabsee.printtokenapp.ui.theme.PrintTokenAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrintTokenAppTheme {
                RequestBluetoothPermission()
                PrintTokenApp()
            }
        }
    }
}

@Composable
fun RequestBluetoothPermission() {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            val notGranted = permissions.filterValues { !it }
            if (notGranted.isNotEmpty()) {
                Toast.makeText(
                    context,
                    "Bluetooth permission is required to print",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    )

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val requiredPermissions = arrayOf(
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.BLUETOOTH_SCAN
            )

            val notGranted = requiredPermissions.filter {
                ContextCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED
            }
            if (notGranted.isNotEmpty()) {
                launcher.launch(requiredPermissions)
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PrintTokenAppTheme {
        PrintTokenApp()
    }
}