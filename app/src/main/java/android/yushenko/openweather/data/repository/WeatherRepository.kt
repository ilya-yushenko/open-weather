package android.yushenko.openweather.data.repository

import android.yushenko.openweather.data.repository.openweather.SearchRemoteSource
import android.yushenko.openweather.data.repository.openweather.WeatherRemoteDataSource
import android.yushenko.openweather.data.model.authentication.User
import android.yushenko.openweather.data.model.search.Search
import android.yushenko.openweather.data.repository.firebase.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(
        private val weatherData: WeatherRemoteDataSource,
        private val searchData: SearchRemoteSource,
        private val authentication: FirebaseAuthentication,
        private val database: DataBaseFirebase
) {

    fun getChannelRegisterOk() = authentication.chanelRegister

    fun getChannelLoginOk() = authentication.chanelLogin

    fun getLiveHistory() = database.listLiveData

    fun getHistorySearches() = database.loadHistory()

    fun getWeatherData(search: Search) = weatherData.getWeatherData(search)

    fun getSearchData(name: String) = searchData.getSearchData(name)

    fun addItemHistory(search: Search) = database.addItemHistory(search)

    fun createUser(user: User) = authentication.createUser(user)

    fun signInUser(user: User) = authentication.signIn(user)

    fun deleteItemHistory(search: Search) = database.deleteItemHistory(search)

    fun getUserData() = database.getUserData()

    fun signUserOut() = authentication.signOut()

    fun isUserSignIn() = authentication.isSignIn()
}