package io.github.aughtone.geohash

import kotlin.math.pow
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue


class GeohashTest {
    private val HARTFORD_LON: Double = -72.727175
    private val HARTFORD_LAT: Double = 41.842967
    private val SCHENECTADY_LON: Double = -73.950691
    private val SCHENECTADY_LAT: Double = 42.819581
    private val PRECISION: Double = 0.000000001
    private val I_LEFT: Int = 0
    private val I_RIGHT: Int = 1
    private val I_TOP: Int = 2
    private val I_BOTTOM: Int = 3
    private val I_LEFT_TOP: Int = 4
    private val I_LEFT_BOT: Int = 5
    private val I_RIGHT_TOP: Int = 6
    private val I_RIGHT_BOT: Int = 7

//    @Test
//    fun getCoverageOfPrivateConstructor() {
//        TestingUtil.callConstructorAndCheckIsPrivate(GeoHash::class.java)
//    }

    @Test
    fun encodeHashToLong() {
        assertEquals(0x65c0000000000002L, Geohash.encodeToLong(41.842967, -72.727175, 2))
    }

    @Test
    fun fromLongToStringInvalid() {
//        try {
//            fromLongToString(0xff)
//            fail("Should have thrown exception")
//        } catch (e: IllegalArgumentException) {
//
//        } catch (e: Exception) {
//            fail("Should have thrown an IllegalArgumentException instead of an exception of type ${e::class.simpleName}")
//        }


        assertThrowsIllegalArgumentException { Geohash.fromLongToString(0xff) }
//        assertThrows(java.lang.IllegalArgumentException::class.java) { GeoHash.fromLongToString(0xff) }
    }

    @Test
    fun fromLongToStringZero() {
        assertThrowsIllegalArgumentException { Geohash.fromLongToString(0) }
//        assertThrows(java.lang.IllegalArgumentException::class.java) { fromLongToString(0) }
    }

    @Test
    fun testWhiteHouseHashEncode() {
        assertEquals(
            "dqcjqcp84c6e",
            Geohash.encodeGeohash(38.89710201881826, -77.03669792041183)
        )
    }

    @Test
    fun testWhiteHouseHashEncodeUsingLatLongObject() {
        assertEquals(
            "dqcjqcp84c6e", Geohash.encodeGeohash(
                Coordinate(
                    38.89710201881826,
                    -77.03669792041183
                )
            )
        )
    }

    @Test
    fun testWhiteHouseHashDecode() {
        val point: Coordinate = Geohash.decodeGeohash("dqcjqcp84c6e")
        assertEquals(point.latitude, 38.89710201881826, PRECISION)
        assertEquals(point.longitude, -77.03669792041183, PRECISION)
    }

    @Test
    fun testFromGeoHashDotOrg() {
        assertEquals("6gkzwgjzn820", Geohash.encodeGeohash(-25.382708, -49.265506))
    }

    @Test
    fun testHashOfNonDefaultLength() {
        assertEquals("6gkzwg", Geohash.encodeGeohash(-25.382708, -49.265506, 6))
    }

    @Test
    fun testHashOfLength12() {
        assertEquals("6gkzwgjzn820", Geohash.encodeGeohash(-25.382708, -49.265506, 12))
    }

    @Test
    fun testHashOfLength13() {
        assertThrowsIllegalArgumentException {
            Geohash.encodeGeohash(
                -25.382708,
                -49.265506,
                13
            )
        }
//        assertThrows(java.lang.IllegalArgumentException::class.java) {
//            encodeGeohash(
//                -25.382708,
//                -49.265506,
//                13
//            )
//        }
    }

    @Test
    fun testHashOfLength20() {
        assertThrowsIllegalArgumentException {
            Geohash.encodeGeohash(
                -25.382708,
                -49.265506,
                20
            )
        }
//        assertThrows(java.lang.IllegalArgumentException::class.java) {
//            encodeGeohash(
//                -25.382708,
//                -49.265506,
//                20
//            )
//        }
    }

