package android.yushenko.openweather.data

import android.content.Context
import android.yushenko.openweather.data.model.firebase.*
import android.yushenko.openweather.data.preferences.AppPreferences
import android.yushenko.openweather.model.authentication.User
import android.yushenko.openweather.model.weather.WeatherOneCall
import android.yushenko.openweather.model.search.Search

class WeatherRepository(context: Context) {
    private val appPreferences = AppPreferences(context)
    private val remoteDataSource = WeatherRemoteDataSource()
    private val searchDataSource = SearchRemoteSource()
    private val firebaseAuthentication = FirebaseAuthentication()
    private val dataBaseFirebase = DataBaseFirebase()


    fun getWeatherRepository(search: Search, onWeatherRepositoryCallback: OnWeatherRepositoryCallback) {
        remoteDataSource.getWeatherData(search, object : OnWeatherRemoteReadyCallback {
            override fun onRemoteDataReady(data: WeatherOneCall?) {
                onWeatherRepositoryCallback.onDataReady(data)
            }
        })
    }

    fun getSearchRepository(name: String, onSearchRepositoryCallback: OnSearchRepositoryCallback) {
        searchDataSource.setData(name)
        searchDataSource.getSearchData(object : OnSearchRemoteReadyCallback {
            override fun onSearchRemoteDataReady(list: List<Search>) {
                onSearchRepositoryCallback.onSearchDataReady(list)
            }

            override fun onIsFound(boolean: Boolean) {
                onSearchRepositoryCallback.onIsFound(boolean)
            }

        })
    }

    fun setSearchPreference(search: Search) {
        dataBaseFirebase.writeDataBase(search)
    }


    fun createUser(user: User, onRepositoryFirebaseCreateUser: OnCreateUserRepositoryCallback) {
        firebaseAuthentication.createUser(user, object : OnFirebaseCreatedUser {
            override fun onCreatedUserOk(boolean: Boolean) {
                dataBaseFirebase.userAddData(user)
                onRepositoryFirebaseCreateUser.onCreatedOk(boolean)
            }

        })
    }

    fun signInUser(user: User, onRepositoryFirebaseSignInUser: OnSignInUserRepositoryCallback) {
        firebaseAuthentication.signIn(user, object : OnFirebaseSignInUser {
            override fun onSignInUserOk(boolean: Boolean) {
                onRepositoryFirebaseSignInUser.onSignInOk(boolean)
            }
        } )
    }

    fun getEmailUser(onGetEmailUserRepositoryCallback: OnGetEmailUserRepositoryCallback) {
        dataBaseFirebase.getEmailUser(object : OnEmailUserFirebaseCallback {
            override fun onGetEmailUserFirebase(email: String) {
                onGetEmailUserRepositoryCallback.onEmailUser(email)
            }
        })
    }

    fun signUserOut() {
        firebaseAuthentication.signOut()
    }

    fun isUserSignIn(): Boolean {
        return firebaseAuthentication.isSignIn()
    }

    fun deleteItemsDataBaseRepository(search: Search) {
        dataBaseFirebase.deleteItemsDataBase(search)
    }

    fun getHistorySearches(onRepositoryHistoryFirebaseCallback: OnHistorySearchesRepositoryCallback) {
        dataBaseFirebase.getListLocalsDataBase(object : OnListLocalsFirebaseCallback {
            override fun onGetListLocalsFirebase(searches: List<Search>) {
                onRepositoryHistoryFirebaseCallback.onHistorySearches(searches)
            }
        })
    }
}

interface OnWeatherRepositoryCallback {
    fun onDataReady(data: WeatherOneCall?)
}

interface OnSearchRepositoryCallback {
    fun onSearchDataReady(searches: List<Search>)
    fun onIsFound(isFound: Boolean)
}

interface OnCreateUserRepositoryCallback {
    fun onCreatedOk(boolean: Boolean)
}

interface OnSignInUserRepositoryCallback {
    fun onSignInOk(boolean: Boolean)
}

interface OnHistorySearchesRepositoryCallback {
    fun onHistorySearches(searches: List<Search>)
}

interface OnGetEmailUserRepositoryCallback {
    fun onEmailUser(email: String)
}