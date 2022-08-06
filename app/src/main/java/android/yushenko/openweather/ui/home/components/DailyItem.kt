package android.yushenko.openweather.ui.home.components

import android.yushenko.openweather.R
import android.yushenko.openweather.model.DailyWeather
import android.yushenko.openweather.shared.formatToDay
import android.yushenko.openweather.ui.theme.robotoFamily
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun DailyItem(
    model: DailyWeather
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 36.dp)
    ) {
        Text(
            text = model.date.formatToDay(),
            style = TextStyle(
                fontFamily = robotoFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            ),
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
        Text(
            text = stringResource(R.string.text_unit_celsius, model.tempMax),
            style = TextStyle(
                fontFamily = robotoFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            ),
            modifier = Modifier
                .weight(0.5f)
                .align(Alignment.CenterVertically)
        )
        Text(
            text = stringResource(R.string.text_unit_celsius, model.tempMin),
            style = TextStyle(
                fontFamily = robotoFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            ),
            modifier = Modifier
                .weight(0.5f)
                .align(Alignment.CenterVertically)
        )
        CoilImage(
            imageModel = model.iconUrl,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(56.dp)
                .fillMaxSize()
                .align(Alignment.CenterVertically)
        )
    }
}