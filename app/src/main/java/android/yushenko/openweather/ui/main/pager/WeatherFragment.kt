package android.yushenko.openweather.ui.main.pager

import android.widget.Toast
import android.yushenko.openweather.R
import android.yushenko.openweather.databinding.WeatherFragmentBinding
import android.yushenko.openweather.ext.observe
import android.yushenko.openweather.model.CurrentWeather
import android.yushenko.openweather.model.Location
import android.yushenko.openweather.shared.*
import android.yushenko.openweather.ui.main.pager.recycler.DailyAdapter
import android.yushenko.openweather.ui.main.pager.recycler.HourlyAdapter
import androidx.fragment.app.viewModels
import com.idapgroup.lifecycle.ktx.observe
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherFragment(private val model: Location) : BaseFragment<WeatherFragmentBinding>(
    WeatherFragmentBinding::inflate
) {

    private val adapterDaily = DailyAdapter()
    private val adapterHourly = HourlyAdapter()

    private val viewModel: WeatherViewModel by viewModels()

    override fun WeatherFragmentBinding.onInitViews() {
        recyclerHourly.adapter = adapterHourly
        recyclerDaily.adapter = adapterDaily
        viewModel.loadWeather(model)
    }

    override fun WeatherFragmentBinding.onInitListener() {
        deleteClick.setOnClickListener {
            viewModel.deleteItemHistory(model)
        }
        refreshClick.setOnClickListener {
            swipeRefresh.isRefreshing = true
            swipeRefresh.drawingTime.and(1000)
            updateData()
        }
        swipeRefresh.setOnRefreshListener(::updateData)
    }

    override fun WeatherFragmentBinding.onInitObserving() {
        with(viewModel) {
            observe(viewState) {
                swipeRefresh.isRefreshing = it == ViewState.Loading
            }
            observe(currentWeather, ::setData)
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

    private fun setData(data: CurrentWeather) {
        with(binding) {
            Picasso.with(activity)
                .load(data.iconUrl)
                .resize(100, 100)
                .into(viewIcon)

            textDate.text = data.date.formatToDate()
            textLocalName.text = data.localeName
            textDescription.text = data.description
            tempView.text = "${data.temp}"
            textTemIconUnit.text = resources.getString(R.string.unit_celsius)

            textToday.text = resources.getText(R.string.text_today)
            infoTitle.text = "Восход солнца\n"
            infoData.text = "${data.sunrise.formatToTime()}\n"

            infoTitle.append("Заход солнца\n")
            infoData.append("${data.sunset.formatToTime()}\n")

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
    }
}