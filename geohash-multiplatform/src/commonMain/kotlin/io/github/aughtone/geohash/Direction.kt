package io.github.aughtone.geohash

/**
 * Directions on a WGS84 projection. top = north, bottom = south, left = west,
 * right = east.
 */
enum class Direction {
    BOTTOM, TOP, LEFT, RIGHT;

    /**
     * Returns the opposite direction. For example LEFT.opposite() == RIGHT.
     *
     * @return the opposite direction
     */
    fun opposite(): io.github.aughtone.geohash.Direction {
        return when (this) {
            io.github.aughtone.geohash.Direction.BOTTOM -> io.github.aughtone.geohash.Direction.TOP
            io.github.aughtone.geohash.Direction.TOP -> io.github.aughtone.geohash.Direction.BOTTOM
            io.github.aughtone.geohash.Direction.LEFT -> io.github.aughtone.geohash.Direction.RIGHT
            io.github.aughtone.geohash.Direction.RIGHT -> io.github.aughtone.geohash.Direction.LEFT
        }
    }
}
