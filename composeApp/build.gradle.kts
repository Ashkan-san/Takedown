plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.realm)
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

kotlin {
    // Hier die verschiedenen Targets
    // JVM Target
    jvm()

    // Android Target
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    // IOS Target
    val buildIos = false

    if (buildIos) {
        listOf(
            iosX64(),
            iosArm64(),
            iosSimulatorArm64(),
        ).forEach { iosTarget ->
            iosTarget.binaries.framework {
                baseName = "composeApp"
                isStatic = true
                binaryOption("bundleId", "de.takedown.app")
            }
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)

            implementation(compose.components.uiToolingPreview)
            implementation(compose.components.resources)

            implementation(libs.precompose)
            implementation(libs.kmm.viewmodel)
            implementation(libs.realm.base)
            implementation(libs.realm.sync)
            implementation(libs.kotlin.datetime)

            implementation(libs.coil)
            implementation(libs.coil.compose)
            implementation(libs.coil.ktor)
            implementation(libs.coil.svg)

            implementation(libs.koin.core)
            implementation(libs.koin.compose)
        }

        androidMain.dependencies {
            implementation(libs.compose.ui)
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.play.services.maps)
            implementation(libs.maps.compose)
            implementation(libs.ktor.android)
            implementation(libs.koin.android)
        }
        iosMain.dependencies {
        }
    }

    task("testClasses")
}

// Hier Android Gradle anpassen
android {
    namespace = "de.takedown.app"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "de.takedown.app"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    sourceSets {
        named("main") {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            res.srcDirs("src/androidMain/res")
            resources.srcDir("src/commonMain/composeResources")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        // https://developer.android.com/jetpack/androidx/releases/compose-kotlin
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/versions/**"
        }
    }
    buildTypes {
        getByName("release") {
            // RELEASE BUILD ZUM TESTEN
            isMinifyEnabled = false
            isShrinkResources = false

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
        create("customDebugType") {
            isDebuggable = true
        }
    }
}

dependencies {
    debugImplementation(libs.androidx.ui.tooling)
}

// Unnötige Annotations entfernen
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        kotlin.sourceSets.all {
            languageSettings.optIn("androidx.compose.material3.ExperimentalMaterial3Api")
            languageSettings.optIn("androidx.compose.foundation.ExperimentalFoundationApi")
            languageSettings.optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
            languageSettings.optIn("kotlinx.cinterop.ExperimentalForeignApi")
            languageSettings.optIn("androidx.compose.ui.ExperimentalComposeUiApi")
        }
    }
}

tasks.withType(Tar::class.java) {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
tasks.withType(Zip::class.java) {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
