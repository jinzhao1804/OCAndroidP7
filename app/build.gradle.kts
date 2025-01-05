plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.openclassrooms.arista"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.openclassrooms.arista"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        // Flag to enable support for the new language APIs
        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = JavaVersion.VERSION_19
        targetCompatibility = JavaVersion.VERSION_19
    }

    kotlinOptions {
        jvmTarget = "19"
    }

    buildFeatures {
        viewBinding = true
    }

}

dependencies {
    // Core KTX and Desugaring
    implementation("androidx.core:core-ktx:1.15.0") // Core extensions
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.1.4") // Core library desugaring

    // Room dependencies
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")

    // Hilt for Dependency Injection
    implementation("com.google.dagger:hilt-android:2.49") // Align Hilt version
    kapt("com.google.dagger:hilt-compiler:2.49") // Align Hilt version

    // Android libraries
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")
    implementation("androidx.activity:activity-ktx:1.9.3")
    implementation("androidx.fragment:fragment-ktx:1.8.5")

    // Tests
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")


    // Mockito for mocking objects
    testImplementation("org.mockito:mockito-core:4.2.0")

    testImplementation("io.mockk:mockk:1.13.4")

    // Mockito-Kotlin for easier mocking with Kotlin
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")

    // Kotlin Coroutines test dependencies for suspending functions
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    // AndroidX Test for JUnit integration
    testImplementation("androidx.test.ext:junit:1.2.1")

    // For mocking suspending functions (inline mocking)
    testImplementation("org.mockito:mockito-inline:4.2.0")

    // Optional: Use this for tests that need the `runBlocking` coroutine builder to work seamlessly.
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.9.0")

}

kapt {
    correctErrorTypes = true
    javacOptions {
        option("--add-exports", "jdk.compiler/com.sun.tools.javac.main=ALL-UNNAMED")
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "19"
            freeCompilerArgs = freeCompilerArgs + listOf(
                "-Xadd-exports=jdk.compiler/com.sun.tools.javac.main=ALL-UNNAMED"
            )
        }
    }

}