    @Test
    fun testHashEncodeGivenNonPositiveLength() {
        assertThrowsIllegalArgumentException {
            Geohash.encodeGeohash(
                -25.382708,
                -49.265506,
                0
            )
        }
//        assertThrows(java.lang.IllegalArgumentException::class.java) {
//            encodeGeohash(
//                -25.382708,
//                -49.265506,
//                0
//            )
//        }
    }

    @Test
    fun testAnother() {
        assertEquals("sew1c2vs2q5r", Geohash.encodeGeohash(20.0, 31.0))
    }

    @Test
    fun testEncodeHashWithLatTooBig() {
        assertThrowsIllegalArgumentException {
            Geohash.encodeGeohash(1000.0, 100.0, 4)
        }
//        assertThrows(java.lang.IllegalArgumentException::class.java) { encodeGeohash(1000, 100, 4) }
    }

    @Test
    fun testEncodeHashWithLatTooSmall() {
        assertThrowsIllegalArgumentException {
            Geohash.encodeGeohash(
                -1000.0,
                100.0,
                4
            )
        }
//        assertThrows(java.lang.IllegalArgumentException::class.java) {
//            encodeGeohash(
//                -1000,
//                100,
//                4
//            )
//        }
    }

    // XXX Can't test that since you can't give it a null hash
//    @Test
//    fun testAdjacentHashThrowsExceptionGivenNullHash() {
//        assertThrowsIllegalArgumentException{
//            GeoHash.adjacentHash(
//                null,
//                Direction.RIGHT
//            )
//        }
//        assertThrows(java.lang.IllegalArgumentException::class.java) {
//            GeoHash.adjacentHash(
//                null,
//                Direction.RIGHT
//            )
//        }
//}

    @Test
    fun testAdjacentHashThrowsExceptionGivenBlankHash() {
        assertThrowsIllegalArgumentException {
            Geohash.adjacentHash("", io.github.aughtone.geohash.Direction.RIGHT)
        }
//        assertThrows(java.lang.IllegalArgumentException::class.java) {
//            GeoHash.adjacentHash(
//                "",
//                Direction.RIGHT
//            )
//        }
    }

    @Test
    fun testAdjacentBottom() {
        assertEquals("u0zz", Geohash.adjacentHash("u1pb", io.github.aughtone.geohash.Direction.BOTTOM))
    }

    @Test
    fun testAdjacentTop() {
        assertEquals("u1pc", Geohash.adjacentHash("u1pb", io.github.aughtone.geohash.Direction.TOP))
    }

    @Test
    fun testAdjacentLeft() {
        assertEquals("u1p8", Geohash.adjacentHash("u1pb", io.github.aughtone.geohash.Direction.LEFT))
    }

    @Test
    fun testAdjacentRight() {
        assertEquals("u300", Geohash.adjacentHash("u1pb", io.github.aughtone.geohash.Direction.RIGHT))
    }

    @Test
    fun testLeft() {
        assertEquals("u1p8", Geohash.left("u1pb"))
    }

    @Test
    fun testRight() {
        assertEquals("u300", Geohash.right("u1pb"))
    }

    @Test
    fun testTop() {
        assertEquals("u1pc", Geohash.top("u1pb"))
    }

    @Test
    fun testBottom() {
        assertEquals("u0zz", Geohash.bottom("u1pb"))
    }

    @Test
    fun testNeighbouringHashes() {
        val center = "dqcjqc"
        val neighbours: Set<String> = setOf(
            "dqcjqf", "dqcjqb", "dqcjr1",
            "dqcjq9", "dqcjqd", "dqcjr4", "dqcjr0", "dqcjq8"
        )
        assertEquals(neighbours, Geohash.neighbours(center).toSet())
    }

    @Test
    fun testHashDecodeOnBlankString() {
        val point: Coordinate = Geohash.decodeGeohash("")
        assertEquals(0.0, point.latitude, PRECISION)
        assertEquals(0.0, point.longitude, PRECISION)
    }

    // XXX Not a unit test
//    //    @Test
//    fun testSpeed() {
//        val t: Long = java.lang.System.currentTimeMillis()
//        val numIterations = 10000
//        for (i in 0 until numIterations) encodeGeohash(38.89710201881826, -77.03669792041183)
//        val numPerSecond: Double = (numIterations / (java.lang.System.currentTimeMillis() - t)
//                * 1000).toDouble()
//        println("num encodeHash per second=$numPerSecond")
//    }

