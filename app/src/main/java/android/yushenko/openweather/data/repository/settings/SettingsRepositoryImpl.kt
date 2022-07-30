package android.yushenko.openweather.data.repository.settings

import android.content.SharedPreferences
import android.yushenko.openweather.data.model.response.settings.Units
import android.yushenko.openweather.shared.Constants
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class SettingsRepositoryImpl constructor(
    private val sharedPreferences: SharedPreferences,
    private val editor: SharedPreferences.Editor,
    private val gson: Gson
) : SettingsRepository {

    private val _currentUnit = MutableStateFlow(Units.Metric)

    override fun getLiveCurrentUnit(): Flow<Units> = _currentUnit

    override fun setCurrentUnit(unit: Units) {
        val json = gson.toJson(unit)
        editor.putString(Constants.CurrentUnit, json).apply()
        _currentUnit.value = unit
    }

    override fun fetchCurrentUnit() {
        val json = sharedPreferences.getString(Constants.CurrentUnit, "")
        if (!json.isNullOrEmpty()) {
            gson.fromJson(json, Units::class.java)
        } else {
            Units.Metric
        }.let {
            _currentUnit.value = it
        }
    }

    override fun getCurrentUnit(): Units {
        val json = sharedPreferences.getString(Constants.CurrentUnit, "")
        if (!json.isNullOrEmpty()) {
            gson.fromJson(json, Units::class.java)
        } else {
            Units.Metric
        }.let {
            return it
        }
    }
}