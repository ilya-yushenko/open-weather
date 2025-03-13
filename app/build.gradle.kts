plugins {
    id("com.android.application")
    id("kotlin-android")
    id("com.google.gms.google-services")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
}

android {
    namespace = "android.yushenko.openweather"
    compileSdk = versions.targetApi

    defaultConfig {
        applicationId = "android.yushenko.openweather"
        minSdk = versions.minApi
        targetSdk = versions.targetApi
        versionCode = versions.versionCode
        versionName = versions.versionName
    }

    buildTypes {
        named("debug") {
            isDebuggable = true
        }
        named("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    buildFeatures {
        compose = true
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = versions.compose
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    api(files("../libs/idapgroup-lifecycle-ktx-1.0.3.aar"))


    implementation("com.google.android.gms:play-services-location:20.0.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")
    core()
    compose()
    design()
    coroutines()
    hilt()
    picasso()
    fragment()
    retrofit()
    okhttp()
    lifecycle()
    firebase()
    navigation()
    test()
}
