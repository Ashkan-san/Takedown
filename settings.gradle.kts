rootProject.name = "Takedown"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

include(":shared")
include(":composeApp")

// PULL TO REFRESH
/*
includeBuild("<androidx-compose-material3-pullrefresh-path>/library") {
    dependencySubstitution {
        substitute(module("me.omico.compose:compose-material3-pullrefresh")).using(project(":"))
    }
}*/
