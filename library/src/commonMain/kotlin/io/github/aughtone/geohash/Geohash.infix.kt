package io.github.aughtone.geohash

import io.github.aughtone.types.quantitative.Coordinates


infix fun String.geohashContains(coordinate: Coordinates) = Geohash.hashContains(this, coordinate)
