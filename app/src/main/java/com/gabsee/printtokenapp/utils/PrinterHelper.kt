package com.gabsee.printtokenapp.utils

import android.bluetooth.BluetoothDevice
import com.dantsu.escposprinter.EscPosPrinter
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection

class PrinterHelper {

    fun printToken(device: BluetoothDevice, token: String) {
        val connection = BluetoothConnection(device)
        val printer = EscPosPrinter(connection, 203, 48f, 32)

        printer.printFormattedText(
            "[C]<b>=== TOKEN LISTRIK ===</b>\n" +
                    "[C]Token:\n" +
                    "[C]<font size='big'><b>$token</b></font>"
        )

        printer.disconnectPrinter()
    }

}