# Project Development Guide (AGENTS.md)

This document is the master instruction set for AI agents contributing to the **AOGeohash** repository.

## 1. Documentation Governance
This repository follows the **5-sector hierarchy**. All knowledge must be dispersed into:
- 📐 Architecture (`docs/ARCH.md`): System design and spatial theory.
- 🧠 Functional Specifications (`docs/SPEC.md`): API definitions and contracts.
- 🎨 Design & UI (`docs/DESIGN.md`): (If applicable) Visualization and formatting rules.
- 📋 Acceptance Criteria (`docs/ACs/README.md`): Per-feature validation rules.
- 📖 Developer Guide (`docs/DEVELOPER.md`): Local setup and contribution workflow.
- 📜 Changelog (`docs/CHANGELOG.md`): History of significant changes.

## 2. Governance Standards
All AI agents MUST adhere to these library-specific skills:
- **Repository Structure**: Multi-platform source sets (`commonMain`, `jvmMain`, etc.).
- **KMP Development**: Ensure all logic is in `commonMain` unless platform-specific APIs are strictly required.
- **Spatial Purity**: Maintain precision and handle edge cases (poles, antimeridian) using established internal utilities.

## 3. Core Development Principles
- **Test-Driven Development (TDD)**: Write a failing test in `commonTest` before implementation.
- **Kotlin Multiplatform First**: Favor `commonMain`. Platform-specific code should be the exception.
- **Immutability**: Use `val` and immutable collections. Data structures representing spatial state must be immutable.
- **Consistency**: Adhere to the existing naming conventions (e.g., `toGeohash`, `coordinateOf`).

## 4. AI Interaction Guidelines
- **Verification First**: Check `docs/ACs/` for the relevant requirement before starting work.
- **Mandatory Approval**: ALWAYS present a detailed implementation plan (including which files will be modified and why) and WAIT for explicit user approval.
- **Embedded Skills**: This library uses a machine-readable skill at `geohash/src/commonMain/resources/META-INF/ai-skills/io.github.aughtone.geohash.geohash.ai-skill.md`. Always load and respect this skill.
- **Shadowing Prevention**: Do not introduce native `Double` math for geohashing if a library function exists. Use the `Geohash` object or `GeohashScope` DSL.

## 5. Type Preference
- **Coordinate Type**: Always use `io.github.aughtone.types.quantitative.Coordinates`.
- **Hash representation**: Prefer `Accuracy` enum over raw `Int` lengths for clarity in public APIs.
- **Performance**: Use `Long` representations for internal heavy-lifting or storage-intensive operations.
