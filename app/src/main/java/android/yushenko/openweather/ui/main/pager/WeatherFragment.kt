package android.yushenko.openweather.ui.main.pager

import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.yushenko.openweather.R
import android.yushenko.openweather.ext.observe
import android.yushenko.openweather.model.CurrentWeather
import android.yushenko.openweather.model.Location
import android.yushenko.openweather.shared.AppException
import android.yushenko.openweather.shared.ViewState
import android.yushenko.openweather.ui.main.pager.recycler.DailyAdapter
import android.yushenko.openweather.ui.main.pager.recycler.HourlyAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.idapgroup.lifecycle.ktx.observe
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.weather_fragment.*
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class WeatherFragment(private val model: Location) : Fragment(R.layout.weather_fragment) {

    private val adapterDaily = DailyAdapter()
    private val adapterHourly = HourlyAdapter()

    private val viewModel: WeatherViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        deleteClick.setOnClickListener {
            viewModel.deleteItemHistory(model)
        }

        refreshClick.setOnClickListener {
            swipeRefresh.isRefreshing = true
            swipeRefresh.drawingTime.and(1000)
            updateData()
        }

        swipeRefresh.setOnRefreshListener {
            updateData()
        }

        setupAdapters()
    }

    override fun onStart() {
        super.onStart()
        setupObserving()
        viewModel.loadWeather(model)
    }

    private fun setupAdapters() {
        recyclerHourly.adapter = adapterHourly
        recyclerDaily.adapter = adapterDaily
    }

    private fun setupObserving() {
        with(viewModel) {
            observe(viewState) {
                swipeRefresh.isRefreshing = it == ViewState.Loading
            }
            observe(currentWeather) {
                showWeatherData(it)
            }
            observe(hourlyWeather) {
                adapterHourly.setData(it)
            }
            observe(dailyWeather) {
                adapterDaily.setData(it)
            }
            observe(errorEvent) {
                val message = when (it) {
                    AppException.NoInternet -> resources.getString(R.string.no_internet)
                    is AppException.Id -> it.message
                        ?: resources.getString(R.string.has_error)
                    else -> resources.getString(R.string.has_error)
                }
                Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateData() {
        viewModel.loadWeather(model)
    }

    private fun showWeatherData(data: CurrentWeather) {
        Picasso.with(activity)
            .load(data.iconUrl)
            .resize(100, 100)
            .into(viewIcon)

        textDate.text = getDate(data.date)
        textLocalName.text = data.localeName
        textDescription.text = data.description
        tempView.text = "${data.temp}"
        textTemIconUnit.text = resources.getString(R.string.text_unit_celsius)

        textToday.text = resources.getText(R.string.text_today)
        infoTitle.text = "Восход солнца\n"
        infoData.text = getTime(data.sunrise) + "\n"

        infoTitle.append("Заход солнца\n")
        infoData.append(getTime(data.sunset) + "\n")

        infoTitle.append("Чувствуется как\n")
        infoData.append("${data.feelsLike}°\n")

        infoTitle.append("Влажность\n")
        infoData.append("${data.humidity} %\n")

        infoTitle.append("Давление\n")
        infoData.append("${data.pressure} гПа\n")

        infoTitle.append("Облачность\n")
        infoData.append("${data.clouds} %\n")

        infoTitle.append("Видимость\n")
        infoData.append("${data.visibility / 1000} км\n")

        infoTitle.append("Уф-индекс\n")
        infoData.append("${data.uvi}\n")

        infoTitle.append("Скорость ветра\n")
        infoData.append("${data.windSpeed} м/с\n")
        infoTitle.append("Порыв ветра\n")
        infoData.append("${data.windGust} м/с\n")
    }

    private fun getTime(seconds: Long): String? {
        val date = Date(seconds * 1000L)
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        return dateFormat.format(date)
    }

    private fun getDate(seconds: Long): String? {
        val date = Date(seconds * 1000L)
        val dateFormat = SimpleDateFormat("EEEE dd MMMM HH:mm", Locale.getDefault())
        return dateFormat.format(date)
    }
}