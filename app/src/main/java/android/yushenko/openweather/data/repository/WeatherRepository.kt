package android.yushenko.openweather.data.repository

import android.yushenko.openweather.data.repository.openweather.SearchRemoteSource
import android.yushenko.openweather.data.repository.openweather.WeatherRemoteDataSource
import android.yushenko.openweather.data.model.firebase.*
import android.yushenko.openweather.data.model.authentication.User
import android.yushenko.openweather.data.model.search.Search

class WeatherRepository {
    private val remoteDataSource = WeatherRemoteDataSource()
    private val searchDataSource = SearchRemoteSource()
    private val firebaseAuthentication = FirebaseAuthentication()
    private val dataBaseFirebase = DataBaseFirebase()

    suspend fun getWeatherData(search: Search) = remoteDataSource.getWeatherData(search)

    suspend fun getSearchData(name: String) = searchDataSource.getSearchData(name)


    fun setSearchPreference(search: Search) {
        dataBaseFirebase.writeDataBase(search)
    }


    fun createUser(user: User, onRepositoryFirebaseCreateUser: OnCreateUserRepositoryCallback) {
        firebaseAuthentication.createUser(user, object : OnFirebaseCreatedUser {
            override fun onCreatedUserOk(boolean: Boolean) {
                if (boolean) {
                    dataBaseFirebase.userAddData(user)
                    firebaseAuthentication.signOut()
                }
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