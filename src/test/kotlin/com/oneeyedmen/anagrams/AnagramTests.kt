package com.oneeyedmen.anagrams

import com.oneeyedmen.okeydoke.Approver
import com.oneeyedmen.okeydoke.junit5.ApprovalsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledIfSystemProperty
import org.junit.jupiter.api.extension.RegisterExtension
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

val words: List<String> = File("./words.txt").readLines()

@Suppress("JUnitMalformedDeclaration")
class AnagramTests {

    @Test
    fun `could be made from the letters in`() {
        assertTrue("A".couldBeMadeFromTheLettersIn("A CAT"))
        assertTrue("CAT".couldBeMadeFromTheLettersIn("A CAT"))
        assertTrue("AA".couldBeMadeFromTheLettersIn("A CAT"))
        assertTrue("ACT".couldBeMadeFromTheLettersIn("A CAT"))

        assertFalse("H".couldBeMadeFromTheLettersIn("A CAT"))
        assertFalse("AAH".couldBeMadeFromTheLettersIn("A CAT"))
        assertFalse("TAT".couldBeMadeFromTheLettersIn("A CAT"))

        assertTrue("".couldBeMadeFromTheLettersIn("A CAT"))
        assertTrue("".couldBeMadeFromTheLettersIn(""))
    }

    @Test
    fun `anagrams for A CAT`() {
        assertEquals(
            listOf("A ACT", "A CAT", "ACTA"),
            words.anagramsFor("A CAT", 3)
        )
    }

    @Test
    fun `anagrams for REFACTORING TO`(approver: Approver) {
        approver.assertApproved(
            words.anagramsFor("REFACTORING TO").joinToString("\n")
        )
    }

    @Test
    @EnabledIfSystemProperty(named = "run-slow-tests", matches = "true")
    fun `anagrams for REFACTORING TO KOTLIN depth 3`(approver: Approver) {
        approver.assertApproved(
            words.anagramsFor("REFACTORING TO KOTLIN", depth = 3).joinToString("\n")
        )
    }

    @Test fun `letter bit sets`() {
        assertEquals("".toLetterBitSet(), "".toLetterBitSet())
        assertEquals(0, "".toLetterBitSet())
        assertEquals(1, "A".toLetterBitSet())
        assertEquals(1, "AA".toLetterBitSet())
        assertEquals(2, "B".toLetterBitSet())
        assertEquals("A".toLetterBitSet(), "A".toLetterBitSet())
        assertEquals("A".toLetterBitSet(), "AA".toLetterBitSet())
        assertEquals("B".toLetterBitSet(), "B".toLetterBitSet())
        assertEquals("BA".toLetterBitSet(), "AB".toLetterBitSet())
        assertEquals("BA".toLetterBitSet(), "ABA".toLetterBitSet())

        assertFalse("A".toLetterBitSet().hasLettersNotIn("A".toLetterBitSet()))
        assertFalse("AA".toLetterBitSet().hasLettersNotIn("A".toLetterBitSet()))
        assertFalse("A".toLetterBitSet().hasLettersNotIn("AA".toLetterBitSet()))
        assertTrue("AB".toLetterBitSet().hasLettersNotIn("A".toLetterBitSet()))
        assertFalse("A".toLetterBitSet().hasLettersNotIn("AB".toLetterBitSet()))
    }

    @Suppress("unused")
    companion object {
        @RegisterExtension
        @JvmField
        val approvals = ApprovalsExtension("src/test/kotlin")
    }
}



