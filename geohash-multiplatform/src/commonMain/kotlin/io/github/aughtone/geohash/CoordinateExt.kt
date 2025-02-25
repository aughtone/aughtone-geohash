package io.github.aughtone.geohash

import io.github.aughtone.types.quantitative.Coordinates

/**
 * Convert a [Coordinate] to a [String] geohash.
 */
fun Coordinates.toGeohash(): String = toGeohash(length = Geohash.MAX_HASH_LENGTH)

/**
 * Convert a [Coordinate] to a [String] geohash of the specified length. Defaults to [Accuracy.About3cm.length] (12).
 */
fun Coordinates.toGeohash(length: Int = Geohash.MAX_HASH_LENGTH): String =
    Geohash.encodeGeohash(this, length)

/**
 * Convert a [Coordinate] to a [Long] geohash of the specified [Accuracy]. Defaults to [Accuracy.About3cm] (12).
 */
fun Coordinates.toGeohash(accuracy: Accuracy = Accuracy.About3cm): String =
    Geohash.encodeGeohash(this, length = accuracy.length)

/**
 * Convert a [Coordinate] to a [Long] geohash with the given length. Defaults to [Accuracy.About3cm.length] (12).
 */
fun Coordinates.toGeohashLong(length: Int = Geohash.MAX_HASH_LENGTH): Long =
    Geohash.encodeToLong(this, length = length)

/**
 * infix function Check if a [Coordinate] is within a given geohash.
 */
infix fun Coordinates.within(geohash: String) = Geohash.hashContains(geohash, this)
