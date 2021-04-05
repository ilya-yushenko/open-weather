package android.yushenko.openweather.data.preferences

import android.content.Context
import android.yushenko.openweather.data.model.firebase.DataBaseFirebase
import android.yushenko.openweather.model.search.Search

class AppPreferences(context: Context) {

    private val settings = context.getSharedPreferences(Preferences.APP_PREFERENCES, Context.MODE_PRIVATE)
    private val search = Search()


    fun getSearchPreference(): Search {
        search.name = settings.getString(Preferences.APP_PREFERENCES_NAME, "Киев")
        search.lat = settings.getFloat(Preferences.APP_PREFERENCES_LAT, 50.4333f)
        search.lon = settings.getFloat(Preferences.APP_PREFERENCES_LON, 30.5167f)
        return search
    }

    fun setSearchPreference(search: Search) {
        settings.edit().putString(Preferences.APP_PREFERENCES_NAME, search.localNames?.ru).apply()
        settings.edit().putFloat(Preferences.APP_PREFERENCES_LAT, search.lat).apply()
        settings.edit().putFloat(Preferences.APP_PREFERENCES_LON, search.lon).apply()
    }
}