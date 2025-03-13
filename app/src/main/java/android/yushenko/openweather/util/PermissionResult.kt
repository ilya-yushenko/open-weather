package android.yushenko.openweather.util

data class PermissionResult(
    val permission: String,
    val result: Result,
    val hasAskedForPermissions: Boolean,
    val isMarkedAsDontAsk: Boolean = false
)

enum class Result {
    GRANTED, // PermissionChecker.PERMISSION_GRANTED
    DENIED, // PermissionChecker.PERMISSION_DENIED
    DENIED_APP_OP // PermissionChecker.PERMISSION_DENIED_APP_OP
}