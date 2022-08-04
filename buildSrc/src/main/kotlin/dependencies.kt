import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.core() {
    add("implementation", "androidx.core:core-ktx:${versions.core}")
    add("implementation", "androidx.appcompat:appcompat:${versions.appcompat}")
}

fun DependencyHandler.compose() {
    add("implementation", "androidx.compose.ui:ui:${versions.compose}")
    add("implementation", "androidx.compose.material:material:${versions.compose}")
    add("implementation", "androidx.compose.ui:ui-tooling-preview:${versions.compose}")
    add("implementation", "androidx.activity:activity-compose:1.5.1")
    add("implementation", "androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    add("implementation", "androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")
    add("implementation", "androidx.navigation:navigation-compose:2.5.1")
    add("implementation", "androidx.compose.material:material-icons-extended:1.2.0")
    add("implementation", "androidx.hilt:hilt-navigation-compose:1.0.0")
    add("implementation", "com.github.skydoves:landscapist-coil:1.6.0")
    add("implementation", "com.google.accompanist:accompanist-swiperefresh:0.26.0-alpha")
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
    add("implementation", "org.jetbrains.kotlinx:kotlinx-coroutines-core:${versions.coroutines}")
    add("implementation", "org.jetbrains.kotlinx:kotlinx-coroutines-android:${versions.coroutines}")
}

fun DependencyHandler.picasso() {
    add("implementation", "com.squareup.picasso:picasso:2.5.2")
}

fun DependencyHandler.fragment() {
    add("implementation", "androidx.fragment:fragment-ktx:${versions.fragment}")
}

fun DependencyHandler.retrofit() {
    add("implementation", "com.squareup.retrofit2:retrofit:${versions.retrofit}")
    add("implementation", "com.squareup.retrofit2:converter-gson:${versions.retrofit}")
}

fun DependencyHandler.okhttp() {
    add("implementation", "com.squareup.okhttp3:okhttp:${versions.okhttp}")
    add("implementation", "com.squareup.okhttp3:logging-interceptor:${versions.okhttp}")
}

fun DependencyHandler.lifecycle() {
    add("implementation", "androidx.lifecycle:lifecycle-extensions:${versions.lifecycle}")
    add("implementation", "androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    add("implementation", "com.idapgroup:lifecycle-ktx:1.0.3")
}

fun DependencyHandler.firebase() {
    add("implementation", "com.google.firebase:firebase-bom:${versions.firebase}")
    add("implementation", "com.google.firebase:firebase-auth-ktx:21.0.6")
    add("implementation", "com.google.firebase:firebase-database-ktx:19.7.0")
    add("implementation", "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.3.5")
    add("implementation", "com.google.firebase:firebase-firestore-ktx:22.1.2")
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
    add("testImplementation", "junit:junit:4.12")
    add("androidTestImplementation", "androidx.test.ext:junit:1.1.2")
    add("androidTestImplementation", "androidx.test.espresso:espresso-core:3.3.0")

    add("debugImplementation", "androidx.compose.ui:ui-tooling:${versions.compose}")
    add("androidTestImplementation", "androidx.compose.ui:ui-test-junit4:${versions.compose}")
}
