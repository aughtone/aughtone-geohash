package io.github.aughtone.geohash.support

import io.github.aughtone.geohash.Geohash


/**
 * The characters used for encoding base 32 strings.
 */
private val charactersBase32: CharArray = charArrayOf(
    '0', '1', '2', '3', '4', '5',
    '6', '7', '8', '9', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k',
    'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
)

/**
 * Used for lookup of index of characters in the above array.
 */
private val characterIndexesBase32: Map<Char, Int> =
    charactersBase32.mapIndexed { index: Int, c: Char -> c to index }.toMap()


/**
 * Returns the base 32 encoding of the given length from a [Long]
 * geohash.
 *
 * @param i
 * the geohash
 * @param length
 * the length of the returned hash
 * @return the string geohash
 */
internal fun encodeBase32(input: Long, length: Int): String {
    var num = input
    val isNegative = num < 0
    if (isNegative) {
        num = -num
    }

    val builder = StringBuilder()
    while (num >= 32) {
        builder.append(charactersBase32[(num % 32).toInt()])
        num /= 32
    }
    builder.append(charactersBase32[num.toInt()])

    val result = builder.reverse().toString()
    return if (isNegative) {
        "-$result".padStart(length + 1, '0')
    } else {
        result.padStart(length, '0')
    }
}

/**
 * Returns the base 32 encoding of length [Geohash.MAX_HASH_LENGTH]
 * from a [Long] geohash.
 *
 * @param i
 * the geohash
 * @return the base32 geohash
 */
internal fun encodeBase32(i: Long): String {
    return encodeBase32(i, Geohash.MAX_HASH_LENGTH)
}

/**
 * Returns the conversion of a base32 geohash to a long.
 *
 * @param hash
 * geohash as a string
 * @return long representation of hash
 */
internal fun decodeBase32(hash: String): Long {
    val isNegative = hash.startsWith("-")
    val startIndex = if (isNegative) 1 else 0
    var base: Long = 1
    var result: Long = 0
    for (i in hash.length - 1 downTo startIndex) {
        val j = charIndexOfBase32(hash[i])
        result += base * j
        base *= 32
    }
    if (isNegative) result *= -1
    return result
}

/**
 * Returns the index in the digits array of the character ch. Throws an
 * [IllegalArgumentException] if the character is not found in the
 * array.
 *
 * @param ch
 * character to obtain index for
 * @return index of ch character in characterIndexes map.
 */
// @VisibleForTesting
internal fun charIndexOfBase32(ch: Char): Int =
    requireNotNull(characterIndexesBase32[ch]) { "not a base32 character: $ch" }
