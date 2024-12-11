// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    // Apply the Kotlin plugin for Android with the desired version
    kotlin("android") version "1.9.0" apply false // Correct Kotlin version for all modules

    // Android application plugin, version 8.5.2, apply false because it will be applied in the app module
    id("com.android.application") version "8.5.2" apply false

    // Dagger Hilt plugin for dependency injection
    id("com.google.dagger.hilt.android") version "2.49" apply false // Align Hilt plugin version
}