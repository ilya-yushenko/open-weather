package android.yushenko.openweather.di

import android.yushenko.openweather.data.api.ApiServiceFactory
import android.yushenko.openweather.data.api.ApiServiceFactoryImpl
import android.yushenko.openweather.data.repository.db.DataBaseFirebaseRepository
import android.yushenko.openweather.data.repository.db.DataBaseFirebaseRepositoryImpl
import android.yushenko.openweather.data.repository.oauth.OauthFirebaseRepository
import android.yushenko.openweather.data.repository.oauth.OauthFirebaseRepositoryImpl
import android.yushenko.openweather.data.repository.weather.WeatherApiRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun apiServicesFactory(): ApiServiceFactory = ApiServiceFactoryImpl()

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun provideFirebaseDbReference(): DatabaseReference = Firebase.database.reference

    @Singleton
    @Provides
    fun provideWeatherApiRepository(
        factory: ApiServiceFactory
    ): WeatherApiRepository = factory.apiService()

    @Singleton
    @Provides
    fun provideFirebaseDbRepository(
        firebaseAuth: FirebaseAuth,
        remoteDb: DatabaseReference
    ): DataBaseFirebaseRepository = DataBaseFirebaseRepositoryImpl(
        firebaseAuth = firebaseAuth,
        remoteDb = remoteDb
    )

    @Singleton
    @Provides
    fun provideOauthFirebaseRepository(
        firebaseAuth: FirebaseAuth,
    ): OauthFirebaseRepository = OauthFirebaseRepositoryImpl(
        firebaseAuth = firebaseAuth,
    )
}