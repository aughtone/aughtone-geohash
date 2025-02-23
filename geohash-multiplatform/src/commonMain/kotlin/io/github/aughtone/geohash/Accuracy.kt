package io.github.aughtone.geohash

/**
 * This enum is used to specify a rough accuracy of the geohash.
 *
 * <pre>
 * Geohash length	Cell width	Cell height
 * 1	≤ 5,000km	×	5,000km
 * 2	≤ 1,250km	×	625km
 * 3	≤ 156km	×	156km
 * 4	≤ 39.1km	×	19.5km
 * 5	≤ 4.89km	×	4.89km
 * 6	≤ 1.22km	×	0.61km
 * 7	≤ 153m	×	153m
 * 8	≤ 38.2m	×	19.1m
 * 9	≤ 4.77m	×	4.77m
 * 10	≤ 1.19m	×	0.596m
 * 11	≤ 149mm	×	149mm
 * 12	≤ 37.2mm	×	18.6mm
 * </pre>
 */
enum class Accuracy(val length:Int) {
    /**
     * About a 5000km area with a cell width x height: ≤ 5,000km × 5,000km
     */
    About5000km(length = 1),
    /**
     * About a 1000km area with a cell width x height: ≤ 1,250km × 625km
     */
    About1000km(length = 2),
    /**
     * About a 150km area with a cell width x height: ≤ 156km × 156km
     */
    About150km(length = 3),
    /**
     * About a 30km area with a cell width x height: ≤ 39.1km × 19.5km
     */
    About30km(length = 4),
    /**
     * About a 5km area with a cell width x height: ≤ 4.89km × 4.89km
     */
    About5km(length = 5),
    /**
     * About a 1km area with a cell width x height: ≤ 1.22km × 0.61km
     */
    About1km(length = 6),
    /**
     * About a 150m area with a cell width x height: ≤ 153m × 153m
     */
    About150m(length = 7),
    /**
     * About a 30m area with a cell width x height: ≤ 38.2m × 19.1m
     */
    About30m(length = 8),
    /**
     * About a 5m area with a cell width x height: ≤ 4.77m × 4.77m
     */
    About5m(length = 9),
    /**
     * About a 1m area with a cell width x height: ≤ 1.19m × 0.596m
     */
    About1m(length = 10),
    /**
     * About a 1/10m or 1dm area with a cell width x height: ≤ 149mm × 149mm
     */
    About1dm(length = 11),
    /**
     * Pinpoint. About a 3cm area with a cell width x height: ≤ 37.2mm × 18.6mm
     */
    About3cm(length = 12);
}