    @Test
    fun testMovingHashCentreUpByGeoHashHeightGivesAdjacentHash() {
        val fullHash = "dqcjqcp84c6e"
        for (i in 1..Geohash.MAX_HASH_LENGTH) {
            val hash = fullHash.substring(0, i)
            val top: String = Geohash.top(hash)
            val d: Double = Geohash.heightDegrees(hash.length)
            assertEquals(
                top,
                Geohash.encodeGeohash(Geohash.decodeGeohash(hash).add(d, 0.0), hash.length)
            )
        }
    }

    @Test
    fun testMovingHashCentreUpBySlightlyMoreThanHalfGeoHashHeightGivesAdjacentHash() {
        val fullHash = "dqcjqcp84c6e"
        for (i in 1..Geohash.MAX_HASH_LENGTH) {
            val hash = fullHash.substring(0, i)
            val top: String = Geohash.top(hash)
            val d: Double = Geohash.heightDegrees(hash.length)
            assertEquals(
                top,
                Geohash.encodeGeohash(
                    Geohash.decodeGeohash(hash).add(d / 2 * 1.01, 0.0),
                    hash.length
                )
            )
        }
    }

    @Test
    fun testMovingHashCentreUpBySlightlyLessThanHalfGeoHashHeightGivesSameHash() {
        val fullHash = "dqcjqcp84c6e"
        for (i in 1..Geohash.MAX_HASH_LENGTH) {
            val hash = fullHash.substring(0, i)
            val d: Double = Geohash.heightDegrees(hash.length)
            assertEquals(
                hash,
                Geohash.encodeGeohash(
                    Geohash.decodeGeohash(hash).add(d / 2 * 0.99, 0.0),
                    hash.length
                )
            )
        }
    }

    @Test
    fun testMovingHashCentreRightByGeoHashWidthGivesAdjacentHash() {
        val fullHash = "dqcjqcp84c6e"
        for (i in 1..Geohash.MAX_HASH_LENGTH) {
            val hash = fullHash.substring(0, i)
            val right: String = Geohash.right(hash)
            val d: Double = Geohash.widthDegrees(hash.length)
            assertEquals(
                right,
                Geohash.encodeGeohash(Geohash.decodeGeohash(hash).add(0.0, d), hash.length)
            )
        }
    }

    @Test
    fun testMovingHashCentreRightBySlighltyMoreThanHalfGeoHashWidthGivesAdjacentHash() {
        val fullHash = "dqcjqcp84c6e"
        for (i in 1..Geohash.MAX_HASH_LENGTH) {
            val hash = fullHash.substring(0, i)
            val right: String = Geohash.right(hash)
            val d: Double = Geohash.widthDegrees(hash.length)
            assertEquals(
                right,
                Geohash.encodeGeohash(
                    Geohash.decodeGeohash(hash).add(0.0, d / 2 * 1.01),
                    hash.length
                )
            )
        }
    }

    @Test
    fun testMovingHashCentreRightBySlightlyLessThanHalfGeoHashWidthGivesAdjacentHash() {
        val fullHash = "dqcjqcp84c6e"
        for (i in 1..Geohash.MAX_HASH_LENGTH) {
            val hash = fullHash.substring(0, i)
            val d: Double = Geohash.widthDegrees(hash.length)
            assertEquals(
                hash,
                Geohash.encodeGeohash(
                    Geohash.decodeGeohash(hash).add(0.0, d / 2 * 0.99),
                    hash.length
                )
            )
        }
    }

