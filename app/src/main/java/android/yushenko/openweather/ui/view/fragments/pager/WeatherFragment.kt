package android.yushenko.openweather.ui.view.fragments.pager

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import android.yushenko.openweather.R
import android.yushenko.openweather.ui.viewmodel.WeatherViewModel
import android.yushenko.openweather.data.model.search.Search
import android.yushenko.openweather.data.model.weather.WeatherOneCall
import android.yushenko.openweather.ui.adapters.recycler.DailyAdapter
import android.yushenko.openweather.ui.adapters.recycler.HourlyAdapter
import android.yushenko.openweather.ui.shared.BaseViewModel
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.idapgroup.lifecycle.ktx.observe
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.weather_fragment.*
import java.text.SimpleDateFormat
import java.util.*

class WeatherFragment(private val search: Search) : Fragment(R.layout.weather_fragment) {

    private val adapterDaily = DailyAdapter()
    private val adapterHourly = HourlyAdapter()

    private val viewModel: WeatherViewModel by viewModels()
    private val eventViewModel: BaseViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        deleteClick.setOnClickListener { viewModel.deleteItemHistory(search) }

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
        viewModel.loadWeather(search)
    }

    private fun setupAdapters() {
        recyclerHourly.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false)
        recyclerDaily.layoutManager = LinearLayoutManager(requireActivity())

        recyclerHourly.adapter = adapterHourly
        recyclerDaily.adapter = adapterDaily
    }

    private fun setupObserving() {
        observe(viewModel.liveWeatherData, {
            adapterHourly.setData(it?.hourly.orEmpty().take(25))
            adapterDaily.setData(it?.daily.orEmpty())
            showWeatherData(it)
            swipeRefresh.isRefreshing = false
        })

        observe(eventViewModel.errorEvent, {
            Log.i("TAG", "Fragment: $it")
            Toast.makeText(requireActivity(), "Warning! $it", Toast.LENGTH_SHORT).show()
        })

    }

    private fun updateData() {
        viewModel.loadWeather(search)
    }

    private fun showWeatherData(weather: WeatherOneCall) {
        Picasso.with(activity)
                .load("http://openweathermap.org/img/wn/" + weather.current!!.weather!![0].icon + "@2x.png")
                .resize(100, 100)
                .into(viewIcon)

        textDate.text = getDate(weather.current!!.dt!!.toLong())
        textLocalName.text = weather.nameLocale
        textDescription.text = weather.current!!.weather!![0].description
        textTemp.text = weather.current!!.temp!!.toInt().toString()
        textTemIconUnit.text = resources!!.getString(R.string.text_unit_celsius)

        textToday.text = resources.getText(R.string.text_today)
        infoTitle.text = "Восход солнца\n"
        infoData.text = getTime(weather.current?.sunrise!!.toLong()) + "\n"

        infoTitle.append("Заход солнца\n")
        infoData.append(getTime(weather.current!!.sunset!!.toLong()) + "\n")

        infoTitle.append("Чувствуется как\n")
        infoData.append("${weather.current?.feelsLike?.toInt()}°\n")

        infoTitle.append("Влажность\n")
        infoData.append("${weather.current!!.humidity} %\n")

        infoTitle.append("Давление\n")
        infoData.append("${weather.current!!.pressure} гПа\n")

        infoTitle.append("Облачность\n")
        infoData.append("${weather.current!!.clouds} %\n")

        infoTitle.append("Видимость\n")
        infoData.append("${weather.current!!.visibility!! / 1000} км\n")

        infoTitle.append("Уф-индекс\n")
        infoData.append("${weather.current!!.uvi!!.toInt()}\n")

        infoTitle.append("Скорость ветра\n")
        infoData.append("${weather.current!!.windSpeed} м/с\n")
        if (weather.current!!.windGust != null) {
            infoTitle.append("Порыв ветра\n")
            infoData.append("${weather.current!!.windGust} м/с\n")
        }
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