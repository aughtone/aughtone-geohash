package io.github.aughtone.geohash

import kotlin.test.Test
import kotlin.test.assertEquals

class BineryTestingTest {
    private val testLatitude = 20.05
    private val testLongitude = -15.5

    @Test
    fun testToString() {
        // This test was used to debug the fact that long min values is 1 less bit than in java.
        // see the note on the FIRST_BIT_FLAGGED constant and the encodeHashToLong function.
        val target = (Geohash.FIRST_BIT_FLAGGED ushr (5 * Geohash.MAX_HASH_LENGTH))
        println("target: $target - ${target.toString(2)}")

        val hash = Geohash.encodeGeohash(20.0, 31.0)
        println("hash: $hash")

        assertEquals("sew1c2vs2q5r", hash)


        val mask = -9223372036854775807L - 1
        assertEquals("-9223372036854775808", mask.toString())
        assertEquals("-1000000000000000000000000000000000000000000000000000000000000000", mask.toString(2))
    }

}
