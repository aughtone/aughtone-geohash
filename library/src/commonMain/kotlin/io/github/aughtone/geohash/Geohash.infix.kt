package io.github.aughtone.geohash



infix fun String.geohashContains(coordinate: Coordinate) = Geohash.hashContains(this, coordinate)
