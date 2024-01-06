import org.jetbrains.compose.ExperimentalComposeLibrary

val precomposeVersion = "1.5.7"
val htmlUnitVersion = "3.7.0"
val kmmViewModelVersion = "1.0.0-ALPHA-15"
val realmVersion = "1.13.0"

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("io.realm.kotlin") version "1.13.0"
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            //implementation(compose.preview)
            implementation(compose.material3)
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)
            implementation(compose.materialIconsExtended)

            // PRECOMPOSE NAVIGATION
            implementation("moe.tlaster:precompose:$precomposeVersion")

            // HTMLUNIT
            implementation("org.htmlunit:htmlunit3-android:$htmlUnitVersion")

            // VIEWMODEL
            //implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
            implementation("com.rickclephas.kmm:kmm-viewmodel-core:$kmmViewModelVersion")

            // MAPS
            implementation("com.google.maps.android:maps-compose:4.3.0")
            implementation("com.google.android.gms:play-services-maps:18.2.0")

            // REBUGGER
            implementation("io.github.theapache64:rebugger:1.0.0-rc02")

            // REALM DB
            implementation("io.realm.kotlin:library-base:$realmVersion")
            implementation("io.realm.kotlin:library-sync:$realmVersion")
        }

        androidMain.dependencies {
            implementation(libs.compose.ui)
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
        }
    }
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
    sourceSets["main"].apply {
        manifest.srcFile("src/androidMain/AndroidManifest.xml")
        // mehrere Resource Directories
        res.srcDirs("src/androidMain/res")
        resources.srcDirs("src/commonMain/resources")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            // RELEASE BUILD ZUM TESTEN
            isMinifyEnabled = true
            isShrinkResources = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        create("customDebugType") {
            isDebuggable = true
        }
    }
}

// Unnötige Annotations entfernen
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        kotlin.sourceSets.all {
            languageSettings.optIn("androidx.compose.material3.ExperimentalMaterial3Api")
            languageSettings.optIn("androidx.compose.foundation.ExperimentalFoundationApi")
            languageSettings.optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
            languageSettings.optIn("kotlinx.cinterop.ExperimentalForeignApi")
        }
    }
}
 