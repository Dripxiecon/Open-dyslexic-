package com.dripxiecon.opendyslexic.keyboard

import android.inputmethodservice.InputMethodService
import android.view.View
import com.dripxiecon.opendyslexic.layouts.KeyboardLayouts

/**
 * Android entry point for the keyboard. The service owns the input connection;
 * rendering and key definitions remain separate from input handling.
 */
class OpenDyslexicImeService : InputMethodService() {
    override fun onCreateInputView(): View {
        return KeyboardView(this).apply {
            onKeyPressed = { key ->
                KeyHandler.commit(key, currentInputConnection)
            }
            setKeys(KeyboardLayouts.letters())
        }
    }
}
