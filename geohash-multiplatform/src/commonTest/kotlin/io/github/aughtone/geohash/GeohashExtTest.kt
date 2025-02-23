package io.github.aughtone.geohash

import io.github.aughtone.types.quantitative.Coordinates
import kotlin.test.Test
import kotlin.test.assertEquals

class GeohashExtTest {
    private val testLatitude = 20.05
    private val testLongitude = -15.5
    private val testCoordinate = Coordinates(testLatitude, testLongitude)

    private val testGeohash = "9q9hr5udfr"

    @Test
    fun testTruncatesToAccuracyWhenLonger() {
        assertEquals("9q9", testGeohash.toAccuracy(Accuracy.About150km))
        assertEquals("9q9hr", testGeohash.toAccuracy(Accuracy.About5km))
        assertEquals("9q9hr5udf", testGeohash.toAccuracy(Accuracy.About5m))
    }

    @Test
    fun testTruncatesToAccuracyWhenNotLonger() {
        assertEquals("9q9hr", "9q9hr".toAccuracy(Accuracy.About5km))
        assertEquals("9q9hr", "9q9hr".toAccuracy(Accuracy.About1km))
    }

}
