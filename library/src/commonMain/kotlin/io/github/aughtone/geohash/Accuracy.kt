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
     * Cell width x height: ≤ 5,000km × 5,000km
     */
    FiveThousandKilometres(length = 1),
    /**
     * Cell width x height: ≤ 1,250km × 625km
     */
    OneThousandKilometres(length = 2),
    /**
     * Cell width x height: ≤ 156km × 156km
     */
    OneHundredFiftyKilometres(length = 3),
    /**
     * Cell width x height: ≤ 39.1km × 19.5km
     */
    ThirtyKilometres(length = 4),
    /**
     * Cell width x height: ≤ 4.89km × 4.89km
     */
    FiveKilometres(length = 5),
    /**
     * Cell width x height: ≤ 1.22km × 0.61km
     */
    OneKilometre(length = 6),
    /**
     * Cell width x height: ≤ 153m × 153m
     */
    OneHundredFiftyMetres(length = 7),
    /**
     * Cell width x height: ≤ 38.2m × 19.1m
     */
    ThirtyMetres(length = 8),
    /**
     * Cell width x height: ≤ 4.77m × 4.77m
     */
    FiveMetres(length = 9),
    /**
     * Cell width x height: ≤ 1.19m × 0.596m
     */
    OneMetre(length = 10),
    /**
     * Cell width x height: ≤ 149mm × 149mm
     */
    OneDecimetre(length = 11),
    /**
     * Cell width x height: ≤ 37.2mm × 18.6mm
     */
    Pinpoint(length = Geohash.MAX_HASH_LENGTH),
}
