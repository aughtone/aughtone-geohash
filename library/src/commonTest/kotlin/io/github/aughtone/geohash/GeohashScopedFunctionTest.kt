package io.github.aughtone.geohash

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GeohashScopedFunctionTest {
    private val testLatitude = 20.05
    private val testLongitude = -15.5
    private val testCoordinate = Coordinate(testLatitude, testLongitude)
    private val testGeohash = "eesfp3cpxy"
    private val testGeohashLong = 7741945652739808556L

    @Test
    fun testStringFunctionHasScope() {
        val actual = geohash(testGeohash) {
            assertTrue(it contains testCoordinate)
            assertEquals("eesfp3cpxv", it.southOf())
            assertEquals("eesfp3cpxz", it adjacent Direction.TOP)
            it
        }
        assertEquals(testGeohash, actual)
    }

    @Test
    fun testLongFunctionHasScope() {
        val actual = geohash(testGeohashLong) {
            assertTrue(it contains testCoordinate)
            assertEquals("eesfp3cpxyf7", it.southOf())
            assertEquals("eesfp3cpxyfm", it adjacent Direction.TOP)
            it
        }
        assertEquals(testGeohashLong, actual)
    }
}
