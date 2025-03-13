package android.yushenko.openweather.util

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.core.content.ContextCompat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class PermissionsComponent private constructor(
    private val builder: Initializer
) : PermissionsManager {

    private var context: Context = builder.context
    private val statePermission = MutableStateFlow<List<PermissionResult>>(emptyList())

//    val launcherMultiplePermissions = rememberLauncherForActivityResult(
//        ActivityResultContracts.RequestMultiplePermissions()
//    ) { permissionsMap ->
//        if (permissionsMap.isNotEmpty()) {
//            Log.i("DEB_TAG", "permissionsMap= $permissionsMap")
//            val areGranted = permissionsMap.values.reduce { acc, next -> acc && next }
//            if (areGranted) {
//                Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }

    override fun subscribe(vararg permissions: String): Flow<List<PermissionResult>> {
        return statePermission.map { listPermissionResult ->
            listPermissionResult.filter { permissionResult ->
                permissions.all { permission ->
                    permissionResult.permission == permission
                }
            }
        }
    }

    override fun request(vararg permissions: String): List<PermissionResult> {
//        checkAndRequestPermissions()
        return emptyList()
    }


    override fun navigateToOsAppSettings() {

    }

    private fun checkAndRequestPermissions(
        context: Context,
        permissions: Array<String>,
        launcher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>
    ) {
        if (
            permissions.all {
                ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
            }
        ) {

        } else {
            // Request permissions
            launcher.launch(permissions)
        }
    }

    class Initializer {
        lateinit var context: Context
            private set

        fun context(context: Context) = apply { this.context = context }

        fun prepare(): PermissionsManager {
            return PermissionsComponent(this)
        }
    }
}