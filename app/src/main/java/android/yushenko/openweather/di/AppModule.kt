package android.yushenko.openweather.di

import android.content.Context
import android.content.SharedPreferences
import android.yushenko.openweather.data.factory.ApiServiceFactory
import android.yushenko.openweather.data.factory.ApiServiceFactoryImpl
import android.yushenko.openweather.data.repository.db.DataBaseFirebaseRepository
import android.yushenko.openweather.data.repository.db.DataBaseFirebaseRepositoryImpl
import android.yushenko.openweather.data.repository.oauth.OauthFirebaseRepository
import android.yushenko.openweather.data.repository.oauth.OauthFirebaseRepositoryImpl
import android.yushenko.openweather.data.repository.settings.SettingsRepository
import android.yushenko.openweather.data.repository.settings.SettingsRepositoryImpl
import android.yushenko.openweather.data.repository.weather.WeatherApiRepository
import android.yushenko.openweather.shared.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @[Singleton Provides]
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences = context.getSharedPreferences(
        Constants.Preferences, Context.MODE_PRIVATE
    )

    @[Singleton Provides]
    fun provideSharedPreferencesEditor(
        sharedPreferences: SharedPreferences
    ): SharedPreferences.Editor = sharedPreferences.edit()

    @[Singleton Provides]
    fun provideGson(): Gson = GsonBuilder().create()

    @[Singleton Provides]
    fun apiServicesFactory(): ApiServiceFactory = ApiServiceFactoryImpl()

    @[Singleton Provides]
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @[Singleton Provides]
    fun provideFirebaseDbReference(): DatabaseReference = Firebase.database.reference

    @[Singleton Provides]
    fun provideWeatherApiRepository(
        factory: ApiServiceFactory
    ): WeatherApiRepository = factory.apiService()

    @[Singleton Provides]
    fun provideFirebaseDbRepository(
        firebaseAuth: FirebaseAuth,
        remoteDb: DatabaseReference
    ): DataBaseFirebaseRepository = DataBaseFirebaseRepositoryImpl(
        firebaseAuth = firebaseAuth,
        remoteDb = remoteDb
    )

    @[Singleton Provides]
    fun provideOauthFirebaseRepository(
        firebaseAuth: FirebaseAuth,
    ): OauthFirebaseRepository = OauthFirebaseRepositoryImpl(
        firebaseAuth = firebaseAuth,
    )

    @[Singleton Provides]
    fun provideSettingsRepository(
        sharedPreferences: SharedPreferences,
        editor: SharedPreferences.Editor,
        gson: Gson
    ): SettingsRepository = SettingsRepositoryImpl(
        sharedPreferences = sharedPreferences,
        editor = editor,
        gson = gson,
    )

}