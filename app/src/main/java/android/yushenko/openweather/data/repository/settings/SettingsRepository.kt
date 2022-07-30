package android.yushenko.openweather.data.repository.settings

import android.yushenko.openweather.data.model.response.settings.Units
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getLiveCurrentUnit(): Flow<Units>
    fun setCurrentUnit(unit: Units)
    fun fetchCurrentUnit()
    fun getCurrentUnit(): Units
}