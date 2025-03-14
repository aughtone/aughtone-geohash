package io.github.aughtone.geohash


/**
 * Truncates the `geohash` to a specified accuracy level.
 *
 * This function takes a `geohash` and an [Accuracy] enum value, and returns a new string that
 * contains only the accuracy length up to the length defined by the [Accuracy] value.
 * If the original `geohash` is shorter than the specified accuracy length, the original
 * `geohash` is returned, and no reduction in accuracy occurs.
 *
 * @param accuracy The desired accuracy level, which determines the length of the resulting `geohash`.
 * @return A new `geohash` truncated to the specified accuracy.
 * @sample
 *  val geohash = "9q9hr5udfr"
 *  println(geohash.toAccuracy(Accuracy.About150km)) // Output: 9q9
 *  println(geohash.toAccuracy(Accuracy.About5km)) // Output: 9q9hr
 *  println(geohash.toAccuracy(Accuracy.About5m)) // Output: 9q9hr5udf
 *  println("9q9hr5ud".toAccuracy(Accuracy.About3cm)) //Output: 9q9hr5ud, which is About30m, the same as the original geohash.
 */
fun String.toAccuracy(accuracy: Accuracy) = take(accuracy.length)
