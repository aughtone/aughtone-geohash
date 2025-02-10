package io.github.aughtone.geohash

import kotlin.test.fail

public inline fun assertThrowsIllegalArgumentException(codeToTest: () -> Any?): Unit {
    try {
        codeToTest()
        fail("Should have thrown an IllegalArgumentException")
    } catch (e: IllegalArgumentException) {
        return
    } catch (e: Exception) {
        fail("Should have thrown an IllegalArgumentException instead of an exception of type ${e::class.simpleName}")
    }
}
