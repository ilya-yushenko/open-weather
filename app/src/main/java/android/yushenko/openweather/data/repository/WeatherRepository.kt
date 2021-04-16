package android.yushenko.openweather.data.repository

import android.yushenko.openweather.data.repository.openweather.SearchRemoteSource
import android.yushenko.openweather.data.repository.openweather.WeatherRemoteDataSource
import android.yushenko.openweather.data.model.authentication.User
import android.yushenko.openweather.data.model.search.Search
import android.yushenko.openweather.data.repository.firebase.*
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherData: WeatherRemoteDataSource,
    private val searchData: SearchRemoteSource) {

    private val authentication = FirebaseAuthentication()
    private val database = DataBaseFirebase()

    suspend fun getWeatherData(search: Search) = weatherData.getWeatherData(search)

    suspend fun getSearchData(name: String) = searchData.getSearchData(name)

    suspend fun getHistorySearches() = database.getListLocals()

    fun addItemHistory(search: Search) = database.addItemHistory(search)

    suspend fun createUser(user: User) = authentication.createUser(user)

    suspend fun signInUser(user: User) = authentication.signIn(user)

    fun deleteItemHistory(search: Search) = database.deleteItemHistory(search)

    fun getEmailUser() = authentication.getEmailUser()

    fun signUserOut() = authentication.signOut()

    fun isUserSignIn() = authentication.isSignIn()
}