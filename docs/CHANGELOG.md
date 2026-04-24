# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/), and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).


## [1.0.4] - 2026-04-24

### Changed
- **Branding & Standardization**:
    - Renamed "AughtOne" to "Aughtone" across the project.
    - Unified iOS Kit naming to `AughtoneGeohashKit` and added missing `bundleId`.
    - Standardized `namespace` to `io.github.aughtone.geohash`.
- **Dependency Updates**: Bumped `aughtone-types` to `2.0.3`.

## [1.0.3] - 2026-04-23

### Changed
- **Toolchain Upgrade**: Upgraded to **Android Gradle Plugin 9.1.1** and **Kotlin 2.3.20**.
- **Platform Alignment**: Updated **Android Compile SDK to 37** and transitioned to the `com.android.kotlin.multiplatform.library` plugin.
- **Dependency Refresh**: Updated core dependencies, including `io.github.aughtone:types:2.0.0`.

## [1.0.0] - 2026-04-23


### Added
- **AI-Skill Integration**: Published the `io.github.aughtone.geohash.ai-skill.md` machine-readable skill to expose Geohash capabilities to AI agents.
- **Project Governance**: Initialized the 5-sector documentation hierarchy (`ARCH.md`, `SPEC.md`, `DEVELOPER.md`) and core standards for KMP development.
- **Agent Instructions**: Added `AGENTS.md` to guide AI contributors on repository structure and quality engineering protocols.

### Changed
- Stabilized dependencies and build configuration for the `1.0.0` stable release.