    /**
     *
     *
     * Use this [link](http://www.lucenerevolution.org/sites/default/files/Lucene%20Rev%20Preso%20Smiley%20Spatial%20Search.pdf) for double-checking.
     *
     */
    @Test
    fun testCoverBoundingBoxWithHashLength4AroundBoston() {
        val hashes: Set<String> = Geohash.coverBoundingBox(
            SCHENECTADY_LAT, SCHENECTADY_LON,
            HARTFORD_LAT, HARTFORD_LON, 4
        ).hashes

        // check schenectady hash
        assertEquals("dre7", Geohash.encodeGeohash(SCHENECTADY_LAT, SCHENECTADY_LON, 4))
        // check hartford hash
        assertEquals("drkq", Geohash.encodeGeohash(HARTFORD_LAT, HARTFORD_LON, 4))
        assertEquals("drs7", Geohash.encodeGeohash(SCHENECTADY_LAT, HARTFORD_LON, 4))
        assertEquals("dr7q", Geohash.encodeGeohash(HARTFORD_LAT, SCHENECTADY_LON, 4))

        // check neighbours
        assertEquals("drs", Geohash.adjacentHash("dre", io.github.aughtone.geohash.Direction.RIGHT))
        assertEquals("dr7", Geohash.adjacentHash("dre", io.github.aughtone.geohash.Direction.BOTTOM))
        assertEquals("drk", Geohash.adjacentHash("drs", io.github.aughtone.geohash.Direction.BOTTOM))

        assertEquals("drdt drdv drej drem dret drev drsj drsm drst drsv drtj \n" +
                "drds drdu dreh drek dres dreu drsh drsk drss drsu drth \n" +
                "drde drdg dre5 DRE7 DREE DREG DRS5 DRS7 drse drsg drt5 \n" +
                "drdd drdf dre4 DRE6 DRED DREF DRS4 DRS6 drsd drsf drt4 \n" +
                "drd9 drdc dre1 DRE3 DRE9 DREC DRS1 DRS3 drs9 drsc drt1 \n" +
                "drd8 drdb dre0 DRE2 DRE8 DREB DRS0 DRS2 drs8 drsb drt0 \n" +
                "dr6x dr6z dr7p DR7R DR7X DR7Z DRKP DRKR drkx drkz drmp \n" +
                "dr6w dr6y dr7n DR7Q DR7W DR7Y DRKN DRKQ drkw drky drmn \n" +
                "dr6t dr6v dr7j dr7m dr7t dr7v drkj drkm drkt drkv drmj \n" +
                "dr6s dr6u dr7h dr7k dr7s dr7u drkh drkk drks drku drmh \n" +
                "dr6e dr6g dr75 dr77 dr7e dr7g drk5 drk7 drke drkg drm5 \n", Geohash.gridAsString("dreb", 5, hashes))
//        java.lang.System.out.println(GeoHash.gridAsString("dreb", 5, hashes))

        // check corners are in
        assertTrue(hashes.contains("dre7"))
        assertTrue(hashes.contains("drkq"))
        assertTrue(hashes.contains("drs7"))
        assertTrue(hashes.contains("dr7q"))
        val expected =
            "dre7,dree,dreg,drs5,drs7,dre6,dred,dref,drs4,drs6,dre3,dre9,drec,drs1,drs3,dre2,dre8,dreb,drs0,drs2,dr7r,dr7x,dr7z,drkp,drkr,dr7q,dr7w,dr7y,drkn,drkq"
        // WTF what the heck is going on here?
        val ex: Set<String> =
            expected.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray().toSet()
//            setOf(
////                java.util.Arrays.asList<T>(
////                    *expected.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
////                )
//            )
        println(ex)
        println(hashes)
        assertEquals(ex, hashes)
    }

    @Test
    fun testCoverBoundingBoxWithHashLengthOneAroundBoston() {
        val coverage: Coverage? = Geohash.coverBoundingBox(
            SCHENECTADY_LAT, SCHENECTADY_LON,
            HARTFORD_LAT, HARTFORD_LON, 1
        )
        assertNotNull(coverage)
        assertEquals(setOf("d"), coverage.hashes)
        assertEquals(1, coverage.hashLength)
        println(coverage.ratio)
        assertEquals(1694.6984366342194, coverage.ratio, PRECISION)
    }

    @Test
    fun testCoverBoundingBoxWithOptimalHashLengthAroundBoston() {
        val coverage: Coverage? = Geohash.coverBoundingBox(
            SCHENECTADY_LAT, SCHENECTADY_LON,
            HARTFORD_LAT, HARTFORD_LON
        )
        assertNotNull(coverage)
        assertEquals(4, coverage.hashes.size)
        assertEquals(3, coverage.hashLength)
    }

