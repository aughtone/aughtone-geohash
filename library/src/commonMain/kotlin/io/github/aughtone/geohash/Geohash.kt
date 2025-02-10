// Translated from JS to Java by David Moten, 2013
// Translated from Java to Kotlin by Brill Pappin, 2025

package io.github.aughtone.geohash

import io.github.aughtone.geohash.support.lowerNibble
import kotlin.math.abs


/**
 * Utility functions for
 * [geohashing](http://en.wikipedia.org/wiki/Geohash).
 */
object Geohash {
    /**
     * The standard practical maximum length for geohashes.
     */
    const val MAX_HASH_LENGTH = 12

    // XXX In java -0x8000000000000000 comes out to -9223372036854775808 which works fine but
    //  assigning -9223372036854775808 in kotlin is an error, so we set it to max value and then
    //  subtract one to get what we need.
    //  Since this is used for bit masking, the bits are important for the encoding to work properly
    //  in the GeoHash.encodeHashToLong function --bpappin
    internal const val FIRST_BIT_FLAGGED = -9223372036854775807L - 1


    /**
     * The characters used in base 32 representations.
     */
    internal const val BASE32: String = "0123456789bcdefghjkmnpqrstuvwxyz"
    private const val PRECISION = 0.000000000001


    /**
     * Powers of 2 from 32 down to 1.
     */
    private val BITS = intArrayOf(16, 8, 4, 2, 1)


    /**
     * Utility lookup for neighbouring hashes.
     */
    private val NEIGHBOURS: Map<io.github.aughtone.geohash.Direction, Map<Parity, String>> = mapOf(
        io.github.aughtone.geohash.Direction.BOTTOM to mapOf(
            Parity.EVEN to "14365h7k9dcfesgujnmqp0r2twvyx8zb",
            Parity.ODD to "238967debc01fg45kmstqrwxuvhjyznp",
        ),
        io.github.aughtone.geohash.Direction.TOP to mapOf(
            Parity.EVEN to "p0r21436x8zb9dcf5h7kjnmqesgutwvy",
            Parity.ODD to "bc01fg45238967deuvhjyznpkmstqrwx",
        ),
        io.github.aughtone.geohash.Direction.LEFT to mapOf(
            Parity.EVEN to "238967debc01fg45kmstqrwxuvhjyznp",
            Parity.ODD to "14365h7k9dcfesgujnmqp0r2twvyx8zb",
        ),
        io.github.aughtone.geohash.Direction.RIGHT to mapOf(
            Parity.EVEN to "bc01fg45238967deuvhjyznpkmstqrwx",
            Parity.ODD to "p0r21436x8zb9dcf5h7kjnmqesgutwvy",
        )
    )

    /**
     * Utility lookup for hash borders.
     */
    private val BORDERS: Map<io.github.aughtone.geohash.Direction, Map<Parity, String>> = mapOf(
        io.github.aughtone.geohash.Direction.BOTTOM to mapOf(
            Parity.EVEN to "028b",
            Parity.ODD to "0145hjnp",
        ),
        io.github.aughtone.geohash.Direction.TOP to mapOf(
            Parity.EVEN to "prxz",
            Parity.ODD to "bcfguvyz",
        ),
        io.github.aughtone.geohash.Direction.LEFT to mapOf(
            Parity.EVEN to "0145hjnp",
            Parity.ODD to "028b",
        ),
        io.github.aughtone.geohash.Direction.RIGHT to mapOf(
            Parity.EVEN to "bcfguvyz",
            Parity.ODD to "prxz",
        )
    )

    private val HASH_WIDTH_DEGREES: Array<Double> = Array<Double>(MAX_HASH_LENGTH + 1) { i: Int ->
        io.github.aughtone.geohash.calculateWidthDegrees(i)
    }

    private val HASH_HEIGHT_DEGREES: DoubleArray = DoubleArray(MAX_HASH_LENGTH + 1) { i: Int ->
        io.github.aughtone.geohash.calculateHeightDegrees(i)
    }

