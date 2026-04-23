---
skill-id: io.github.aughtone.geohash
name: "[AughtOne Geohash](https://github.com/aughtone/aughtone-geohash)"
type: "AughtOne AI-Skill"
scope: core
compatibility: ">=1.0.0"
author: "[Brill Pappin](https://github.com/bpappin)"
---

# AI Skill: Aughtone Geohash

This library provides geohash encoding, decoding, and neighbor calculations for Kotlin Multiplatform. It is designed as a spatial extension for `aughtone-types`.

## 🧰 The AI Toolbox (Key Functions)

### **Type Interop (Aughtone Types)**
- `Coordinates.toGeohash(length|accuracy): String`
    - **Usage**: `val hash = myCoords.toGeohash(Accuracy.About1m)`
- `coordinateOf(geohash: String): Coordinates`
    - **Usage**: `val center = coordinateOf("dr5reg")`
- `Coordinates.within(geohash: String): Boolean` (Infix)
    - **Usage**: `if (myCoords within "dr5reg") { ... }`

### **Encoding & Decoding**
- `stringGeohashOf(coordinate: Coordinates, length: Int = 12): String`
- `longGeohashOf(coordinate: Coordinates, length: Int = 12): Long`
- `String.toAccuracy(accuracy: Accuracy): String`
    - **Usage**: `"9q9hr5udfr".toAccuracy(Accuracy.About5km)` -> `"9q9hr"`

### **Spatial DSL**
Use the `geohash` function to access a rich DSL for spatial manipulation.
```kotlin
val result = geohash("dr5reg") { hash ->
    if (hash contains myCoords) {
        hash.northOf().eastOf()
    } else {
        hash adjacent Direction.BOTTOM
    }
}
```
Available within `geohash { ... }`:
- `.northOf()`, `.southOf()`, `.eastOf()`, `.westOf()`
- `.adjacent(Direction)`
- `.contains(Coordinates)`
- `.neighboursOf()`: Returns `List<String>` of all 8 surrounding hashes.

## 📏 Accuracy & Length Guide

| Length | Accuracy | Cell Size (Approx) |
| :--- | :--- | :--- |
| 1 | `About5000km` | 5000km × 5000km |
| 3 | `About150km` | 156km × 156km |
| 5 | `About5km` | 4.9km × 4.9km |
| 7 | `About150m` | 153m × 153m |
| 9 | `About5m` | 4.8m × 4.8m |
| 11 | `About1dm` | 149mm × 149mm |
| 12 | `About3cm` | 37mm × 18mm |

## 📜 Compliance & Standards

- **Encoding**: Standard 32-character Base32 (0-9, b-z excluding a, i, l, o).
- **CRS**: Uses **WGS84** coordinates via `aughtone-types`.

## 🤖 Agent Onboarding
1. **Context Registration**: Add this skill file to the `AGENTS.md` of the consuming project.
2. **README Verification**: Ensure the root `README.md` contains the "AI-Assisted Development" section.
3. **Usage Rules**:
    - **DSL Priority**: Always prefer the `geohash { ... }` block for neighbor navigation as it handles edge cases (poles/IDL) automatically.
    - **Types Integration**: Always use the extension functions on `Coordinates` (from `aughtone-types`) for encoding to maintain type safety.
    - **Precision**: Choose the correct `Accuracy` enum value based on the business use case (e.g., `About1m` for POIs, `About5km` for city-level areas).
