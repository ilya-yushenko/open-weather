import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.core() {
    add("implementation", "androidx.core:core-ktx:${versions.core}")
    add("implementation", "androidx.appcompat:appcompat:${versions.appcompat}")
}

fun DependencyHandler.design() {
    add("implementation", "androidx.constraintlayout:constraintlayout:${versions.constraint}")
    add("implementation", "com.google.android.material:material:${versions.material}")
    add("implementation", "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
}

fun DependencyHandler.hilt() {
    add("implementation", "com.google.dagger:hilt-android:${versions.hilt}")
    add("kapt", "com.google.dagger:hilt-android-compiler:${versions.hilt}")
    add("kapt", "androidx.hilt:hilt-compiler:1.0.0-alpha01")
}

fun DependencyHandler.coroutines() {
    add("implementation", "org.jetbrains.kotlinx:kotlinx-coroutines-android:${versions.coroutines}")
}

fun DependencyHandler.picasso() {
    add("implementation", "com.squareup.picasso:picasso:${versions.picasso}")
}

fun DependencyHandler.fragment() {
    add("implementation", "androidx.fragment:fragment-ktx:${versions.fragment}")
}

fun DependencyHandler.retrofit() {
    add("implementation", "com.squareup.retrofit2:retrofit:${versions.retrofit}")
    add("implementation", "com.squareup.retrofit2:converter-gson:${versions.retrofit}")
}

fun DependencyHandler.okhttp() {
    add("implementation", "com.squareup.okhttp3:logging-interceptor:${versions.okhttp}")
}

fun DependencyHandler.lifecycle() {
    add("implementation", "androidx.lifecycle:lifecycle-extensions:${versions.lifecycleExt}")
    add(
        "implementation",
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${versions.lifecycleViewModel}"
    )
    add("implementation", "com.idapgroup:lifecycle-ktx:${versions.idapLifecycle}")
}

fun DependencyHandler.firebase() {
    add("implementation", "com.google.firebase:firebase-bom:${versions.firebaseBom}")
    add("implementation", "com.google.firebase:firebase-auth-ktx:${versions.firebaseAuth}")
    add("implementation", "com.google.firebase:firebase-database-ktx:${versions.firebaseDb}")
    add(
        "implementation",
        "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${versions.playServices}"
    )
    add("implementation", "com.google.firebase:firebase-firestore-ktx:${versions.firestore}")
}

fun DependencyHandler.navigation() {
    add("implementation", "androidx.navigation:navigation-fragment-ktx:${versions.navigation}")
    add("implementation", "androidx.navigation:navigation-ui-ktx:${versions.navigation}")
    add(
        "implementation",
        "androidx.navigation:navigation-dynamic-features-fragment:${versions.navigation}"
    )
}

fun DependencyHandler.test() {
    add("testImplementation", "junit:junit:${versions.junit}")
    add("androidTestImplementation", "androidx.test.ext:junit:${versions.extJunit}")
    add("androidTestImplementation", "androidx.test.espresso:espresso-core:${versions.espresso}")
}