    /**
     * Returns the adjacent hash in given [Direction]. Based on
     * https://github.com/davetroy/geohash-js/blob/master/geohash.js. This
     * method is an improvement on the original js method because it works at
     * borders too (at the poles and the -180,180 longitude boundaries).
     *
     * @param hash
     * string hash relative to which the adjacent is returned
     * @param direction
     * direction relative to `hash` for which the adjacent is
     * returned
     * @return hash of adjacent hash
     */
    fun adjacentHash(hash: String, direction: io.github.aughtone.geohash.Direction): String {
        require(hash.isNotEmpty()) { "adjacent has no meaning for a zero length hash that covers the whole world" }

        val adjacentHashAtBorder = adjacentHashAtBorder(hash, direction)
        if (adjacentHashAtBorder != null) return adjacentHashAtBorder

        val source = hash.lowercase()
        val lastChar = source[source.length - 1]
        val parity: Parity = if ((source.length % 2 == 0)) Parity.EVEN else Parity.ODD
        var base = source.substring(0, source.length - 1)
        if (BORDERS[direction]!![parity]!!.indexOf(lastChar) != -1) base =
            adjacentHash(base, direction)
        return base + BASE32[NEIGHBOURS[direction]!![parity]!!.indexOf(
            lastChar
        )]
    }

    /**
     * check if hash is on edge and direction would push us over the edge if so, wrap round to the
     * other limit for longitude or if at latitude boundary (a pole) then spin longitude
     * around 180 degrees.
     */
    private fun adjacentHashAtBorder(hash: String, direction: io.github.aughtone.geohash.Direction): String? {
        // check if hash is on edge and direction would push us over the edge
        // if so, wrap round to the other limit for longitude
        // or if at latitude boundary (a pole) then spin longitude around 180
        // degrees.
        val centre: Coordinate = decodeGeohash(hash)

        when (direction) {
            io.github.aughtone.geohash.Direction.BOTTOM -> if (abs(centre.latitude - widthDegrees(hash.length) / 2 + 90) < PRECISION) {
                return encodeGeohash(centre.latitude, centre.longitude + 180, hash.length)
            }

            io.github.aughtone.geohash.Direction.TOP -> if (abs(centre.latitude + widthDegrees(hash.length) / 2 - 90) < PRECISION) {
                return encodeGeohash(centre.latitude, centre.longitude + 180, hash.length)
            }

            io.github.aughtone.geohash.Direction.LEFT -> if (abs(centre.longitude - widthDegrees(hash.length) / 2 + 180) < PRECISION) {
                return encodeGeohash(centre.latitude, 180.0, hash.length)
            }

            io.github.aughtone.geohash.Direction.RIGHT -> if (abs(centre.longitude + widthDegrees(hash.length) / 2 - 180) < PRECISION) {
                return encodeGeohash(centre.latitude, -180.0, hash.length)
            }
        }

        // XXX This is wierd in kotlin, we should revisit wha this is doing once all the tests are passing. --bpappin
        return null
    }

    /**
     * Returns the adjacent hash to the right (east).
     *
     * @param hash
     * to check
     * @return hash on right of given hash
     */
    fun right(hash: String): String {
        return adjacentHash(hash, io.github.aughtone.geohash.Direction.RIGHT)
    }

    /**
     * Returns the adjacent hash to the left (west).
     *
     * @param hash
     * origin
     * @return hash on left of origin hash
     */
    fun left(hash: String): String {
        return adjacentHash(hash, io.github.aughtone.geohash.Direction.LEFT)
    }

    /**
     * Returns the adjacent hash to the top (north).
     *
     * @param hash
     * origin
     * @return hash above origin hash
     */
    fun top(hash: String): String {
        return adjacentHash(hash, io.github.aughtone.geohash.Direction.TOP)
    }

    /**
     * Returns the adjacent hash to the bottom (south).
     *
     * @param hash
     * origin
     * @return hash below (south) of origin hash
     */
    fun bottom(hash: String): String {
        return adjacentHash(hash, io.github.aughtone.geohash.Direction.BOTTOM)
    }

    /**
     * Returns the adjacent hash N steps in the given [Direction]. A
     * negative N will use the opposite [Direction].
     *
     * @param hash
     * origin hash
     * @param direction
     * to desired hash
     * @param steps
     * number of hashes distance to desired hash
     * @return hash at position in direction a number of hashes away (steps)
     */
    fun adjacentHash(hash: String, direction: io.github.aughtone.geohash.Direction, steps: Int): String {
        if (steps < 0) return adjacentHash(
            hash, direction.opposite(),
            abs(steps.toDouble()).toInt()
        )
        else {
            var h = hash
            for (i in 0 until steps) h = adjacentHash(h, direction)
            return h
        }
    }

