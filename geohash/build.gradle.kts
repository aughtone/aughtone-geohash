import com.vanniktech.maven.publish.SonatypeHost
import org.gradle.kotlin.dsl.assign
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompileCommon

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.vanniktech.mavenPublish)
}

group = "io.github.aughtone"
version = libs.versions.versionName.get().toString()

kotlin {
    jvm()
    androidTarget {
        publishLibraryVariants("release")
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
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
            baseName = "GeohashKit"
            isStatic = true
//            binaryOption(
//                "bundleId",
//                libs.versions.applicationId.get().toString()
//            ) //"app.occurrence"
//            binaryOption(
//                "bundleShortVersionString",
//                libs.versions.versionName.get().toString()
//            ) //"1.0.0"
//            binaryOption("bundleVersion", libs.versions.versionCode.get().toString()) //"1"
        }
    }
//    linuxX64()

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
        // moduleName = "io.github.aughtone.types"
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

android {
    namespace = libs.versions.applicationId.get().toString()
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    if (!project.hasProperty("skip-signing")) {
        signAllPublications()
    }

    coordinates(group.toString(), "geohash", version.toString())

    pom {
        name = "Geohash Multiplatform Library"
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
