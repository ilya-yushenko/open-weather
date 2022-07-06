package android.yushenko.openweather.di

import android.yushenko.openweather.data.api.ApiHelper
import android.yushenko.openweather.data.repository.firebase.DataBaseFirebase
import android.yushenko.openweather.data.repository.firebase.FirebaseAuthentication
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideApi(): ApiHelper = ApiHelper

    @Singleton
    @Provides
    fun provideFirebaseInstance(): FirebaseAuth = FirebaseAuth.getInstance()
}