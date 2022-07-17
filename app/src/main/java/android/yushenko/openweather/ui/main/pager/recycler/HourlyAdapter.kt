package android.yushenko.openweather.ui.main.pager.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.yushenko.openweather.R
import android.yushenko.openweather.model.HourlyWeather
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class HourlyAdapter : RecyclerView.Adapter<HourlyAdapter.HourlyHolder>() {
    private val listHourly: MutableList<HourlyWeather> = mutableListOf()

    fun setData(list: List<HourlyWeather>) {
        listHourly.clear()
        listHourly.addAll(list)
        notifyDataSetChanged()
    }

    class HourlyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val hourTV: TextView = itemView.findViewById(R.id.hourView)
        private val windTV: TextView = itemView.findViewById(R.id.windView)
        private val iconIV: ImageView = itemView.findViewById(R.id.iconView)
        private val tempTV: TextView = itemView.findViewById(R.id.tempView)

        fun bind(data: HourlyWeather) {
            Picasso.with(itemView.context)
                .load(data.iconUrl)
                .into(iconIV)

            hourTV.text = getTime(data.date)
            windTV.text = "${data.windSpeed} м/с"
            tempTV.text = "${data.temp}°"
        }

        private fun getTime(seconds: Long): String? {
            val date = Date(seconds * 1000L)
            return SimpleDateFormat("HH:mm").format(date)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyHolder {
        val view = LayoutInflater
            .from(parent.context).inflate(R.layout.item_hourly, parent, false)
        return HourlyHolder(view)
    }

    override fun onBindViewHolder(holder: HourlyHolder, position: Int) {
        holder.bind(listHourly[position])
    }

    override fun getItemCount() = listHourly.size

}