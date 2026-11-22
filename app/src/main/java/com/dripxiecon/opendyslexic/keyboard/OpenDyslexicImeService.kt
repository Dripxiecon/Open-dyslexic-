package com.dripxiecon.opendyslexic.keyboard

import android.inputmethodservice.InputMethodService
import android.view.View
import com.dripxiecon.opendyslexic.layouts.KeyboardLayouts
import com.dripxiecon.opendyslexic.layouts.KeyKind
import com.dripxiecon.opendyslexic.layouts.KeyboardMode

/**
 * Android entry point. The service coordinates layout state while the view
 * remains responsible only for drawing and hit-testing.
 */
class OpenDyslexicImeService : InputMethodService() {
    private var mode = KeyboardMode.LETTERS
    private var shifted = false
    private var keyboardView: KeyboardView? = null

    override fun onCreateInputView(): View {
        return KeyboardView(this).also { view ->
            keyboardView = view
            view.onKeyPressed = ::handleKey
            refreshLayout()
        }
    }

    override fun onFinishInputView(finishingInput: Boolean) {
        keyboardView = null
        super.onFinishInputView(finishingInput)
    }

    private fun handleKey(key: com.dripxiecon.opendyslexic.layouts.KeyDefinition) {
        when (key.kind) {
            KeyKind.SHIFT -> {
                shifted = !shifted
                refreshLayout()
            }
            KeyKind.SWITCH_NUMBERS -> {
                mode = KeyboardMode.NUMBERS
                refreshLayout()
            }
            KeyKind.SWITCH_SYMBOLS -> {
                mode = KeyboardMode.SYMBOLS
                refreshLayout()
            }
            KeyKind.SWITCH_LETTERS -> {
                mode = KeyboardMode.LETTERS
                refreshLayout()
            }
            else -> {
                val output = if (key.kind == KeyKind.TEXT && shifted) {
                    key.value.uppercase()
                } else null
                KeyHandler.commit(key, currentInputConnection, output)
                if (shifted && key.kind == KeyKind.TEXT) {
                    shifted = false
                    refreshLayout()
                }
            }
        }
    }

    private fun refreshLayout() {
        val rows = when (mode) {
            KeyboardMode.LETTERS -> KeyboardLayouts.letters(shifted)
            KeyboardMode.NUMBERS -> KeyboardLayouts.numbers()
            KeyboardMode.SYMBOLS -> KeyboardLayouts.symbols()
        }
        keyboardView?.setRows(rows)
    }
}
