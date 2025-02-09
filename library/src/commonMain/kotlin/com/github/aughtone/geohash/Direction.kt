package com.github.aughtone.geohash

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
    fun opposite(): Direction {
        return when (this) {
            BOTTOM -> TOP
            TOP -> BOTTOM
            LEFT -> RIGHT
            RIGHT -> LEFT
        }
    }
}
