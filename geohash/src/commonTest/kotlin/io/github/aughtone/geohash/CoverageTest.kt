package io.github.aughtone.geohash

import kotlin.test.Test
import kotlin.test.assertEquals

class CoverageTest {

    @Test
    fun testCoverageHashLengthZero() {
        val coverage: io.github.aughtone.geohash.Coverage =
            io.github.aughtone.geohash.Coverage(emptySet(), 1.0)
        assertEquals(0, coverage.hashLength)
        // get coverage of toString
//        java.lang.System.out.println(coverage)
    }

    @Test
    fun testCoverageHashLengthNotZero() {
        val coverage: io.github.aughtone.geohash.Coverage =
            io.github.aughtone.geohash.Coverage(setOf("100", "200", "300"), 1.0)
        assertEquals(3, coverage.hashLength)
        // get coverage of toString
//        java.lang.System.out.println(coverage)
    }

    @Test
    fun testCoverageToString() {
        val coverage: io.github.aughtone.geohash.Coverage =
            io.github.aughtone.geohash.Coverage(setOf("100", "200", "300"), 1.0)
        assertEquals("Coverage(hashes=[100, 200, 300], ratio=1.0)", coverage.toString())
        // get coverage of toString
//        java.lang.System.out.println(coverage)
    }

    //FIXME GeoHash test?
//    @Test
//    fun testCoverageOfAnAreaThatCantBeCoveredWithHashOfLengthOne() {
//        val coverage: Coverage = GeoHash.coverBoundingBox(-5, 100, -45, 170)
//        assertEquals(1, coverage.getHashLength())
//        assertEquals(Sets.newHashSet("q", "r"), coverage.getHashes())
//    }

    //FIXME GeoHash test?
//    /**
//     * test copied from https://github.com/davidmoten/geo/pull/25/files to validate bug fix
//     */
//    @Test
//    fun testWideCoverage() {
//        val top = -1.0
//        val left = -26.0
//        val bottom = -2.0
//        val right = 175.0
//
//        val coverage: Coverage = GeoHash.coverBoundingBox(top, left, bottom, right, 1)
//        assertTrue(coverage.getHashes().contains("r"))
//    }

    //FIXME GeoHash test?
    /**
     * test copied from https://github.com/davidmoten/geo/issues/34 to validate bug fix
     */
//    @Test
//    fun testCoverageAllWorld() {
//        val topLeftLat = 90.0
//        val topLeftLon = -179.0
//        val bottomRightLat = -90.0
//        val bottomRightLon = 180.0
//        val coverage: Coverage =
//            GeoHash.coverBoundingBox(topLeftLat, topLeftLon, bottomRightLat, bottomRightLon, 1)
//        assertEquals(
//            Sets.newHashSet(
//                "0",
//                "1",
//                "2",
//                "3",
//                "4",
//                "5",
//                "6",
//                "7",
//                "8",
//                "9",
//                "b",
//                "c",
//                "d",
//                "e",
//                "f",
//                "g",
//                "h",
//                "j",
//                "k",
//                "m",
//                "n",
//                "p",
//                "q",
//                "r",
//                "s",
//                "t",
//                "u",
//                "v",
//                "w",
//                "x",
//                "y",
//                "z"
//            ),
//            coverage.getHashes()
//        )
//    }

    //FIXME GeoHash test?
//    @Test
//    fun testCoverageAllWorldLeaflet() {
//        val topLeftLat = 90.0
//        val topLeftLon = -703.0
//        val bottomRightLat = -90.0
//        val bottomRightLon = 624.0
//        val coverage: Coverage =
//            GeoHash.coverBoundingBox(topLeftLat, topLeftLon, bottomRightLat, bottomRightLon, 1)
//        assertEquals(
//            Sets.newHashSet(
//                "0",
//                "1",
//                "2",
//                "3",
//                "4",
//                "5",
//                "6",
//                "7",
//                "8",
//                "9",
//                "b",
//                "c",
//                "d",
//                "e",
//                "f",
//                "g",
//                "h",
//                "j",
//                "k",
//                "m",
//                "n",
//                "p",
//                "q",
//                "r",
//                "s",
//                "t",
//                "u",
//                "v",
//                "w",
//                "x",
//                "y",
//                "z"
//            ),
//            coverage.getHashes()
//        )
//    }

    //FIXME GeoHash test?
//    @Test
//    fun testCoverageAntimeridianGoogleMaps() {
//        val topLeftLat = 39.0
//        val topLeftLon = 156.0
//        val bottomRightLat = 3.0
//        val bottomRightLon = -118.0
//        val coverage: Coverage =
//            GeoHash.coverBoundingBox(topLeftLat, topLeftLon, bottomRightLat, bottomRightLon, 1)
//        assertEquals(Sets.newHashSet("x", "8", "9"), coverage.getHashes())
//    }

    //FIXME GeoHash test?
//    @Test
//    fun testCoverageAntimeridianLeaflet() {
//        val topLeftLat = 39.0
//        val topLeftLon = -204.0
//        val bottomRightLat = 2.0
//        val bottomRightLon = -121.0
//        val coverage: Coverage =
//            GeoHash.coverBoundingBox(topLeftLat, topLeftLon, bottomRightLat, bottomRightLon, 1)
//        assertEquals(Sets.newHashSet("x", "8", "9"), coverage.getHashes())
//    }

//FIXME GeoHash test?
//    @Test
//    fun testCoverageAntimeridianLeaflet2() {
//        val topLeftLat = 44.0
//        val topLeftLon = 110.0
//        val bottomRightLat = 9.0
//        val bottomRightLon = 194.0
//        val coverage: Coverage =
//            GeoHash.coverBoundingBox(topLeftLat, topLeftLon, bottomRightLat, bottomRightLon, 1)
//        assertEquals(Sets.newHashSet("w", "x", "8"), coverage.getHashes())
//    }

    //FIXME GeoHash test?
//    @Test
//    fun testCoverageMaxHashes() {
//        val topLeftLat = 50.1112
//        val topLeftLon = -6.8167
//        val bottomRightLat = 46.6997
//        val bottomRightLon = 3.7301
//        val coverage: Coverage = GeoHash.coverBoundingBoxMaxHashes(
//            topLeftLat,
//            topLeftLon,
//            bottomRightLat,
//            bottomRightLon,
//            64
//        )
//        assertEquals(3, coverage.getHashLength())
//        assertEquals(24, coverage.getHashes().size())
//    }
}
