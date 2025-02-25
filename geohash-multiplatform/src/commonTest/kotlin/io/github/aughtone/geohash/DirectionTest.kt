package io.github.aughtone.geohash

import kotlin.test.Test
import kotlin.test.assertEquals

class DirectionTest {
    @Test
    fun testOpposite() {
        assertEquals(io.github.aughtone.geohash.Direction.LEFT, io.github.aughtone.geohash.Direction.RIGHT.opposite())
        assertEquals(io.github.aughtone.geohash.Direction.RIGHT, io.github.aughtone.geohash.Direction.LEFT.opposite())
        assertEquals(io.github.aughtone.geohash.Direction.TOP, io.github.aughtone.geohash.Direction.BOTTOM.opposite())
        assertEquals(io.github.aughtone.geohash.Direction.BOTTOM, io.github.aughtone.geohash.Direction.TOP.opposite())
    }
}
