package io.github.aughtone.geohash

import io.github.aughtone.types.quantitative.Coordinates
import io.github.aughtone.types.quantitative.split
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class CoordinatesTest {
    private val testLatitude = 20.05
    private val testLongitude = -15.5
    private val testCoordinate = Coordinates(testLatitude, testLongitude)

    @Test
    fun testToString() {
        assertEquals(
            "Coordinates(latitude=20.05, longitude=-15.5, accuracy=null)",
            testCoordinate.toString()
        )
    }

    @Test
    fun testHashCode() {
        val a: Coordinates = testCoordinate
        val b: Coordinates = Coordinates(testLatitude, testLongitude)

        assertEquals(a.hashCode(), b.hashCode())
        assertEquals(a, b)
        assertTrue(a == b)
    }

    @Test
    fun testInfixEquals() {
        val a: Coordinates = testCoordinate
        val b: Coordinates = Coordinates(testLatitude, testLongitude)

        assertTrue(a == b)
    }

    @Test
    fun testInfixAdd() {
        val a: Coordinates = testCoordinate
        val b = a(1.0, 1.0)
        assertNotEquals(a, b)
        assertEquals(21.05, b.latitude)
        assertEquals(-14.5, b.longitude)
    }

    @Test
    fun testSplitCoordinateIntoPair() {
        val actual = testCoordinate.split()
        assertEquals(testCoordinate.latitude, actual.first)
        assertEquals(testCoordinate.longitude, actual.second)
    }

    @Test
    fun testToGeohashWithLength() {
        assertEquals("eesfp3cpxyfk", testCoordinate.toGeohash())
        assertEquals("eesfp3", testCoordinate.toGeohash(6))
        assertEquals("eesf", testCoordinate.toGeohash(4))
        assertEquals("e", testCoordinate.toGeohash(1))
    }

    @Test
    fun testToGeohashWithAccuracy() {
        assertEquals("ees", testCoordinate.toGeohash(Accuracy.About150km))
        assertEquals("eesfp3cpx", testCoordinate.toGeohash(Accuracy.About5m))
        assertEquals("eesfp3cpxy", testCoordinate.toGeohash(Accuracy.About1m))
    }

    @Test
    fun testGeohashWithinInfuxFunction() {
        val geohash = testCoordinate.toGeohash()
        assertTrue(testCoordinate within geohash)
    }
}
