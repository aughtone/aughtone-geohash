package io.github.aughtone.geohash

object GeohashScope {
    /**
     * Returns the adjacent hash to the right (east).
     */
    fun String.eastOf() = Geohash.right(this)

    /**
     * Returns the adjacent hash to the left (west).
     */
    fun String.westOf() = Geohash.left(this)

    /**
     * Returns the adjacent hash above (north).
     */
    fun String.northOf() = Geohash.top(this)

    /**
     * Returns the adjacent hash below (south).
     */
    fun String.southOf() = Geohash.bottom(this)

    fun String.neighboursOf(): List<String> = Geohash.neighbours(this)



    /**
     * Checks is the [Coordinate] is contained within a given geohash.
     *
     * @param coordinate [Coordinate] to check
     * @return [Boolean]
     */
    infix fun String.contains(coordinate: Coordinate) = Geohash.hashContains(this, coordinate)

    /**
     * Returns the adjacent hash in given [Direction].
     * This function works at borders (at the poles and the -180,180 longitude boundaries).
     *
     * @param direction [Direction] direction relative to this geohash for which the adjacent geohash is returned
     * @return [String] of adjacent geohash
     */
    infix fun String.adjacent(direction: io.github.aughtone.geohash.Direction) = Geohash.adjacentHash(this, direction)


}
