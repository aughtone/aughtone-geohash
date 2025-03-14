package io.github.aughtone.geohash

import kotlin.test.Test
import kotlin.test.assertEquals

class CoverageLongsTest {

    @Test
    fun testCoverageLongsHashLengthZero() {
        val coverage: io.github.aughtone.geohash.CoverageLongs =
            io.github.aughtone.geohash.CoverageLongs(longArrayOf(), 0, 1.0)
        assertEquals(0, coverage.hashLength)
    }

    @Test
    fun testCoverageLongsHashLengthNotZero() {
        val coverage: io.github.aughtone.geohash.CoverageLongs =
            io.github.aughtone.geohash.CoverageLongs(longArrayOf(100, 200, 300, 400, 500), 5, 1.0)
        assertEquals(4, coverage.hashLength)
    }

    @Test
    fun testToString() {
        val coverage: io.github.aughtone.geohash.CoverageLongs =
            io.github.aughtone.geohash.CoverageLongs(longArrayOf(1, 2, 3, 4, 5), 5, 1.0)
        assertEquals("CoverageLongs(hashes=[1, 2, 3, 4, 5], count=5, ratio=1.0)", coverage.toString())
    }

    // FIXME this test is testing the geohas class, not the coverage class.
//    @Test
//    fun testCoverageLongsOfAnAreaThatCantBeCoveredWithHashOfLengthOne() {
//        val coverage: CoverageLongs = GeoHash.coverBoundingBoxLongs(-5, 100, -45, 170, 1)
//        assertEquals(1, coverage.getHashLength())
//    }
}
