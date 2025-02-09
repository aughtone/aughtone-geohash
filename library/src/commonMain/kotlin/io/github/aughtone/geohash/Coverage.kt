package io.github.aughtone.geohash

/**
 * A set of hashes and a measure of how well those hashes cover a region.
 * Immutable.
 *
 * The ratio is how well the coverage is covered by the hashes. Will be >=1. Closer to 1
 * the close the coverage is to the region in question.
 *
 * @param hashes
 *            set of hashes comprising the coverage
 * @param ratio
 *            ratio of area of hashes to the area of target region
 */
data class Coverage(val hashes: Set<String>, val ratio: Double) {

    //    Coverage(CoverageLongs coverage) {
//        this.ratio = coverage.getRatio();
//        this.hashes = new TreeSet<String>();
//        for(Long l : coverage.getHashes()) {
//            hashes.add(GeoHash.fromLongToString(l));
//        }
//    }
    constructor(coverage: io.github.aughtone.geohash.CoverageLongs) : this(hashes = coverage.hashes.map {
        Geohash.fromLongToString(
            it
        )
    }.toSet(), coverage.ratio)

    /**
     * Returns the length in characters of the first hash returned by an
     * iterator on the hash set. All hashes should be of the same length in this
     * coverage.
     */
    val hashLength: Int = if (hashes.isEmpty()) 0
    else hashes.iterator().next().length

}