    @Test
    fun testCoverBoundingBoxWithMaxHashesThrowsException() {
        val coverage: Coverage? = Geohash.coverBoundingBoxMaxHashes(
            SCHENECTADY_LAT,
            SCHENECTADY_LON, HARTFORD_LAT, HARTFORD_LON, 0
        )
        assertNull(coverage)
    }

    @Test
    fun testCoverBoundingBoxWithMaxHashesIsOne() {
        val coverage: Coverage? = Geohash.coverBoundingBoxMaxHashes(
            SCHENECTADY_LAT,
            SCHENECTADY_LON, HARTFORD_LAT, HARTFORD_LON, 1
        )
        assertNotNull(coverage)
        assertEquals(1, coverage.hashes.size)
        assertEquals(2, coverage.hashLength)
    }

    @Test
    fun testCoverBoundingBoxWithMaxHashesReturnsMoreThanMax() {
        val coverage: Coverage? = Geohash.coverBoundingBoxMaxHashes(
            SCHENECTADY_LAT,
            SCHENECTADY_LON, SCHENECTADY_LAT - 0.000000001,
            SCHENECTADY_LON + 0.000000001, Int.MAX_VALUE
        )
        assertNotNull(coverage)
        assertEquals(Geohash.MAX_HASH_LENGTH, coverage.hashLength)
    }

    @Test
    fun testDisplayOfCoverages() {
        assertEquals("Coverage(hashes=[d], ratio=1694.6984366342194)", Geohash.coverBoundingBox(SCHENECTADY_LAT, SCHENECTADY_LON, HARTFORD_LAT, HARTFORD_LON, 1).toString())
        assertEquals("Coverage(hashes=[dr], ratio=52.95932614481936)", Geohash.coverBoundingBox(SCHENECTADY_LAT, SCHENECTADY_LON, HARTFORD_LAT, HARTFORD_LON, 2).toString())
        assertEquals("Coverage(hashes=[dr7, drk, dre, drs], ratio=6.61991576810242)", Geohash.coverBoundingBox(SCHENECTADY_LAT, SCHENECTADY_LON, HARTFORD_LAT, HARTFORD_LON, 3).toString())

//        for (i in 1..6) {
//            val coverage: Coverage = GeoHash.coverBoundingBox(
//                SCHENECTADY_LAT,
//                SCHENECTADY_LON, HARTFORD_LAT, HARTFORD_LON, i
//            )
////            println(
////                ("length="
////                        + i
////                        + ",numHashes="
////                        + coverage.hashes.size
////                        + ", ratio="
////                        + coverage.ratio
////                        + ", processingTimeFactor="
////                        + (coverage.hashes.size.pow(2.0) * GeoHash.widthDegrees(i) * GeoHash.heightDegrees(
////                    i
////                )))
////            )
//        }
    }

    @Test
    fun testEnclosingHashLengthAroundBoston() {
        val length: Int = Geohash.hashLengthToCoverBoundingBox(
            SCHENECTADY_LAT,
            SCHENECTADY_LON, HARTFORD_LAT, HARTFORD_LON
        )
        val hashes: Set<String> = Geohash.coverBoundingBox(
            SCHENECTADY_LAT, SCHENECTADY_LON,
            HARTFORD_LAT, HARTFORD_LON, length
        ).hashes
        assertEquals(setOf("dr"), hashes)
    }

    @Test
    fun testCoverBoundingBoxWithHashLength3AroundBoston() {
        val hashes: Set<String> = Geohash.coverBoundingBox(
            SCHENECTADY_LAT, SCHENECTADY_LON,
            HARTFORD_LAT, HARTFORD_LON, 3
        ).hashes
        assertEquals(setOf("dr7", "dre", "drk", "drs"), hashes)
    }

