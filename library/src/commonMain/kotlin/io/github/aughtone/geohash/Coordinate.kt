package io.github.aughtone.geohash

/**
 * A lat, long pair (WGS84). Immutable.
 */
data class Coordinate(val latitude: Double, val longitude: Double) {
    /**
     * Returns a new [Coordinate] object with latitude, and longitude increased by deltaLatitude,
     * and deltaLongitude.
     *
     * @param deltaLatitude change in latitude
     * @param deltaLongitude change in longitude
     * @return [Coordinate] with increased latitude and longitude
     */
    fun add(deltaLatitude: Double, deltaLongitude: Double): Coordinate {
        return Coordinate(latitude + deltaLatitude, longitude + deltaLongitude)
    }

    operator fun invoke(deltaLatitude: Double, deltaLongitude: Double): Coordinate {
        return Coordinate(latitude + deltaLatitude, longitude + deltaLongitude)
    }

    /**
     * Adds two [Coordinate] together with an operator function.
     * Example usage: val coord3 = coord1 + coord2
     */
    operator fun plus(other: Coordinate): Coordinate {
        return Coordinate(latitude + other.latitude, longitude + other.longitude)
    }
}

/**
 * Split a [Coordinate] into a [Pair] of doubles.
 */
fun Coordinate.split(): Pair<Double, Double> = Pair(this.latitude, this.longitude)

/**
 * Convert a [Coordinate] to a [String] geohash.
 */
fun Coordinate.toGeohash(): String = toGeohash(length = Geohash.MAX_HASH_LENGTH)

/**
 * Convert a [Coordinate] to a [String] geohash of the specified length. Defaults to [Accuracy.Pinpoint.length] (12).
 */
fun Coordinate.toGeohash(length: Int = Geohash.MAX_HASH_LENGTH): String =
    Geohash.encodeGeohash(this, length)

/**
 * Convert a [Coordinate] to a [Long] geohash of the specified [Accuracy]. Defaults to [Accuracy.Pinpoint] (12).
 */
fun Coordinate.toGeohash(accuracy: Accuracy = Accuracy.Pinpoint): String =
    Geohash.encodeGeohash(this, length = accuracy.length)

/**
 * Convert a [Coordinate] to a [Long] geohash with the given length. Defaults to [Accuracy.Pinpoint.length] (12).
 */
fun Coordinate.toGeohashLong(length: Int = Geohash.MAX_HASH_LENGTH): Long =
    Geohash.encodeToLong(this, length = length)

/**
 * infix function Check if a [Coordinate] is within a given geohash.
 */
infix fun Coordinate.within(geohash: String) = Geohash.hashContains(geohash, this)
