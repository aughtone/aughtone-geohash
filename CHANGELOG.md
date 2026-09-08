# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/), and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).



## [1.0.5] - 2026-09-07

### Changed
- **Dependency Updates**:
    - Bumped `aughtone-types` to `3.4.0`.

### Removed
- **Embedded AI Skills**: The `META-INF/ai-skills/` resource is no longer packaged in the published artifact. Consumers that scanned dependencies for a skill file will not find one here.
- **Standards Documentation**: Removed the AI-skill and quality-engineering standards documents.

## [1.0.4] - 2026-06-28

### Added
- **Bounding Box Coverage**: Added bounding box coverage capabilities (`coverBoundingBox` and `coverBoundingBoxMaxHashes`) to the public API.

### Changed
- **Branding & Standardization**:
    - Renamed "AughtOne" to "Aughtone" across the project.
    - Unified iOS Kit naming to `AughtoneGeohashKit` and added missing `bundleId`.
    - Standardized `namespace` to `io.github.aughtone.geohash`.
- **Dependency Updates**:
    - Bumped `aughtone-types` to `3.1.0`.
    - Upgraded Kotlin to `2.4.0` and Android Gradle Plugin (AGP) to `9.2.1`.
    - Upgraded Maven Publish plugin to `0.37.0`.

### Fixed
- **Multiplatform Testing**: Overrode `toString()` in `Coverage` and `CoverageLongs` to enforce consistent double and array formatting across JVM and JS targets, resolving test failures on Kotlin/JS.
- **Coordinates Test**: Fixed a test compilation error in `CoordinatesTest` where `Coordinates.add` was incorrectly called as a function invocation.

## [1.0.3] - 2026-04-23

### Changed
- **Toolchain Upgrade**: Upgraded to **Android Gradle Plugin 9.1.1** and **Kotlin 2.3.20**.
- **Platform Alignment**: Updated **Android Compile SDK to 37** and transitioned to the `com.android.kotlin.multiplatform.library` plugin.
- **Dependency Refresh**: Updated core dependencies, including `io.github.aughtone:types:2.0.0`.

## [1.0.0] - 2026-04-23


### Added
- **Project Governance**: Initialized the 5-sector documentation hierarchy (`ARCH.md`, `SPEC.md`, `DEVELOPER.md`) and core standards for KMP development.
- **Agent Instructions**: Added `AGENTS.md` to guide AI contributors on repository structure and contribution conventions.

### Changed
- Stabilized dependencies and build configuration for the `1.0.0` stable release.
