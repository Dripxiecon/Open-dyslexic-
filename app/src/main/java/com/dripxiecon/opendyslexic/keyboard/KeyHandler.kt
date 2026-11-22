package com.dripxiecon.opendyslexic.keyboard

import android.view.KeyEvent
import android.view.inputmethod.InputConnection
import com.dripxiecon.opendyslexic.layouts.KeyDefinition
import com.dripxiecon.opendyslexic.layouts.KeyKind

/** Sends text-editing actions to the currently focused application. */
object KeyHandler {
    fun commit(key: KeyDefinition, connection: InputConnection?, textOverride: String? = null) {
        when (key.kind) {
            KeyKind.TEXT -> connection?.commitText(textOverride ?: key.value, 1)
            KeyKind.SPACE -> connection?.commitText(" ", 1)
            KeyKind.DELETE -> {
                val selected = connection?.getSelectedText(0)
                if (!selected.isNullOrEmpty()) {
                    connection.commitText("", 1)
                } else {
                    connection?.deleteSurroundingText(1, 0)
                }
            }
            KeyKind.ENTER -> {
                connection?.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER))
                connection?.sendKeyEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_ENTER))
            }
            else -> Unit
        }
    }
}
