package io.github.aughtone.geohash.support

import io.github.aughtone.geohash.support.charIndexOfBase32
import io.github.aughtone.geohash.support.decodeBase32
import io.github.aughtone.geohash.support.encodeBase32
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class Base32Test {


    @Test
    fun testEncodePositiveInteger() {
        assertEquals("15pn7", encodeBase32(1234567, 5))
    }

    @Test
    fun testEncodesZero() {
        assertEquals("0", encodeBase32(0, 1))
    }

    @Test
    fun testEncodesNegativeInteger() {
        assertEquals("-3v", encodeBase32(-123, 2))
    }

    @Test
    fun testDecodeToPositiveInteger() {
        assertEquals(1234567, decodeBase32("15pn7"))
    }

    @Test
    fun testDecodeToZero() {
        assertEquals("0", encodeBase32(0, 1))
        assertEquals(0, decodeBase32("0"))
    }

    @Test
    fun testDecodeThenEncodeIsIdentity() {
        assertEquals("1000", encodeBase32(decodeBase32("1000"), 4))
    }

    @Test
    fun testDecodeManyZeros() {
        assertEquals(0, decodeBase32("0000000"))
    }

    @Test
    fun testDecodeOneZero() {
        assertEquals(0, decodeBase32("0"))
    }

    @Test
    fun testDecodeToNegativeInteger() {
        assertEquals("-3v", encodeBase32(-123, 2))
        assertEquals(-123, decodeBase32("-3v"))
    }

    @Test
    fun testEncodePadsToLength12() {
        assertEquals("00000000003v", encodeBase32(123))
    }

    @Test
    fun testGetCharIndexThrowsExceptionWhenNonBase32CharacterGiven() {
        try {
            charIndexOfBase32('?')
            fail("Expected IllegalArgumentException to be thrown")
        }catch(e:IllegalArgumentException){
            // Expected outcome, noop
        }catch (e:Exception){
            fail("Expected IllegalArgumentException instead of ${e::class.simpleName}")
        }
//        assertThrows(java.lang.IllegalArgumentException::class.java) { Base32.getCharIndex('?') }
    }

//    @Test
//    fun getCoverageOfConstructorAndCheckConstructorIsPrivate() {
//        TestingUtil.callConstructorAndCheckIsPrivate(Base32::class.java)
//    }

}
