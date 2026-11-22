package com.dripxiecon.opendyslexic.layouts

enum class KeyKind {
    TEXT, SPACE, DELETE, ENTER, SHIFT, SWITCH_NUMBERS, SWITCH_SYMBOLS, SWITCH_LETTERS
}

data class KeyDefinition(
    val label: String,
    val value: String = label,
    val kind: KeyKind = KeyKind.TEXT,
    val weight: Float = 1f,
    val alternatives: List<String> = emptyList()
)

data class KeyRow(
    val keys: List<KeyDefinition>,
    /** Empty horizontal space before the first key, measured in normal-key widths. */
    val startInset: Float = 0f
)

enum class KeyboardMode { LETTERS, NUMBERS, SYMBOLS }
