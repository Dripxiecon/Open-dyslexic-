package com.dripxiecon.opendyslexic.keyboard

import android.view.inputmethod.InputConnection
import com.dripxiecon.opendyslexic.layouts.KeyDefinition
import com.dripxiecon.opendyslexic.layouts.KeyKind

/** Converts key actions into InputConnection operations. */
object KeyHandler {
    fun commit(key: KeyDefinition, connection: InputConnection?) {
        when (key.kind) {
            KeyKind.TEXT -> connection?.commitText(key.value, 1)
            KeyKind.SPACE -> connection?.commitText(" ", 1)
            KeyKind.DELETE -> connection?.deleteSurroundingText(1, 0)
            KeyKind.ENTER -> connection?.sendKeyEvent(
                android.view.KeyEvent(android.view.KeyEvent.ACTION_DOWN, android.view.KeyEvent.KEYCODE_ENTER)
            )
        }
    }
}