    @Test
    fun testCoverBoundingBoxWithZeroLengthThrowsException() {
        assertThrowsIllegalArgumentException {
            Geohash.coverBoundingBox(
                SCHENECTADY_LAT, SCHENECTADY_LON, HARTFORD_LAT,
                HARTFORD_LON, 0
            )
        }
//        assertThrows(
//            java.lang.IllegalArgumentException::class.java
//        ) {
//            GeoHash.coverBoundingBox(
//                SCHENECTADY_LAT, SCHENECTADY_LON, HARTFORD_LAT,
//                HARTFORD_LON, 0
//            )
//        }
    }

    @Test
    fun testGeoHashLengthAcrossLongitude180() {
        assertEquals(
            0, Geohash.hashLengthToCoverBoundingBox(
                71.676351, 178.389963, 70.633291,
                -177.116629
            )
        )
    }

    @Test
    fun testGeoHashLengthAcrossEcuador() {
        assertEquals(
            0, Geohash.hashLengthToCoverBoundingBox(
                2.935289, -82.751538, -5.623710,
                -74.753492
            )
        )
    }

    @Test
    fun testGeoHashLengthAcrossSouthPole() {
        assertEquals(
            0, Geohash.hashLengthToCoverBoundingBox(
                -80, 10, 80,
                20
            )
        )
    }

    @Test
    fun testGeoHashWidthDegrees() {
        Geohash.encodeGeohash(-25.382708, -49.265506, 6)
        Geohash.encodeGeohash(-25.382708, -49.265506, 5)
        Geohash.encodeGeohash(-25.382708, -49.265506, 4)
        Geohash.encodeGeohash(-25.382708, -49.265506, 3)
        Geohash.encodeGeohash(-25.382708, -49.265506, 2)
        Geohash.encodeGeohash(-25.382708, -49.265506, 1)
        assertEquals(45.0, Geohash.widthDegrees(1), 0.00001)

        assertEquals(11.25, Geohash.widthDegrees(2), 0.00001)
        assertEquals(1.40625, Geohash.widthDegrees(3), 0.00001)
        assertEquals(0.3515625, Geohash.widthDegrees(4), 0.00001)
        assertEquals(0.0439453125, Geohash.widthDegrees(5), 0.00001)
        assertEquals(0.010986328125, Geohash.widthDegrees(6), 0.00001)
    }

    @Test
    fun testGeoHashHeightDegrees() {
        Geohash.encodeGeohash(-25.382708, -49.265506, 6)
        Geohash.encodeGeohash(-25.382708, -49.265506, 5)
        Geohash.encodeGeohash(-25.382708, -49.265506, 4)
        Geohash.encodeGeohash(-25.382708, -49.265506, 3)
        Geohash.encodeGeohash(-25.382708, -49.265506, 2)
        Geohash.encodeGeohash(-25.382708, -49.265506, 1)
        assertEquals(45.0, Geohash.heightDegrees(1), 0.00001)

        assertEquals(11.25 / 2, Geohash.heightDegrees(2), 0.00001)
        assertEquals(1.40625, Geohash.heightDegrees(3), 0.00001)
        assertEquals(0.3515625 / 2, Geohash.heightDegrees(4), 0.00001)
        assertEquals(0.0439453125, Geohash.heightDegrees(5), 0.00001)
        assertEquals(0.010986328125 / 2, Geohash.heightDegrees(6), 0.00001)
    }

    @Test
    fun testHeightDegreesForLengthEqualsZero() {
        assertEquals(180.0, Geohash.heightDegrees(0), PRECISION)
    }

    @Test
    fun testHeightDegreesForLengthGreaterThanMax() {
        assertEquals(4.190951585769653e-8, Geohash.heightDegrees(13), PRECISION)
    }

    @Test
    fun testWidthDegreesForLengthEqualsZero() {
        assertEquals(360.0, Geohash.widthDegrees(0), PRECISION)
    }

    @Test
    fun testWidthDegreesForLengthGreaterThanMax() {
        assertEquals(4.190951585769653e-8, Geohash.widthDegrees(13), PRECISION)
    }

