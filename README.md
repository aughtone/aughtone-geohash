[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)
![Maven Central Version](https://img.shields.io/maven-central/v/io.github.aughtone/geohash-multiplatform?style=flat)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.1.10-blue.svg?logo=kotlin&style=flat)](http://kotlinlang.org)
[![Kotlin Multiplatform](https://img.shields.io/badge/Kotlin-Multiplatform-brightgreen?logo=kotlin)](https://github.com/JetBrains/compose-multiplatform)


![badge-android](http://img.shields.io/badge/platform-android-6EDB8D.svg?style=flat)
![badge-ios](http://img.shields.io/badge/platform-ios-CDCDCD.svg?style=flat)
![badge-desktop](http://img.shields.io/badge/platform-desktop-DB413D.svg?style=flat)
![badge-js](http://img.shields.io/badge/platform-js%2Fwasm-FDD835.svg?style=flat)


# Geohash Multiplatform

This library for set up for [Kotlin Multiplatform](https://www.jetbrains.com/kotlin-multiplatform/) (KMP)

This is a pure Kotlin port of Dave Moten's [geo](https://github.com/davidmoten/geo) Java project.
I think Dave may have ported it to Java from Dave Troy's [geohash-js](https://github.com/davetroy/geohash-js) Javascript project.

Kotlin structures are not the same as Java, and this code was almost a decade old when I started 
this port, so quite a lot of refactoring will need to be done to make it all rainbows and sunshine 
for Kotlin developers.

Feel free to fork it and make improvements, I'll keep up as best I can.

# Features

Dave lists several features on his project page, but I'll just paraphrase the important one's here:

* Full Multiplatform Library. Its pur kotlin and should compile on any platform.
* encodes a geohashes from a latitude & longitude, with a length from 1 to 12
* decodes a latitude, longitude from a geohashes
* finds an adjacent hash in cardinal directions, and works on borders including the poles.
* can find all 8 adjacent hashes
* calculates hash length to enclose a bounding box
* calculates geohashes of given length to cover a bounding box, wih ratios.
* can calculate the height and width of a geohashe in degrees
* encodes & decodes Long values from geohashes

# Installation
![Maven Central Version](https://img.shields.io/maven-central/v/io.github.aughtone/geohash-multiplatform?style=flat)
```gradle
implementation("io.github.aughtone:geohash-multiplatform:${version}")
```

# Quick Start

You can use a Coordinate object, or a pair of Double values to generate a geohash from.
```kotlin
val location: Coordinate = Coordinate(latitude = 20.05, longitude = -15.5)
val geohash = location.toGeohash(4)
```
To generate a geohash with a maximum length: 
```kotlin
val geohash:String = stringGeohashOf(coordinate = location)
```

You can also specify the length of the geohash you want to generate:
```kotlin
val geohash:String = stringGeohashOf(coordinate = location, length = 6)

```
You can work with an encoded geohash. 
The lambda must return either the same geohash or a modified version:
```kotlin
val checkCoordinate: Coordinate = Coordinate(latitude = 20.05, longitude = -15.5)
val myGeohash = stringGeohashOf(latitude = 20.05, longitude = -15.5, length = 6)
val myOther = geohash(myGeohash) { geohash ->
            if(geohash contains checkCoordinate) {
                it.southOf()
            }else{
                geohash adjacent Direction.TOP
            }
        }

```
You can also access the geohash object directly. In fact, until I refactor this a little more, 
that will be the only way to access some of the functions:
```kotlin
val myGeohash = stringGeohashOf(latitude = 20.05, longitude = -15.5, length = 6)
val data : List<String> = Geohash.neighbours(myGeohash)
```

# Feedback

Bugs can go into the issue tracker, but you are probably going to get faster support by creating a PR.   
