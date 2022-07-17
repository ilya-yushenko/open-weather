package android.yushenko.openweather.data.repository.db

import android.yushenko.openweather.data.model.authentication.UserInitial
import android.yushenko.openweather.data.model.entity.LocationEntity
import kotlinx.coroutines.flow.Flow

interface DataBaseFirebaseRepository {
    fun getSavedListLive(): Flow<List<LocationEntity>>
    fun fetchSavedList()
    suspend fun getUserData(): UserInitial?
    fun userAddData(user: UserInitial)
    fun addItemHistory(search: LocationEntity)
    fun deleteItemHistory(uuid: String)
}