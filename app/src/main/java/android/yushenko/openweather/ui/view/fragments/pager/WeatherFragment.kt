package android.yushenko.openweather.ui.view.fragments.pager

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.yushenko.openweather.R
import android.yushenko.openweather.ui.viewmodel.WeatherViewModel
import android.yushenko.openweather.data.model.search.Search
import android.yushenko.openweather.data.model.weather.WeatherOneCall
import android.yushenko.openweather.ui.adapters.recycler.DailyAdapter
import android.yushenko.openweather.ui.adapters.recycler.HourlyAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.weather_fragment.*
import java.text.SimpleDateFormat
import java.util.*

class WeatherFragment(private val search: Search) : Fragment(R.layout.weather_fragment) {

    private lateinit var mCityTextView: TextView
    private lateinit var mImageView: ImageView
    private lateinit var mDescTextView: TextView
    private lateinit var mTempTextView: TextView
    private lateinit var mInfoTitleTV: TextView
    private lateinit var mInfoDataTV: TextView
    private lateinit var mDateTV: TextView

    private lateinit var deleteClick: ImageView
    private lateinit var refreshClick: ImageView

    private lateinit var refreshLayout: SwipeRefreshLayout

    private lateinit var recyclerViewHourly: RecyclerView
    private lateinit var recyclerViewDaily: RecyclerView

    private val adapterDaily = DailyAdapter()
    private val adapterHourly = HourlyAdapter()

    private val viewModel: WeatherViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mCityTextView = view.findViewById(R.id.city_tv)
        mImageView = view.findViewById(R.id.view_image)
        mDescTextView = view.findViewById(R.id.description_tv)
        mTempTextView = view.findViewById(R.id.temp_tv)
        mInfoTitleTV = view.findViewById(R.id.info_title_tv)
        mInfoDataTV = view.findViewById(R.id.info_data_tv)
        mDateTV = view.findViewById(R.id.date_tv)

        deleteClick = view.findViewById(R.id.image_delete_click)
        deleteClick.setOnClickListener { viewModel.deleteItemsFirebase(search) }

        refreshClick = view.findViewById(R.id.image_refresh_click)
        refreshClick.setOnClickListener {
            refreshLayout.isRefreshing = true
            refreshLayout.drawingTime.and(1000)
            updateData()
        }

        refreshLayout = view.findViewById(R.id.swipe_refresh)
        refreshLayout.setOnRefreshListener {
            updateData()
        }

        setupAdapters(view)
    }

    override fun onStart() {
        super.onStart()
        setupObserving()
        viewModel.loadWeather(search)
    }

    private fun setupAdapters(view: View) {
        recyclerViewHourly = view.findViewById(R.id.rec_hourly_view)
        recyclerViewDaily = view.findViewById(R.id.rec_daily_view)

        recyclerViewHourly.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false)
        recyclerViewDaily.layoutManager = LinearLayoutManager(requireActivity())

        recyclerViewHourly.adapter = adapterHourly
        recyclerViewDaily.adapter = adapterDaily
    }

    private fun setupObserving() {
        viewModel.liveData.observe(requireActivity()) {
            adapterHourly.setData(it?.hourly.orEmpty().take(25))
            adapterDaily.setData(it?.daily.orEmpty())
            showWeatherData(it)
            refreshLayout.isRefreshing = false
        }
    }

    private fun updateData() {
        viewModel.loadWeather(search)
    }

    private fun showWeatherData(weather: WeatherOneCall) {
        Picasso.with(activity)
                .load("http://openweathermap.org/img/wn/" + weather.current!!.weather!![0].icon + "@2x.png")
                .resize(100, 100)
                .into(mImageView)

        mDateTV.text = getDate(weather.current!!.dt!!.toLong())
        mCityTextView.text = weather.nameLocale
        mDescTextView.text = weather.current!!.weather!![0].description
        mTempTextView.text = weather.current!!.temp!!.toInt().toString()
        tem_icon_unit_tv.text = resources.getText(R.string.text_unit_celsius)

        text_today.text = resources.getText(R.string.text_today)
        mInfoTitleTV.text = "Восход солнца\n"
        mInfoDataTV.text = getTime(weather.current?.sunrise!!.toLong()) + "\n"

        mInfoTitleTV.append("Заход солнца\n")
        mInfoDataTV.append(getTime(weather.current!!.sunset!!.toLong()) + "\n")

        mInfoTitleTV.append("Чувствуется как\n")
        mInfoDataTV.append("${weather.current?.feelsLike?.toInt()}°\n")

        mInfoTitleTV.append("Влажность\n")
        mInfoDataTV.append("${weather.current!!.humidity} %\n")

        mInfoTitleTV.append("Давление\n")
        mInfoDataTV.append("${weather.current!!.pressure} гПа\n")

        mInfoTitleTV.append("Облачность\n")
        mInfoDataTV.append("${weather.current!!.clouds} %\n")

        mInfoTitleTV.append("Видимость\n")
        mInfoDataTV.append("${weather.current!!.visibility!! / 1000} км\n")

        mInfoTitleTV.append("Уф-индекс\n")
        mInfoDataTV.append("${weather.current!!.uvi!!.toInt()}\n")

        mInfoTitleTV.append("Скорость ветра\n")
        mInfoDataTV.append("${weather.current!!.windSpeed} м/с\n")
        if (weather.current!!.windGust != null) {
            mInfoTitleTV.append("Порыв ветра\n")
            mInfoDataTV.append("${weather.current!!.windGust} м/с\n")
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