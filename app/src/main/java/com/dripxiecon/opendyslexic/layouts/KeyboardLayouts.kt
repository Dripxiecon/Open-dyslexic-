package com.dripxiecon.opendyslexic.layouts

/** Initial layout data. Dedicated number and symbol layouts will be added next. */
object KeyboardLayouts {
    fun letters(): List<KeyDefinition> = listOf(
        *"qwertyuiop".map { KeyDefinition(it.toString()) }.toTypedArray(),
        *"asdfghjkl".map { KeyDefinition(it.toString()) }.toTypedArray(),
        *"zxcvbnm".map { KeyDefinition(it.toString()) }.toTypedArray(),
        KeyDefinition("⌫", kind = KeyKind.DELETE),
        KeyDefinition("space", value = " ", kind = KeyKind.SPACE),
        KeyDefinition("↵", kind = KeyKind.ENTER)
    )
}
