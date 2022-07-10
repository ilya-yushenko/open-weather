plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("com.google.gms.google-services")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdkVersion(versions.targetApi)

    defaultConfig {
        applicationId = "android.yushenko.openweather"
        minSdkVersion(versions.minApi)
        targetSdkVersion(versions.targetApi)
        versionCode(versions.versionCode)
        versionName(versions.versionName)
    }

    aaptOptions.cruncherEnabled = false

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    lintOptions {
        isAbortOnError = false
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    kotlin()
    core()
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
