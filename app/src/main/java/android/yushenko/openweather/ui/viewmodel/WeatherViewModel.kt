package android.yushenko.openweather.ui.viewmodel

import android.app.Application
import android.yushenko.openweather.data.model.authentication.User
import android.yushenko.openweather.data.model.weather.WeatherOneCall
import android.yushenko.openweather.data.model.search.Search
import android.yushenko.openweather.data.repository.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    var liveData = MutableLiveData<WeatherOneCall>()
    var liveSearchData = MutableLiveData<List<Search>>()
    var liveHistorySearchData = MutableLiveData<List<Search>>()
    var liveIsFound = MutableLiveData<Boolean>()
    var liveCreateUser = MutableLiveData<Boolean>()
    var liveSignInUser = MutableLiveData<Boolean>()
    var liveEmailUser = MutableLiveData<String>()

    val weatherRepository = WeatherRepository()

    fun loadWeather(search: Search) {
        CoroutineScope(Dispatchers.IO).launch {
            liveData.postValue(weatherRepository.getWeatherData(search))
        }
    }


    fun loadSearch(name: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val searches = weatherRepository.getSearchData(name)
            if (searches != null) {
                searches.let { liveSearchData.postValue(it) }
                liveIsFound.postValue(true)
            } else {
                liveIsFound.postValue(false)
            }

        }
    }


//        weatherRepository.getWeatherRepository(search, object : OnWeatherRepositoryCallback {
//            override fun onDataReady(data: WeatherOneCall?) {
//                data?.let { liveData.value = it }
//            }
//        })
//    }


//    fun loadSearchRepositoryK(name: String) {
//        weatherRepository.getSearchRepository(name, object : OnSearchRepositoryCallback {
//            override fun onSearchDataReady(searches: List<Search>) {
//                liveSearchData.postValue(searches)
//            }
//
//            override fun onIsFound(isFound: Boolean) {
//                liveIsFound.value = isFound
//            }
//        })
//    }

    fun savePreference(search: Search) {
        weatherRepository.setSearchPreference(search)
    }

    fun createUser(user: User) {
        weatherRepository.createUser(user, object : OnCreateUserRepositoryCallback {
            override fun onCreatedOk(boolean: Boolean) {
                liveCreateUser.value = boolean
            }

        })
    }

    fun signInUser(user: User) {
        weatherRepository.signInUser(user, object : OnSignInUserRepositoryCallback {
            override fun onSignInOk(boolean: Boolean) {
                liveSignInUser.value = boolean
            }

        })
    }

    fun getEmailUser() {
        weatherRepository.getEmailUser(object : OnGetEmailUserRepositoryCallback {
            override fun onEmailUser(email: String) {
                liveEmailUser.value = email
            }

        })
    }

    fun deleteItemsFirebase(search: Search) {
        weatherRepository.deleteItemsDataBaseRepository(search)
    }

    fun loadHistorySearchLocales() {
        weatherRepository.getHistorySearches(object : OnHistorySearchesRepositoryCallback {
            override fun onHistorySearches(searches: List<Search>) {
                liveHistorySearchData.postValue(searches.asReversed())
            }

        })
    }

    fun signUserOut() {
        weatherRepository.signUserOut()
    }

    fun isUserSignIn(): Boolean {
        return weatherRepository.isUserSignIn()
    }
}