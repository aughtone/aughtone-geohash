package io.github.aughtone.geohash.support

internal val Long.lowerNibble get() = (this and 0b1111).toByte()
