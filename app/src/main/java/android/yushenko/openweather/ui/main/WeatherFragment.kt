package android.yushenko.openweather.ui.main

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.yushenko.openweather.R
import android.yushenko.openweather.data.viewmodel.WeatherViewModel
import android.yushenko.openweather.ui.search.SearchFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class WeatherFragment : Fragment(R.layout.weather_fragment) {

    private lateinit var mCityTextView: TextView
    private lateinit var mImageView: ImageView
    private lateinit var mDescTextView: TextView
    private lateinit var mTempTextView: TextView
    private lateinit var mInfoTitleTV: TextView
    private lateinit var mInfoDataTV: TextView
    private lateinit var mDateTV: TextView

    private lateinit var mRefreshLayout: SwipeRefreshLayout

    private lateinit var recyclerViewHourly: RecyclerView
    private lateinit var recyclerViewDaily: RecyclerView

    private val adapterDaily = DailyAdapter()
    private val adapterHourly = HourlyAdapter()

    private val viewModel: WeatherViewModel by viewModels()

    companion object {
        fun newInstance(): WeatherFragment {
            return WeatherFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mCityTextView = view.findViewById(R.id.city_tv)
        mImageView = view.findViewById(R.id.view_image)
        mDescTextView = view.findViewById(R.id.description_tv)
        mTempTextView = view.findViewById(R.id.temp_tv)

        mInfoTitleTV = view.findViewById(R.id.info_title_tv)
        mInfoDataTV = view.findViewById(R.id.info_data_tv)
        mDateTV = view.findViewById(R.id.date_tv)

        mCityTextView.setOnClickListener {
            val fm = requireActivity().supportFragmentManager
            fm.beginTransaction().replace(R.id.fragment_container,
                    SearchFragment.newInstance()).commit()
        }

        mRefreshLayout = view.findViewById(R.id.swipe_refresh)
        mRefreshLayout.setOnRefreshListener { update() }

        requestWeatherOneCall()
        setupAdapters(view)
    }


    override fun onResume() {
        super.onResume()
        update()
    }

    private fun update() {
        mRefreshLayout.isRefreshing =
                !viewModel.loadWeatherRepositories()
    }

    private fun setupAdapters(view: View) {
        recyclerViewHourly = view.findViewById(R.id.rec_hourly_view)
        recyclerViewDaily = view.findViewById(R.id.rec_daily_view)

        recyclerViewHourly.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false)
        recyclerViewDaily.layoutManager = LinearLayoutManager(requireActivity())

        recyclerViewHourly.adapter = adapterHourly
        recyclerViewDaily.adapter = adapterDaily

        setupHourlyObserving()
        setupDailyObserving()
    }

    private fun setupHourlyObserving() {
        viewModel.liveData.observe(requireActivity(), {
            adapterHourly.setData(it?.hourly.orEmpty().take(25))
        })
    }

    private fun setupDailyObserving() {
        viewModel.liveData.observe(requireActivity(), {
            it.daily?.let { it1 -> adapterDaily.setData(it1) }
        })
    }

    private fun requestWeatherOneCall() {
        viewModel.liveData.observe(requireActivity(), {
            val weather = it

            Picasso.with(activity)
                    .load("http://openweathermap.org/img/wn/" + weather.current!!.weather!![0].icon + "@2x.png")
                    .resize(100, 100)
                    .into(mImageView)

            mDateTV.text = getDate(weather.current!!.dt!!.toLong())
            mCityTextView.text = weather.nameLocale
            mDescTextView.text = weather.current!!.weather!![0].description
            mTempTextView.text = weather.current!!.temp!!.toInt().toString() + "°"

            mInfoTitleTV.text = "Восход солнца\n"
            mInfoDataTV.text = getTime(weather.current!!.sunrise!!.toLong()) + "\n"

            mInfoTitleTV.append("Заход солнца\n")
            mInfoDataTV.append(getTime(weather.current!!.sunset!!.toLong()) + "\n")

            mInfoTitleTV.append("Чувствуется как\n")
            mInfoDataTV.append("${weather.current!!.feelsLike!!.toInt()}°\n")

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
        })
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