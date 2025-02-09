package com.github.aughtone.geohash

import kotlin.test.Test
import kotlin.test.assertEquals

class DirectionTest {
    @Test
    fun testOpposite() {
        assertEquals(Direction.LEFT, Direction.RIGHT.opposite())
        assertEquals(Direction.RIGHT, Direction.LEFT.opposite())
        assertEquals(Direction.TOP, Direction.BOTTOM.opposite())
        assertEquals(Direction.BOTTOM, Direction.TOP.opposite())
    }
}
