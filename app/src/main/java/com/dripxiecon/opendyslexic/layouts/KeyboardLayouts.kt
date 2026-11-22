package com.dripxiecon.opendyslexic.layouts

/** Key geometry is data-driven so layouts can be changed without touching rendering. */
object KeyboardLayouts {
    fun letters(uppercase: Boolean = false): List<KeyRow> {
        fun letterRow(text: String) = text.map { char ->
            val value = char.toString()
            KeyDefinition(if (uppercase) value.uppercase() else value, value)
        }

        return listOf(
            KeyRow(letterRow("qwertyuiop")),
            KeyRow(letterRow("asdfghjkl"), startInset = 0.5f),
            KeyRow(
                listOf(KeyDefinition("⇧", kind = KeyKind.SHIFT, weight = 1.45f)) +
                    letterRow("zxcvbnm") +
                    KeyDefinition("⌫", kind = KeyKind.DELETE, weight = 1.45f)
            ),
            KeyRow(
                listOf(
                    KeyDefinition("?123", kind = KeyKind.SWITCH_NUMBERS, weight = 1.45f),
                    KeyDefinition(",", weight = 0.9f),
                    KeyDefinition("space", value = " ", kind = KeyKind.SPACE, weight = 4.8f),
                    KeyDefinition(".", weight = 0.9f),
                    KeyDefinition("↵", kind = KeyKind.ENTER, weight = 1.45f)
                )
            )
        )
    }

    fun numbers(): List<KeyRow> = listOf(
        KeyRow("1234567890".map { KeyDefinition(it.toString()) }),
        KeyRow("@#$%&-*+".map { KeyDefinition(it.toString()) }),
        KeyRow(
            listOf(KeyDefinition("=\\<", kind = KeyKind.SWITCH_SYMBOLS, weight = 1.45f)) +
                "()/;:!?".map { KeyDefinition(it.toString()) } +
                KeyDefinition("⌫", kind = KeyKind.DELETE, weight = 1.45f),
            startInset = 0.1f
        ),
        KeyRow(
            listOf(
                KeyDefinition("ABC", kind = KeyKind.SWITCH_LETTERS, weight = 1.45f),
                KeyDefinition(",", weight = 0.9f),
                KeyDefinition("space", value = " ", kind = KeyKind.SPACE, weight = 4.8f),
                KeyDefinition(".", weight = 0.9f),
                KeyDefinition("↵", kind = KeyKind.ENTER, weight = 1.45f)
            )
        )
    )

    fun symbols(): List<KeyRow> = listOf(
        KeyRow("~`|•√π÷×¶∆".map { KeyDefinition(it.toString()) }),
        KeyRow("£¢€¥^°={}\\©".map { KeyDefinition(it.toString()) }),
        KeyRow(
            listOf(KeyDefinition("123", kind = KeyKind.SWITCH_NUMBERS, weight = 1.45f)) +
                "[]<>!?".map { KeyDefinition(it.toString()) } +
                KeyDefinition("⌫", kind = KeyKind.DELETE, weight = 1.45f)
        ),
        KeyRow(
            listOf(
                KeyDefinition("ABC", kind = KeyKind.SWITCH_LETTERS, weight = 1.45f),
                KeyDefinition(",", weight = 0.9f),
                KeyDefinition("space", value = " ", kind = KeyKind.SPACE, weight = 4.8f),
                KeyDefinition(".", weight = 0.9f),
                KeyDefinition("↵", kind = KeyKind.ENTER, weight = 1.45f)
            )
        )
    )
}