    /**
     * Returns a list of the 8 surrounding hashes for a given hash in order
     * left,right,top,bottom,left-top,left-bottom,right-top,right-bottom.
     *
     * @param hash
     * source
     * @return a list of neighbour hashes
     */
    fun neighbours(hash: String): List<String> {
        val left = adjacentHash(hash, io.github.aughtone.geohash.Direction.LEFT)
        val right = adjacentHash(hash, io.github.aughtone.geohash.Direction.RIGHT)
        return listOf(
            left,
            right,
            adjacentHash(hash, io.github.aughtone.geohash.Direction.TOP),
            adjacentHash(hash, io.github.aughtone.geohash.Direction.BOTTOM),
            adjacentHash(left, io.github.aughtone.geohash.Direction.TOP),
            adjacentHash(left, io.github.aughtone.geohash.Direction.BOTTOM),
            adjacentHash(right, io.github.aughtone.geohash.Direction.TOP),
            adjacentHash(right, io.github.aughtone.geohash.Direction.BOTTOM),
        )
    }

    /**
     * Takes a hash represented as a long and returns it as a string.
     *
     * @param hash the hash, with the length encoded in the 4 least significant
     * bits
     * @return the string encoded geohash
     */
    internal fun fromLongToString(hash: Long): String {
        var newHash = hash
        val length = newHash.lowerNibble.toInt()
        require(!(length > 12 || length < 1)) { "invalid long geohash $hash" }
        val geohash = CharArray(length)
        for (pos in 0 until length) {
            geohash[pos] = BASE32[(newHash ushr 59).toInt()]
            newHash = newHash shl 5
        }
        return geohash.joinToString("")
    }

    internal fun fromStringToLong(geohash: String): Long  = encodeToLong(decodeGeohash(geohash), geohash.length)

    fun encodeToLong(coordinate: Coordinate, length: Int): Long = encodeToLong(
        latitude = coordinate.latitude,
        longitude = coordinate.longitude,
        length = length
    )

    fun encodeToLong(latitude: Double, longitude: Double, length: Int): Long {
        var isEven = true
        var minLat = -90.0
        var maxLat = 90.0
        var minLon = -180.0
        var maxLon = 180.0
        var bit = FIRST_BIT_FLAGGED
        var g: Long = 0

        val target = (FIRST_BIT_FLAGGED ushr (5 * length))
        while (bit != target) {
            if (isEven) {
                val mid = (minLon + maxLon) / 2
                if (longitude >= mid) {
                    g = g or bit
                    minLon = mid
                } else maxLon = mid
            } else {
                val mid = (minLat + maxLat) / 2
                if (latitude >= mid) {
                    g = g or bit
                    minLat = mid
                } else maxLat = mid
            }

            isEven = !isEven
            bit = bit ushr 1
        }

        return g or length.toLong()
    }


    fun hashLengthToCoverBoundingBox(
        topLeftLat: Int, topLeftLon: Int,
        bottomRightLat: Int, bottomRightLon: Int,
    ): Int = hashLengthToCoverBoundingBox(
        topLeftLat = topLeftLat.toDouble(), topLeftLon = topLeftLon.toDouble(),
        bottomRightLat = bottomRightLat.toDouble(), bottomRightLon = bottomRightLon.toDouble(),
    )

    /**
     * Returns the maximum length of hash that covers the bounding box. If no
     * hash can enclose the bounding box then 0 is returned.
     *
     * @param topLeftLat
     * latitude of top left point (north west)
     * @param topLeftLon
     * longitude of top left point (north west)
     * @param bottomRightLat
     * latitude of bottom right point (south east)
     * @param bottomRightLon
     * longitude of bottom right point (south east)
     * @return length of the hash
     */
    fun hashLengthToCoverBoundingBox(
        topLeftLat: Double, topLeftLon: Double,
        bottomRightLat: Double, bottomRightLon: Double,
    ): Int {
        var isEven = true
        var minLat = -90.0
        var maxLat = 90.0
        var minLon = -180.0
        var maxLon = 180.0

        for (bits in 0 until MAX_HASH_LENGTH * 5) {
            if (isEven) {
                val mid = (minLon + maxLon) / 2
                if (topLeftLon >= mid) {
                    if (bottomRightLon < mid) return bits / 5
                    minLon = mid
                } else {
                    if (bottomRightLon >= mid) return bits / 5
                    maxLon = mid
                }
            } else {
                val mid = (minLat + maxLat) / 2
                if (topLeftLat >= mid) {
                    if (bottomRightLat < mid) return bits / 5
                    minLat = mid
                } else {
                    if (bottomRightLat >= mid) return bits / 5
                    maxLat = mid
                }
            }

            isEven = !isEven
        }
        return MAX_HASH_LENGTH
    }