    @Test
    fun testGridToString() {
        assertEquals(
            "drdr drdx drdz drep drer drex drez drsp drsr drsx drsz \n" +
                    "drdq drdw drdy dren dreq drew drey drsn drsq drsw drsy \n" +
                    "drdm drdt drdv drej drem dret drev drsj drsm drst drsv \n" +
                    "drdk drds drdu dreh drek dres dreu drsh drsk drss drsu \n" +
                    "drd7 drde drdg dre5 dre7 dree dreg drs5 drs7 drse drsg \n" +
                    "drd6 drdd drdf dre4 dre6 dred dref drs4 drs6 drsd drsf \n" +
                    "drd3 drd9 drdc dre1 dre3 dre9 drec drs1 drs3 drs9 drsc \n" +
                    "drd2 drd8 drdb dre0 dre2 dre8 dreb drs0 drs2 drs8 drsb \n" +
                    "dr6r dr6x dr6z dr7p dr7r dr7x dr7z drkp drkr drkx drkz \n" +
                    "dr6q dr6w dr6y dr7n dr7q dr7w dr7y drkn drkq drkw drky \n" +
                    "dr6m dr6t dr6v dr7j dr7m dr7t dr7v drkj drkm drkt drkv \n",
            Geohash.gridAsString("dred", -5, -5, 5, 5)
        )
        assertEquals(
            "f0 f2 f8 \n" +
                    "dp dr dx \n" +
                    "dn dq dw \n", Geohash.gridAsString(
                "dr", 1,
                emptySet<String>()
            )
        )
//        println(GeoHash.gridAsString("dred", -5, -5, 5, 5))
//        println(
//            GeoHash.gridAsString(
//                "dr", 1,
//                emptySet<String>()
//            )
//        )
    }

    @Test
    fun testHashContains() {
        val centre: Coordinate = Geohash.decodeGeohash("dre7")
        assertTrue(
            Geohash.hashContains(
                "dre7", centre.latitude,
                centre.longitude
            )
        )
        assertFalse(
            Geohash.hashContains(
                "dre7", centre.latitude + 20,
                centre.longitude
            )
        )
        assertFalse(
            Geohash.hashContains(
                "dre7", centre.latitude,
                centre.longitude + 20
            )
        )
    }

    @Test
    fun testHashContainsNearLongitudeBoundary() {
        val hash: String = Geohash.encodeGeohash(-25.0, -179.0, 1)
        assertFalse(Geohash.hashContains(hash, -25.0, 179.0))
        assertTrue(Geohash.hashContains(hash, -25.0, -178.0))
    }

    @Test
    fun testHashLengthToEncloseBoundingBoxReturns0IfBoxTooBig() {
        assertEquals(
            0,
            Geohash.hashLengthToCoverBoundingBox(80, -170, -80, 170)
        )
    }

    @Test
    fun testRightNeighbourCloseTo180Longitude() {
        assertEquals("2", Geohash.adjacentHash("r", io.github.aughtone.geohash.Direction.RIGHT))
    }

    @Test
    fun testLeftNeighbourCloseToMinus180Longitude() {
        assertEquals("r", Geohash.adjacentHash("2", io.github.aughtone.geohash.Direction.LEFT))
    }

    @Test
    fun testTopNeighbourCloseToNorthPole() {
        val hash: String = Geohash.encodeGeohash(90.0, 0.0, 1)
        assertEquals("u", hash)
        assertEquals("b", Geohash.adjacentHash(hash, io.github.aughtone.geohash.Direction.TOP))
    }

    @Test
    fun testBottomNeighbourCloseToSouthPole() {
        val hash: String = Geohash.encodeGeohash(-90.0, 0.0, 1)
        assertEquals("h", hash)
        assertEquals("0", Geohash.adjacentHash(hash, io.github.aughtone.geohash.Direction.BOTTOM))
    }

