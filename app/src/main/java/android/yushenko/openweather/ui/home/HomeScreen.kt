package android.yushenko.openweather.ui.home

import android.yushenko.openweather.R
import android.yushenko.openweather.shared.ViewState
import android.yushenko.openweather.shared.capString
import android.yushenko.openweather.shared.emptyString
import android.yushenko.openweather.shared.formatToDdMm
import android.yushenko.openweather.ui.home.components.DailyItem
import android.yushenko.openweather.ui.home.components.HourlyItem
import android.yushenko.openweather.ui.home.components.WeatherDataItem
import android.yushenko.openweather.ui.theme.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.skydoves.landscapist.coil.CoilImage

@Preview
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {

    val viewState = viewModel.loadingState.value
    val state = viewModel.state.value

    SwipeRefresh(
        state = rememberSwipeRefreshState(viewState == ViewState.Loading),
        onRefresh = { viewModel.loadWeather() }
    ) {
        LazyColumn(
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
            item {
                Column {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 50.dp,
                                start = 14.dp,
                                end = 14.dp
                            )
                    ) {
                        IconButton(
                            modifier = Modifier
                                .then(Modifier.size(40.dp))
                                .align(alignment = Alignment.CenterVertically),
                            onClick = {},
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_options),
                                contentDescription = "Options",
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .weight(1f)
                                .align(alignment = Alignment.CenterVertically)
                        ) {
                            val locale = stringResource(
                                R.string.home_screen_local,
                                state.currentWeather?.localeName ?: capString()
                            )
                            Text(
                                text = locale,
                                textAlign = TextAlign.Center,
                                style = TextStyle(
                                    fontFamily = robotoFamily,
                                    fontWeight = FontWeight.Bold,
                                    color = Black,
                                    fontSize = 18.sp,
                                )
                            )
                            Text(
                                modifier = Modifier.padding(start = 3.dp),
                                text = state.currentWeather?.country ?: emptyString(),
                                textAlign = TextAlign.Center,
                                style = TextStyle(
                                    fontFamily = robotoFamily,
                                    fontWeight = FontWeight.Normal,
                                    color = Black,
                                    fontSize = 18.sp,
                                )
                            )
                        }
                        IconButton(
                            modifier = Modifier
                                .then(Modifier.size(40.dp))
                                .align(alignment = Alignment.CenterVertically),
                            onClick = {},
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_add_new),
                                contentDescription = "Add",
                            )
                        }
                    }

                    val date = stringResource(
                        R.string.home_screen_today_date,
                        System.currentTimeMillis().formatToDdMm()
                    )
                    Text(
                        text = date,
                        style = TextStyle(
                            fontFamily = robotoFamily,
                            fontWeight = FontWeight.Medium,
                            color = LightGray,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 3.dp)
                    )
                    CoilImage(
                        imageModel = state.currentWeather?.iconUrl ?: emptyString(),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(top = 32.dp)
                            .size(102.dp)
                            .fillMaxSize()
                            .align(alignment = Alignment.CenterHorizontally),
                    )
                    Text(
                        text = state.currentWeather?.let {
                            stringResource(R.string.text_unit_celsius, it.temp)
                        } ?: capString(),
                        style = TextStyle(
                            color = Black,
                            fontFamily = robotoFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 56.sp
                        ),
                        modifier = Modifier
                            .wrapContentHeight()
                            .align(alignment = Alignment.CenterHorizontally)
                    )
                    Text(
                        text = state.currentWeather?.description?.capitalize() ?: capString(),
                        style = TextStyle(
                            color = LightGray,
                            fontFamily = robotoFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp
                        ),
                        modifier = Modifier
                            .wrapContentHeight()
                            .padding(top = 12.dp)
                            .align(alignment = Alignment.CenterHorizontally)
                    )
                    Row(
                        modifier = Modifier
                            .padding(
                                top = 45.dp,
                                start = 24.dp,
                                end = 24.dp
                            )
                    ) {
                        WeatherDataItem(
                            iconId = R.drawable.ic_wind,
                            title = stringResource(R.string.home_screen_wind),
                            value = state.currentWeather?.let {
                                stringResource(R.string.text_unit_ms, it.windSpeed)
                            } ?: capString(),
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 4.dp)
                        )
                        WeatherDataItem(
                            iconId = R.drawable.ic_index_uv,
                            title = stringResource(R.string.home_screen_index_uv),
                            value = (state.currentWeather?.uvi ?: "- -").toString(),
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 4.dp)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(
                                top = 8.dp,
                                start = 24.dp,
                                end = 24.dp
                            )
                    ) {
                        WeatherDataItem(
                            iconId = R.drawable.ic_feel_like,
                            title = stringResource(R.string.home_screen_feel_like),
                            value = state.currentWeather?.let {
                                stringResource(R.string.text_unit_celsius, it.feelsLike)
                            } ?: capString(),
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 4.dp)
                        )
                        WeatherDataItem(
                            iconId = R.drawable.ic_humidity,
                            title = stringResource(R.string.home_screen_humidity),
                            value = state.currentWeather?.let {
                                stringResource(R.string.text_unit_percent, it.humidity)
                            } ?: capString(),
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 4.dp)
                        )
                    }
                    val today = if (state.currentWeather != null)
                        stringResource(R.string.home_screen_today) else emptyString()
                    Text(
                        text = today,
                        style = TextStyle(
                            fontFamily = robotoFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp)
                            .padding(horizontal = 24.dp)
                    )
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        itemsIndexed(state.hourlyWeather) { index, model ->
                            if (index == 0) {
                                Spacer(modifier = Modifier.width(24.dp))
                            }
                            HourlyItem(model)
                            if (index < state.hourlyWeather.lastIndex) {
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                            if (index == state.hourlyWeather.lastIndex) {
                                Spacer(modifier = Modifier.width(24.dp))
                            }
                        }
                    }

                    val anotherDays =
                        if (state.currentWeather != null) stringResource(R.string.home_screen_another_days)
                        else emptyString()
                    Text(
                        text = anotherDays,
                        style = TextStyle(
                            fontFamily = robotoFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp, bottom = 16.dp)
                            .padding(horizontal = 24.dp)
                    )
                }
            }

            itemsIndexed(state.dailyWeather) { index, model ->
                DailyItem(model)
                if (index < state.dailyWeather.lastIndex) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                            .height(1.dp)
                            .background(color = WhiteGray)
                    )
                }
            }
            item {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
            }
        }
    }

}