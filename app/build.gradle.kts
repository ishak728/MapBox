plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("androidx.navigation.safeargs.kotlin")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    //id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.ishak.myapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ishak.myapplication"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"


    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }


    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    implementation("com.mapbox.navigation:android:2.19.0")
    implementation("com.mapbox.navigation:ui-dropin:2.9.1")

    implementation ("com.google.android.material:material:1.12.0")

    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))
    implementation("com.google.firebase:firebase-analytics")
    // Add the dependency for the Firebase Authentication library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-auth")
    // Declare the dependency for the Cloud Firestore library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-firestore")


    // glide
    implementation ("com.github.bumptech.glide:glide:4.16.0")


    //*navigation dependency

    val nav_version = "2.7.7"
    // Kotlin
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")
    // Testing Navigation
    androidTestImplementation("androidx.navigation:navigation-testing:$nav_version")


//*hilt
    /*implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-android-compiler:2.44")*/

    implementation("com.google.dagger:hilt-android:2.48.1")
    kapt("com.google.dagger:hilt-android-compiler:2.48.1")
    kapt("androidx.hilt:hilt-compiler:1.0.0")
    implementation("androidx.hilt:hilt-navigation-fragment:1.0.0")
    //implementation ("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0")


    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")

    val lifecycle_version = "2.7.0"
    val arch_version = "2.2.0"


    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    // Lifecycles only (without ViewModel or LiveData)
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version")

    // Saved state module for ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycle_version")

    // alternately - if using Java8, use the following instead of lifecycle-compiler
    implementation("androidx.lifecycle:lifecycle-common-java8:$lifecycle_version")

    // optional - Test helpers for LiveData
    testImplementation("androidx.arch.core:core-testing:$arch_version")

    // optional - Test helpers for Lifecycle runtime
    testImplementation ("androidx.lifecycle:lifecycle-runtime-testing:$lifecycle_version")




    //For each AndroidX Test package you want to use, add its package name to the dependencies section.
    // For example, to add the espresso-core package, add the following lines:
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")


    // Core library
    androidTestImplementation("androidx.test:core:3.5.1")

    // AndroidJUnitRunner and JUnit Rules
    androidTestImplementation("androidx.test:runner:3.5.1")
    androidTestImplementation("androidx.test:rules:3.5.1")

    // Assertions
    androidTestImplementation("androidx.test.ext:junit:3.5.1")
    androidTestImplementation("androidx.test.ext:truth:3.5.1")

    // Espresso dependencies
    androidTestImplementation( "androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation( "androidx.test.espresso:espresso-contrib:3.5.1")
    androidTestImplementation( "androidx.test.espresso:espresso-intents:3.5.1")
    androidTestImplementation( "androidx.test.espresso:espresso-accessibility:3.5.1")
    androidTestImplementation( "androidx.test.espresso:espresso-web:3.5.1")
    androidTestImplementation( "androidx.test.espresso.idling:idling-concurrent:3.5.1")

    // The following Espresso dependency can be either "implementation",
    // or "androidTestImplementation", depending on whether you want the
    // dependency to appear on your APK"s compile classpath or the test APK
    // classpath.
    androidTestImplementation( "androidx.test.espresso:espresso-idling-resource:3.5.1")

    //gson
    implementation ("com.google.code.gson:gson:2.10.1")

    //retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")

    // GSON

    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")



}


// Allow references to generated code
kapt {
    correctErrorTypes = true
}