    /**
     * Returns true if and only if the bounding box corresponding to the hash
     * contains the given lat and long.
     *
     * @param hash
     * hash to test containment in
     * @param latitude [Double]
     * latitude
     * @param longitude [Double]
     * longitude
     * @return true if and only if the hash contains the given lat and long
     */
    fun hashContains(geohash: String, latitude: Double, longitude: Double): Boolean {
        val centre: Coordinate = decodeGeohash(geohash)
        return abs(centre.latitude - latitude) <= heightDegrees(geohash.length) / 2
                && abs(io.github.aughtone.geohash.to180(centre.longitude - longitude)) <= widthDegrees(geohash.length) / 2
    }

    fun hashContains(geohash: String, coordinate: Coordinate): Boolean = hashContains(
        geohash = geohash,
        latitude = coordinate.latitude,
        longitude = coordinate.longitude
    )

    /**
     * Returns the hashes that are required to cover the given bounding box. The
     * maximum length of hash is selected that satisfies the number of hashes
     * returned is less than `maxHashes`. Returns null if hashes
     * cannot be found satisfying that condition. Maximum hash length returned
     * will be [Geohash].MAX_HASH_LENGTH.
     *
     * @param topLeftLat
     * latitude of top left point (north west)
     * @param topLeftLon
     * longitude of top left point (north west)
     * @param bottomRightLat
     * latitude of bottom right point (south east)
     * @param bottomRightLon
     * longitude of bottom right point (south east)
     * @param maxHashes
     * maximum number of hashes to use to cover the box
     * @return coverage
     */
    fun coverBoundingBoxMaxHashes(
        topLeftLat: Double,
        topLeftLon: Double,
        bottomRightLat: Double,
        bottomRightLon: Double,
        maxHashes: Int,
    ): Coverage? {

        // find start length, and make sure its at least 1
        val startLength = hashLengthToCoverBoundingBox(
            topLeftLat, topLeftLon, bottomRightLat,
            bottomRightLon
        ).let { if (it == 0) 1 else it }


        var coverageL: io.github.aughtone.geohash.CoverageLongs? = null
        for (length in startLength..MAX_HASH_LENGTH) {
            val cl: io.github.aughtone.geohash.CoverageLongs = coverBoundingBoxLongs(
                topLeftLat, topLeftLon, bottomRightLat,
                bottomRightLon, length
            )

            if (cl.count > maxHashes) {
                return coverageL.toCoverage()
            } else {
                coverageL = cl
            }
        }
        // note coverage can never be null
        return coverageL.toCoverage()
    }

    /**
     * Returns the hashes of given length that are required to cover the given
     * bounding box.
     *
     * @param topLeftLat
     * latitude of top left point (north west)
     * @param topLeftLon
     * longitude of top left point (north west)
     * @param bottomRightLat
     * latitude of bottom right point (south east)
     * @param bottomRightLon
     * longitude of bottom right point (south east)
     * @param length
     * of hash
     * @return number of hashes of given length required to cover the given
     * bounding box
     */
    fun coverBoundingBox(
        topLeftLat: Double, topLeftLon: Double,
        bottomRightLat: Double, bottomRightLon: Double, length: Int,
    ): Coverage {
        return Coverage(
            coverBoundingBoxLongs(
                topLeftLat, topLeftLon, bottomRightLat,
                bottomRightLon, length
            )
        )
    }

