package io.github.aughtone.geohash

import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.pow
import kotlin.math.round


/**
 * Refines interval by a factor or 2 in either the 0 or 1 ordinate.
 *
 * @param interval
 * two entry array of double values
 * @param cd
 * used with mask
 * @param mask
 * used with cd
 */
internal fun refineInterval(interval: DoubleArray, cd: Int, mask: Int) {
    if ((cd and mask) != 0) interval[0] = (interval[0] + interval[1]) / 2
    else interval[1] = (interval[0] + interval[1]) / 2
}

/**
 * Converts an angle in degrees to range -180< x <= 180.
 *
 * @param d angle in degrees
 * @return converted angle in degrees
 */
internal fun to180(d: Double): Double = if (d < 0) {
    -io.github.aughtone.geohash.to180(abs(d))
} else {
    if (d > 180) {
        d - (round(floor((d + 180) / 360.0))) * 360
    } else {
        d
    }
}

/**
 * Returns the width in degrees of the region represented by a geohash of
 * length n.
 *
 * @param n length of geohash
 * @return width in degrees
 */
internal fun calculateWidthDegrees(n: Int): Double = 180 / 2.0.pow(
    2.5 * n + if (n % 2 == 0) {
        -1.0
    } else {
        -0.5
    }
)

/**
 * Returns the height in degrees of the region represented by a geohash of
 * length `n`.
 *
 * @param n length of hash
 * @return height in degrees of the region represented by a geohash of length `n`
 */
internal fun calculateHeightDegrees(n: Int): Double = (180 / 2.0.pow(
    2.5 * n + if (n % 2 == 0) {
        0.0
    } else {
        -0.5
    }
))
