package com.dripxiecon.opendyslexic.layouts

enum class KeyKind { TEXT, SPACE, DELETE, ENTER }

data class KeyDefinition(
    val label: String,
    val value: String = label,
    val kind: KeyKind = KeyKind.TEXT,
    val alternatives: List<String> = emptyList()
)
