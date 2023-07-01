package android.yushenko.openweather.ui.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.yushenko.openweather.R
import android.yushenko.openweather.ui.theme.LightBlue
import android.yushenko.openweather.ui.theme.LightOrange
import android.yushenko.openweather.ui.theme.WhiteGray
import android.yushenko.openweather.ui.theme.robotoFamily
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.*

@Preview
@Composable
fun LocationScreen(
    viewModel: LocationViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    val context = LocalContext.current

//    val permissions = arrayOf(
//        Manifest.permission.ACCESS_COARSE_LOCATION,
//        Manifest.permission.ACCESS_FINE_LOCATION
//    )
//
//    val launcherMultiplePermissions = rememberLauncherForActivityResult(
//        ActivityResultContracts.RequestMultiplePermissions()
//    ) { permissions ->
//        val areGranted = permissions.values.reduce { acc, next -> acc && next }
//        if (areGranted) {
//            viewModel.getLocation()
//        } else {
//
//        }
//    }

    val launcherMultiplePermissions = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap ->
        val areGranted = permissionsMap.values.reduce { acc, next -> acc && next }
        if (areGranted) {
            // Use location
        } else {
            // Show dialog
        }
    }

    SideEffect {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )



        checkAndRequestLocationPermissions(
            context = context,
            permissions = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            launcher = launcherMultiplePermissions
        )
    }

    val composition by rememberLottieComposition(
        if (state.status != LocationStatus.Ready)
            LottieCompositionSpec.RawRes(R.raw.location_animation)
        else
            LottieCompositionSpec.RawRes(R.raw.ready_animation)
    )

    val lottieAnimation by animateLottieCompositionAsState(
        composition = composition,
        iterations = if (state.status != LocationStatus.Ready)
            LottieConstants.IterateForever else 1
    )
//
//    if (state.status == LocationStatus.RequestPermission) {
//
//    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        LightBlue,
                        LightOrange,
                        LightBlue
                    ),
                )
            )
    ) {

        LottieAnimation(
            composition,
            lottieAnimation,
            modifier = Modifier
                .wrapContentWidth()
                .align(alignment = Alignment.Center)
                .size(250.dp)
        )


        Text(
            text = if (state.status == LocationStatus.Determinate)
                stringResource(R.string.location_screen_determine)
            else stringResource(R.string.location_screen_ready),
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontFamily = robotoFamily,
                fontWeight = if (state.status == LocationStatus.Determinate)
                    FontWeight.Normal
                else FontWeight.Medium,
                fontSize = 16.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .background(WhiteGray)
                .align(alignment = Alignment.BottomCenter)
                .padding(16.dp)
        )
    }
}


fun checkAndRequestLocationPermissions(
    context: Context,
    permissions: Array<String>,
    launcher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>
) {
    if (
        permissions.all {
            ContextCompat.checkSelfPermission(
                context,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    ) {
        // Use location because permissions are already granted
    } else {
        // Request permissions
        launcher.launch(permissions)
    }
}