    fun coverBoundingBoxLongs(
        topLeftLat: Double,
        topLeftLon: Double,
        bottomRightLat: Double,
        bottomRightLon: Double,
        length: Int,
    ): io.github.aughtone.geohash.CoverageLongs {
        require(topLeftLat >= bottomRightLat) { "topLeftLat must be >= bottomRightLat" }
        require(length > 0) { "length must be greater than zero" }

        var newTopLeftLon = topLeftLon
        var newBottomRightLon = bottomRightLon

        val actualWidthDegreesPerHash = widthDegrees(length)
        val actualHeightDegreesPerHash = heightDegrees(length)

        val hashes = mutableSetOf<Long>()

        var diff = newBottomRightLon - newTopLeftLon
        if (diff < 0) {
            // case where bottomRightLon cross the antimeridian
            newBottomRightLon += 360.0
            diff = newBottomRightLon - newTopLeftLon
        } else if (diff > 360) {
            // case where this bounding box displays more than one copy of the world
            newTopLeftLon = -180.0
            newBottomRightLon = 180.0
            diff = 360.0
        }

        run {
            var lat = bottomRightLat
            while (lat <= topLeftLat) {
                var lon = newTopLeftLon
                while (lon <= newBottomRightLon) {
                    hashes.add(encodeToLong(lat, io.github.aughtone.geohash.to180(lon), length))
                    lon += actualWidthDegreesPerHash
                }
                lat += actualHeightDegreesPerHash
            }
        }
        // ensure have the borders covered
        var lat = bottomRightLat
        while (lat <= topLeftLat) {
            hashes.add(encodeToLong(lat, io.github.aughtone.geohash.to180(newBottomRightLon), length))
            lat += actualHeightDegreesPerHash
        }
        var lon = newTopLeftLon
        while (lon <= newBottomRightLon) {
            hashes.add(encodeToLong(topLeftLat, io.github.aughtone.geohash.to180(lon), length))
            lon += actualWidthDegreesPerHash
        }
        // ensure that the topRight corner is covered
        hashes.add(encodeToLong(topLeftLat,
            io.github.aughtone.geohash.to180(newBottomRightLon), length))

        val areaDegrees = diff * (topLeftLat - bottomRightLat)
        val coverageAreaDegrees = hashes.count() * widthDegrees(length) * heightDegrees(length)
        val ratio = coverageAreaDegrees / areaDegrees
        return io.github.aughtone.geohash.CoverageLongs(hashes.toLongArray(), hashes.count(), ratio)
    }

    /**
     * Returns height in degrees of all geohashes of length `n`. Results are
     * deterministic and cached to increase performance.
     *
     * @param n
     * length of geohash
     * @return height in degrees of the geohash with length `n`
     */
    fun heightDegrees(n: Int): Double {
        return if (n > MAX_HASH_LENGTH) io.github.aughtone.geohash.calculateHeightDegrees(
            n
        )
        else HASH_HEIGHT_DEGREES[n]
    }

    /**
     * Returns width in degrees of all geohashes of length n. Results are
     * deterministic and cached to increase performance (might be unnecessary,
     * have not benchmarked).
     *
     * @param n length of hash
     * @return width in degrees
     */
    fun widthDegrees(n: Int): Double {
        return if (n > MAX_HASH_LENGTH) io.github.aughtone.geohash.calculateWidthDegrees(
            n
        )
        else HASH_WIDTH_DEGREES[n]
    }

    /**
     *
     *
     * Returns a String of lines of hashes to represent the relative positions
     * of hashes on a map. The grid is of height and width 2*size centred around
     * the given hash. Highlighted hashes are displayed in upper case. For
     * example, gridToString("dr",1,Collections.&lt;String&gt;emptySet()) returns:
     *
     *
     * <pre>
     * f0 f2 f8
     * dp dr dx
     * dn dq dw
     * </pre>
     *
     * @param hash central hash
     * @param size size of square grid in hashes
     * @param highlightThese hashes to highlight
     * @return String representation of grid
     */
    fun gridAsString(hash: String, size: Int, highlightThese: Set<String?>): String {
        return gridAsString(hash, -size, -size, size, size, highlightThese)
    }

