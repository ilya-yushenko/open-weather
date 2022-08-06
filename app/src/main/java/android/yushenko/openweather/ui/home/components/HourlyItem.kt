package android.yushenko.openweather.ui.home.components

import android.yushenko.openweather.R
import android.yushenko.openweather.model.HourlyWeather
import android.yushenko.openweather.shared.formatToTime
import android.yushenko.openweather.ui.theme.WhiteGray
import android.yushenko.openweather.ui.theme.robotoFamily
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun HourlyItem(
    model: HourlyWeather
) {
    Column(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = WhiteGray,
                shape = RoundedCornerShape(28.dp)
            )
    ) {
        Text(
            text = stringResource(R.string.text_unit_celsius, model.temp),
            style = TextStyle(
                fontFamily = robotoFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            ),
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(top = 16.dp)
        )

        CoilImage(
            imageModel = model.iconUrl,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(56.dp)
                .padding(
                    vertical = 8.dp,
                    horizontal = 4.dp
                )
                .fillMaxSize()
        )
        Text(
            text = model.date.formatToTime(),
            style = TextStyle(
                fontFamily = robotoFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            ),
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(bottom = 16.dp)
        )
    }
}