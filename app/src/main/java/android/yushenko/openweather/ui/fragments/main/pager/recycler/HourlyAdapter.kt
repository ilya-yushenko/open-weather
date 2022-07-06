package android.yushenko.openweather.ui.fragments.main.pager.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.yushenko.openweather.R
import android.yushenko.openweather.data.model.weather.Hourly
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class HourlyAdapter : RecyclerView.Adapter<HourlyAdapter.HourlyHolder>() {
    private val listHourly: MutableList<Hourly> = mutableListOf()

    fun setData(list: List<Hourly>) {
        listHourly.clear()
        listHourly.addAll(list)
        notifyDataSetChanged()
    }

    class HourlyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val hourTV: TextView = itemView.findViewById(R.id.hour_text)
        private val windTV: TextView = itemView.findViewById(R.id.wind_text)
        private val iconIV: ImageView = itemView.findViewById(R.id.icon_image)
        private val tempTV: TextView = itemView.findViewById(R.id.textTemp)

        fun bind(hourly: Hourly) {
            Picasso.with(itemView.context)
                    .load("http://openweathermap.org/img/wn/" + hourly.weather!![0].icon + "@2x.png")
                    .into(iconIV)

            hourTV.setText(getTime(hourly.dt!!.toLong()))
            windTV.text = hourly.windSpeed.toString() + " м/с"
            tempTV.text = hourly.temp!!.toInt().toString() + "°"
        }

        private fun getTime(seconds: Long): String? {
            val date = Date(seconds * 1000L)
            return SimpleDateFormat("HH:mm").format(date)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyHolder {
        val view = LayoutInflater
                .from(parent.context).inflate(R.layout.item_hourly_list, parent, false)
        return HourlyHolder(view)
    }

    override fun onBindViewHolder(holder: HourlyHolder, position: Int) {
        holder.bind(listHourly[position])
    }

    override fun getItemCount() = listHourly.size

}