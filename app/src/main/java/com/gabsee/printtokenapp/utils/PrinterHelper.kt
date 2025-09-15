package com.gabsee.printtokenapp.utils

import android.bluetooth.BluetoothDevice
import com.dantsu.escposprinter.EscPosPrinter
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection

class PrinterHelper {

    fun printToken(device: BluetoothDevice, token: String) {
        val connection = BluetoothConnection(device)
        val printer = EscPosPrinter(connection, 203, 38f, 32)

        val groups: List<String> = if (token.contains(" ")) {
            token.trim().split("\\s+".toRegex())
        } else {
            token.chunked(4)
        }

        val firstLine = groups.take(3).joinToString(" ")
        val secondLine = groups.drop(3).joinToString(" ")

        printer.printFormattedText(
            "[C]<b>=== TOKEN LISTRIK ===</b>\n" +
                    "[C]Token:\n" +
                    "[C]<font size='big'><b>$firstLine</b></font>" +
                    "[C]<font size='big'><b>$secondLine</b></font>"
        )

        printer.disconnectPrinter()
    }

}