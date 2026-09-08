import org.jetbrains.kotlin.gradle.tasks.KotlinCompileCommon

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.multiplatformLibrary)
    alias(libs.plugins.vanniktech.mavenPublish)
}

group = libs.versions.groupId.get().toString()
version = libs.versions.versionName.get().toString()

kotlin {
    jvmToolchain(17)
    jvm()
    android {
        namespace = libs.versions.namespace.get().toString()
        compileSdk {
            version = release(libs.versions.android.compileSdk.get().toInt())
        }
    }
    // See: https://kotlinlang.org/docs/js-project-setup.html
    js(IR) {
        browser {
            generateTypeScriptDefinitions()
        }
        useEsModules() // Enables ES2015 modules
        // binaries.executable()
    }
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "AughtoneGeohashKit"
            isStatic = true
            binaryOption(
                "bundleId",
                libs.versions.namespace.get().toString()
            )
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                //put your multiplatform dependencies here
                api(libs.aughtone.types)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }

    compilerOptions {
        // XXX Activate when this is resolved:
        //  https://youtrack.jetbrains.com/issue/KT-57847/Move-common-for-all-the-backends-module-name-compiler-option-to-the-KotlinCommonCompilerOptions
        //    namespace = libs.versions.namespace.get().toString()
    }
    // XXX Remove whent he above is resolved. This is a workaround.
    //  https://youtrack.jetbrains.com/issue/KT-66568/w-KLIB-resolver-The-same-uniquename...-found-in-more-than-one-library

    metadata {
        compilations.all {
            val compilationName = rootProject.name
            compileTaskProvider.configure {
                if (this is KotlinCompileCommon) {
                    moduleName = "${project.group}:${project.name}_$compilationName"
                }
            }
        }
    }

}

mavenPublishing {
    publishToMavenCentral(automaticRelease = true)

    if (!project.hasProperty("skip-signing")) {
        signAllPublications()
    }

    coordinates(group.toString(), "geohash", version.toString())

    pom {
        name = "Aughtone Geohash"
        description = "A library."
        inceptionYear = "2025"
        url = "https://github.com/aughtone/aughtone-geohash"
        licenses {
            license {
                name = "The Apache License, Version 2.0"
                url = "https://www.apache.org/licenses/LICENSE-2.0"
                distribution = "https://www.apache.org/licenses/LICENSE-2.0.txt"
            }
        }
        developers {
            developer {
                id = "bpappin"
                name = "Brill pappin"
                url = "https://github.com/bpappin"
            }
        }
        scm {
            url = "https://github.com/aughtone/aughtone-geohash"
            connection = "https://github.com/aughtone/aughtone-geohash.git"
            developerConnection = "git@github.com:aughtone/aughtone-geohash.git"
        }
    }
}