    // Nice map here at poles
    // http://www.bigdatamodeling.org/2013/01/intuitive-geohash.html
    @Test
    fun testNeighboursAtSouthPole() {
        val poleHash: String = Geohash.encodeGeohash(-90.0, 0.0)
        assertEquals("h00000000000", poleHash)

        val neighbors: List<String> = Geohash.neighbours(poleHash)
        assertEquals(8, neighbors.size)

        assertEquals("5bpbpbpbpbpb", neighbors[I_LEFT])
        assertEquals("h00000000002", neighbors[I_RIGHT])
        assertEquals("h00000000001", neighbors[I_TOP])
        assertEquals("00000000000p", neighbors[I_BOTTOM])
        assertEquals("5bpbpbpbpbpc", neighbors[I_LEFT_TOP])
        assertEquals("pbpbpbpbpbpz", neighbors[I_LEFT_BOT])
        assertEquals("h00000000003", neighbors[I_RIGHT_TOP])
        assertEquals("00000000000r", neighbors[I_RIGHT_BOT])
    }

    @Test
    fun testNeighboursAtNorthPole() {
        val poleHash: String = Geohash.encodeGeohash(90.0, 0.0)
        assertEquals("upbpbpbpbpbp", poleHash)

        val neighbors: List<String> = Geohash.neighbours(poleHash)
        assertEquals(8, neighbors.size)

        assertEquals("gzzzzzzzzzzz", neighbors[I_LEFT])
        assertEquals("upbpbpbpbpbr", neighbors[I_RIGHT])
        assertEquals("bpbpbpbpbpb0", neighbors[I_TOP])
        assertEquals("upbpbpbpbpbn", neighbors[I_BOTTOM])
        assertEquals("zzzzzzzzzzzb", neighbors[I_LEFT_TOP])
        assertEquals("gzzzzzzzzzzy", neighbors[I_LEFT_BOT])
        assertEquals("bpbpbpbpbpb2", neighbors[I_RIGHT_TOP])
        assertEquals("upbpbpbpbpbq", neighbors[I_RIGHT_BOT])
    }

    @Test
    fun testNeighboursAtLongitude180() {
        val hash: String = Geohash.encodeGeohash(0.0, 180.0)
        assertEquals("xbpbpbpbpbpb", hash)

        val neighbors: List<String> = Geohash.neighbours(hash)
        assertEquals(8, neighbors.size)

        assertEquals("xbpbpbpbpbp8", neighbors[I_LEFT])
        assertEquals("800000000000", neighbors[I_RIGHT])
        assertEquals("xbpbpbpbpbpc", neighbors[I_TOP])
        assertEquals("rzzzzzzzzzzz", neighbors[I_BOTTOM])
        assertEquals("xbpbpbpbpbp9", neighbors[I_LEFT_TOP])
        assertEquals("rzzzzzzzzzzx", neighbors[I_LEFT_BOT])
        assertEquals("800000000001", neighbors[I_RIGHT_TOP])
        assertEquals("2pbpbpbpbpbp", neighbors[I_RIGHT_BOT])
    }

    @Test
    fun testNeighboursAtLongitudeMinus180() {
        val hash: String = Geohash.encodeGeohash(0.0, -180.0)
        assertEquals("800000000000", hash)

        val neighbors: List<String> = Geohash.neighbours(hash)
        println(neighbors)
        assertEquals(8, neighbors.size)

        assertEquals("xbpbpbpbpbpb", neighbors[I_LEFT])
        assertEquals("800000000002", neighbors[I_RIGHT])
        assertEquals("800000000001", neighbors[I_TOP])
        assertEquals("2pbpbpbpbpbp", neighbors[I_BOTTOM])
        assertEquals("xbpbpbpbpbpc", neighbors[I_LEFT_TOP])
        assertEquals("rzzzzzzzzzzz", neighbors[I_LEFT_BOT])
        assertEquals("800000000003", neighbors[I_RIGHT_TOP])
        assertEquals("2pbpbpbpbpbr", neighbors[I_RIGHT_BOT])
    }


    @Test
    fun testCoverBoundingBoxPreconditionLat() {
        assertThrowsIllegalArgumentException {
            Geohash.coverBoundingBox(
                0.0,
                100.0,
                10.0,
                120.0
            )
        }
//        assertThrows(java.lang.IllegalArgumentException::class.java) {
//            GeoHash.coverBoundingBox(
//                0,
//                100,
//                10,
//                120
//            )
//        }
    }
}


private fun Number.pow(d: Double): Double = this.toDouble().pow(d)
