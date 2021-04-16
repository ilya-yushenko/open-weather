package android.yushenko.openweather.di

import android.yushenko.openweather.data.api.ApiHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideRepository() : ApiHelper = ApiHelper
}