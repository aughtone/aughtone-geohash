---
skill-id: io.github.aughtone.geohash.geohash
spec-version: 1.0
type: "Aughtone AI-Skill"
scope: core
compatibility: ">=1.0.3"
author: "[Brill Pappin](https://github.com/bpappin)"
---

# AI Skill: Aughtone Geohash

This library provides geohash encoding, decoding, and neighbor calculations for Kotlin Multiplatform. It is designed as a spatial extension for `aughtone-types`.

## 🧰 The AI Toolbox (Key Functions)

### **Type Interop (Aughtone Types)**
- `Coordinates.toGeohash(length|accuracy): String`: Extension on `aughtone-types`' `Coordinates`.
    - **Usage**: `val hash = myCoords.toGeohash(Accuracy.About1m)`
- `Coordinates.toGeohashLong(length): Long`: Returns the 64-bit long representation.
- `coordinateOf(geohash: String|Long): Coordinates`: Decodes a hash to its center point.
    - **Usage**: `val center = coordinateOf("dr5reg")`
- `Coordinates.within(geohash: String): Boolean` (Infix)
    - **Usage**: `if (myCoords within "dr5reg") { ... }`

### **Encoding & Decoding**
- `stringGeohashOf(coordinate: Coordinates, length|accuracy): String`
- `longGeohashOf(coordinate: Coordinates, length): Long`
- `String.toAccuracy(accuracy: Accuracy): String`: Truncates a hash.
    - **Usage**: `"9q9hr5udfr".toAccuracy(Accuracy.About5km)` -> `"9q9hr"`

### **Spatial DSL**
Use the `geohash` function to access a rich DSL for spatial manipulation.
```kotlin
val result = geohash("dr5reg") { hash ->
    if (hash contains myCoords) {
        hash.northOf().eastOf()
    } else {
        hash adjacent Direction.TOP
    }
}
```
Available within `geohash { ... }` (works on `String` or `Long` input):
- `.northOf()`, `.southOf()`, `.eastOf()`, `.westOf()`: Neighbor navigation.
- `.adjacent(Direction)`: Navigation using `Direction` enum.
- `.contains(Coordinates)`: (Infix) Spatial containment check.
- `.neighboursOf()`: Returns `List<String>` of all 8 surrounding hashes.

## 📏 Accuracy & Length Guide

| Length | Accuracy Enum | Cell Size (Approx at Equator) |
| :--- | :--- | :--- |
| 1 | `About5000km` | 5000km × 5000km |
| 2 | `About1000km` | 1250km × 625km |
| 3 | `About150km` | 156km × 156km |
| 4 | `About30km` | 39.1km × 19.5km |
| 5 | `About5km` | 4.89km × 4.89km |
| 6 | `About1km` | 1.22km × 0.61km |
| 7 | `About150m` | 153m × 153m |
| 8 | `About30m` | 38.2m × 19.1m |
| 9 | `About5m` | 4.77m × 4.77m |
| 10 | `About1m` | 1.19m × 0.596m |
| 11 | `About1dm` | 149mm × 149mm |
| 12 | `About3cm` | 37.2mm × 18.6mm |

## 📜 Compliance & Standards

- **Encoding**: Standard 32-character Base32 (0-9, b-z excluding a, i, l, o).
- **CRS**: Uses **WGS84** coordinates via `aughtone-types`.
- **Implementation**: Ported from `davetroy/geohash-js` and `davidmoten/geo-hash-java`.
- **Borders**: Neighbor logic correctly handles poles and the Antimeridian (IDL).

## 🤖 Agent Onboarding
Include this section to instructions for an AI agent on how to bootstrap this library into a *new* workspace.

1. **Context Registration**: Add this skill file to the `AGENTS.md` of the consuming project.
2. **README Verification**: Ensure the root `README.md` contains the "AI-Assisted Development" section.
3. **Usage Rules**:
    - **DSL Priority**: Always prefer the `geohash { ... }` block for neighbor navigation as it handles edge cases (poles/IDL) automatically.
    - **Types Integration**: Always use the extension functions on `Coordinates` (from `aughtone-types`) for encoding to maintain type safety.
    - **Precision**: Choose the correct `Accuracy` enum value based on the business use case (e.g., `About1m` for POIs, `About5km` for city-level areas).
    - **Long Representation**: Use `Long` hashes for high-performance indexing/storage when possible, as they are more efficient than `String`s.
