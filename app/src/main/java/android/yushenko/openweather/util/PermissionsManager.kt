package android.yushenko.openweather.util

import androidx.core.content.PermissionChecker
import kotlinx.coroutines.flow.Flow

interface PermissionsManager {

    /**
     * Observe the state of permissions. You will get notified when there's new changes to it.
     */
    fun subscribe(vararg permissions: String): Flow<List<PermissionResult>>

    /**
     * Request a permission from the system. The Single will return the permissions result from the OS.
     */
    fun request(vararg permissions: String): List<PermissionResult>

    /**
     * Navigate to your app's settings. Here the user can manually change permissions.
     */
    fun navigateToOsAppSettings()
}