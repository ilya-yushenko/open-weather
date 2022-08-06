package android.yushenko.openweather.ui.home.components

import android.yushenko.openweather.ui.theme.Black
import android.yushenko.openweather.ui.theme.LightGray
import android.yushenko.openweather.ui.theme.WhiteGray
import android.yushenko.openweather.ui.theme.robotoFamily
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WeatherDataItem(
    @DrawableRes iconId: Int,
    title: String,
    value: String,
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp))
            .background(color = WhiteGray)
    ) {
        Row(
            modifier = Modifier
                .align(alignment = Alignment.Center)
        ) {
            Icon(
                painter = painterResource(iconId),
                contentDescription = null,
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
            )
            Column(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .padding(vertical = 24.dp)
            ) {
                Text(
                    text = title,
                    style = TextStyle(
                        color = LightGray,
                        fontFamily = robotoFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    )
                )
                Text(
                    text = value,
                    style = TextStyle(
                        color = Black,
                        fontFamily = robotoFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                )
            }
        }
    }
}