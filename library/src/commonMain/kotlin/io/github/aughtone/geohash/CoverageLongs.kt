package io.github.aughtone.geohash

/**
 * A set of hashes represented by longs and a measure of how well those hashes
 * cover a region. Immutable.
 *
 * ratio: the measure of how well the hashes cover a region. The ratio is
 *       the total area of hashes divided by the area of the bounding box in
 *       degrees squared. The closer the ratio is to 1 the better the more closely
 *       the hashes approximate the bounding box.
 *
 * @param hashes The hashes providing the coverage.
 * @param count The number of hashes.
 * @param ratio ratio of area of hashes to the area of target region. How well the coverage
 *  is covered by the hashes. Will be >=1. Closer to 1 the close the coverage is to the region in question.
 */
data class CoverageLongs(val hashes: LongArray, val count: Int, val ratio: Double) {
    /**
     * Returns the length in characters of the first hash returned by an
     * iterator on the hash set. All hashes should be of the same length in this
     * coverage.
     *
     * @return length of the hash
     */
    val hashLength: Int = if (count == 0) 0
    else (hashes[0] and 0x0f).toInt()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as io.github.aughtone.geohash.CoverageLongs

        if (!hashes.contentEquals(other.hashes)) return false
        if (count != other.count) return false
        if (ratio != other.ratio) return false

        return true
    }

    override fun hashCode(): Int {
        var result = hashes.contentHashCode()
        result = 31 * result + count
        result = 31 * result + ratio.hashCode()
        return result
    }
}

fun io.github.aughtone.geohash.CoverageLongs?.toCoverage(): io.github.aughtone.geohash.Coverage? = if (this == null) {
    null
} else {
    io.github.aughtone.geohash.Coverage(this)
}