    /**
     *
     * Returns a String of lines of hashes to represent the relative positions
     * of hashes on a map. Highlighted hashes are displayed in upper case. For
     * example, gridToString("dr",-1,-1,1,1, setOf("f2","f8")) returns:
     *
     *
     * <pre>
     * f0 F2 F8
     * dp dr dx
     * dn dq dw
     * </pre>
     *
     * @param hash reference hash
     * @param fromRight
     * top left of the grid in hashes to the right (can be negative).
     * @param fromBottom
     * top left of the grid in hashes to the bottom (can be
     * negative).
     * @param toRight
     * bottom righth of the grid in hashes to the bottom (can be
     * negative).
     * @param toBottom
     * bottom right of the grid in hashes to the bottom (can be
     * negative).
     * @param highlightThese hashes to highlight
     * @return String representation of grid
     */
    fun gridAsString(
        hash: String, fromRight: Int, fromBottom: Int, toRight: Int,
        toBottom: Int, highlightThese: Set<String?> = emptySet<String>(),
    ): String {
        val buffer = StringBuilder().apply {
            for (bottom in fromBottom..toBottom) {
                for (right in fromRight..toRight) {
                    var h = adjacentHash(hash, io.github.aughtone.geohash.Direction.RIGHT, right)
                    h = adjacentHash(h, io.github.aughtone.geohash.Direction.BOTTOM, bottom)
                    if (highlightThese.contains(h)) h = h.uppercase()
                    append(h).append(" ")
                }
                append("\n")
            }
        }
        return buffer.toString()
    }

    /**
     * Returns the result of coverBoundingBoxMaxHashes with a maxHashes value of
     * [Geohash].DEFAULT_MAX_HASHES.
     *
     * @param topLeftLat
     * latitude of top left point (north west)
     * @param topLeftLon
     * longitude of top left point (north west)
     * @param bottomRightLat
     * latitude of bottom right point (south east)
     * @param bottomRightLon
     * longitude of bottom right point (south east)
     * @return coverage
     */
    fun coverBoundingBox(
        topLeftLat: Double, topLeftLon: Double,
        bottomRightLat: Double, bottomRightLon: Double,
    ): Coverage? {
        // FIXME maybe change to Coordinate instead of random Doubles.
        return coverBoundingBoxMaxHashes(
            topLeftLat, topLeftLon, bottomRightLat, bottomRightLon,
            MAX_HASH_LENGTH
        )
    }

    /**
     * Returns a geohash of given length for the given WGS84 point.
     *
     * @param coordinate
     * point
     * @param length
     * length of hash
     * @return hash at point of given length
     */
    fun encodeGeohash(coordinate: Coordinate, length: Int): String {
        return encodeGeohash(coordinate.latitude, coordinate.longitude, length)
    }

    /**
     * Returns a geohash of of length [Geohash.MAX_HASH_LENGTH] (12) for
     * the given WGS84 point.
     *
     * @param coordinate
     * point
     * @return hash of default length
     */
    fun encodeGeohash(coordinate: Coordinate): String {
        return encodeGeohash(coordinate.latitude, coordinate.longitude, MAX_HASH_LENGTH)
    }

    /**
     * Returns a geohash of given length for the given WGS84 point
     * (latitude,longitude). If latitude is not between -90 and 90 throws an
     * [IllegalArgumentException].
     *
     * @param latitude
     * in decimal degrees (WGS84)
     * @param longitude
     * in decimal degrees (WGS84)
     * @param length
     * length of desired hash
     * @return geohash of given length for the given point
     */
    fun encodeGeohash(latitude: Double, longitude: Double, length: Int = MAX_HASH_LENGTH): String {
        require(length in 1..12) { "length must be between 1 and 12" }
        require(latitude >= -90 && latitude <= 90) { "latitude must be between -90 and 90 inclusive" }
        return fromLongToString(encodeToLong(latitude,
            io.github.aughtone.geohash.to180(longitude), length))
    }

    /**
     * Returns a latitude,longitude pair as the centre of the given geohash.
     * Latitude will be between -90 and 90 and longitude between -180 and 180.
     *
     * @param geohash
     * hash to decode
     * @return lat long point
     */
    fun decodeGeohash(geohash: String): Coordinate {
//        requireNotNull(geohash){"geohash cannot be null"}
        var isEven = true
        val lat = DoubleArray(2)
        val lon = DoubleArray(2)
        lat[0] = -90.0
        lat[1] = 90.0
        lon[0] = -180.0
        lon[1] = 180.0

        for (i in 0 until geohash.length) {
            val c = geohash[i]
            val cd = BASE32.indexOf(c)
            for (j in 0..4) {
                val mask = BITS[j]
                if (isEven) {
                    io.github.aughtone.geohash.refineInterval(lon, cd, mask)
                } else {
                    io.github.aughtone.geohash.refineInterval(lat, cd, mask)
                }
                isEven = !isEven
            }
        }
        val resultLat = (lat[0] + lat[1]) / 2
        val resultLon = (lon[0] + lon[1]) / 2

        return Coordinate(resultLat, resultLon)
    }
}
