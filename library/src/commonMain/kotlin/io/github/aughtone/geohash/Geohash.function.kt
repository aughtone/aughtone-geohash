package io.github.aughtone.geohash

/**
 * Treat a [String] as a geohash
 * @param geohash [String] the geohash to work with
 * @return [String] the geohash, possibly modified inside the block.
 * @throws IllegalArgumentException if the geohash is invalid.
 */
fun geohash(geohash: String, block: GeohashScope.(geohash: String) -> String): String {
    return GeohashScope.block(geohash)
}

/**
 * Treat a [Long] as a geohash
 * @param geohash [Long] the geohash to work with
 * @return [Long] the geohash, possibly modified inside the block.
 * @throws IllegalArgumentException if the geohash is invalid.
 */
fun geohash(geohash: Long, block: GeohashScope.(geohash: String) -> String): Long {
    return Geohash.fromStringToLong(GeohashScope.block(Geohash.fromLongToString(geohash)))
}

/**
 * Encodes a [Coordinate] into a geohash with the given [Accuracy]. Defaults to [Accuracy.Pinpoint]
 *
 * @param coordinate [Coordinate] to encode
 * @param accuracy [Accuracy] (optional) accuracy of the desired Geohash. Defaults to the maximum
 * value of [Accuracy.Pinpoint] (12).
 * @return [String] a Geohash of given length
 * @throws [IllegalArgumentException] if latitude is not between -90 and 90.
 */
fun stringGeohashOf(coordinate: Coordinate, accuracy: Accuracy = Accuracy.Pinpoint): String =
    Geohash.encodeGeohash(coordinate, accuracy.length)

/**
 * Encodes a [Coordinate] into a geohash of given length.
 *
 * @param coordinate [Coordinate] to encode
 * @param length [Int] (optional) length of desired Geohash. Defaults to the max has length (12).
 * @return [String] a Geohash of given length
 * @throws [IllegalArgumentException] if latitude is not between -90 and 90.
 */
fun stringGeohashOf(coordinate: Coordinate, length: Int = Geohash.MAX_HASH_LENGTH): String =
    Geohash.encodeGeohash(coordinate, length)

/**
 * Encodes a pair of [Double] prepresenting latitude and longitude into a [String] Geohash of given
 * length.
 *
 * @param latitude [Double] in decimal degrees (WGS84).
 * @param longitude [Double] in decimal degrees (WGS84).
 * @param length [Int] (optional) length of desired Geohash. Defaults to the max has length (12).
 * @return [String] a Geohash of given length.
 * @throws [IllegalArgumentException] if latitude is not between -90 and 90.
 */
fun stringGeohashOf(
    latitude: Double,
    longitude: Double,
    length: Int = Geohash.MAX_HASH_LENGTH,
): String =
    Geohash.encodeGeohash(latitude, longitude, length)

/**
 * Encodes a [Coordinate] into a [Long] geohash of given length.
 *
 * @param coordinate [Coordinate] to encode
 * @param length [Int] (optional) length of desired Geohash. Defaults to the max has length (12).
 * @return [Long] a Geohash of given length
 * @throws [IllegalArgumentException] if latitude is not between -90 and 90.
 */
fun longGeohashOf(coordinate: Coordinate, length: Int = Geohash.MAX_HASH_LENGTH): Long =
    Geohash.encodeToLong(
        latitude = coordinate.latitude,
        longitude = coordinate.longitude,
        length = length
    )

/**
 * Returns a [Coordinate] as the centre of the given geohash.
 * Latitude will be between -90 and 90 and longitude between -180 and 180.
 *
 * @param geohash [String] the geohash to decode
 * @return [Coordinate] the latitude and longitude represented by the Geohash.
 */
fun coordinateOf(geohash: String): Coordinate = Geohash.decodeGeohash(geohash)

/**
 * Returns a [Coordinate] as the centre of the given geohash.
 * Latitude will be between -90 and 90 and longitude between -180 and 180.
 *
 * @param geohash [Long] the geohash to decode
 * @return [Coordinate] the latitude and longitude represented by the Geohash.
 */
fun coordinateOf(geohash: Long): Coordinate =
    Geohash.decodeGeohash(Geohash.fromLongToString(geohash))
