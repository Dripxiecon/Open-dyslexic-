package com.dripxiecon.opendyslexic.layouts

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class KeyboardLayoutsTest {
    @Test
    fun letterLayoutHasFourRowsAndQwertyOrder() {
        val rows = KeyboardLayouts.letters()
        assertEquals(4, rows.size)
        assertEquals("qwertyuiop", rows.first().keys.joinToString("") { it.label })
    }

    @Test
    fun uppercaseLayoutChangesLabelsButKeepsTypedValuesLowercase() {
        val firstKey = KeyboardLayouts.letters(uppercase = true).first().keys.first()
        assertEquals("Q", firstKey.label)
        assertEquals("q", firstKey.value)
    }

    @Test
    fun everyLayoutHasPositiveKeyWidths() {
        val layouts = listOf(
            KeyboardLayouts.letters(),
            KeyboardLayouts.numbers(),
            KeyboardLayouts.symbols()
        )
        layouts.flatten().flatMap { it.keys }.forEach {
            assertTrue("Every key must have positive width", it.weight > 0f)
        }
    }

    @Test
    fun bottomRowsProvideSpaceAndEnterActions() {
        listOf(
            KeyboardLayouts.letters(),
            KeyboardLayouts.numbers(),
            KeyboardLayouts.symbols()
        ).forEach { rows ->
            val kinds = rows.last().keys.map { it.kind }
            assertTrue(kinds.contains(KeyKind.SPACE))
            assertTrue(kinds.contains(KeyKind.ENTER))
        }
    }
}
