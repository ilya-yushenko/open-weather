package android.yushenko.openweather.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.yushenko.openweather.R
import android.yushenko.openweather.model.weather.Daily
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class DailyAdapter : RecyclerView.Adapter<DailyAdapter.DailyHolder>() {
    private val listDaily: MutableList<Daily> = mutableListOf()

    fun setData(list: List<Daily>) {
        listDaily.clear()
        listDaily.addAll(list)
        notifyDataSetChanged()
    }

    class DailyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var mNameDayTV: TextView = itemView.findViewById(R.id.name_day)
        private var mWindTV: TextView = itemView.findViewById(R.id.wind_daily_text)
        private var mIconIV: ImageView = itemView.findViewById(R.id.icon_image)
        private var mTempMaxTV: TextView = itemView.findViewById(R.id.temp_max)
        private var mTempMinTV: TextView = itemView.findViewById(R.id.temp_min)

        fun bind(daily: Daily) {
            Picasso.with(itemView.context)
                    .load("http://openweathermap.org/img/wn/" + daily.weather!![0].icon + "@2x.png")
                    .into(mIconIV)
            mNameDayTV.setText(getTime(daily.dt!!.toLong()))

            mTempMaxTV.text = daily.temp!!.max!!.toInt().toString() + "°"
            mTempMinTV.text = daily.temp!!.min!!.toInt().toString() + "°"
            mWindTV.text = daily.windSpeed.toString() + " м/с"
        }

        private fun getTime(seconds: Long): String? {
            val date = Date(seconds * 1000L)
            return SimpleDateFormat("EEEE").format(date)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_daily_list, parent, false)
        return DailyHolder(view)
    }

    override fun onBindViewHolder(holder: DailyHolder, position: Int) {
        holder.bind(listDaily[position])
    }

    override fun getItemCount(): Int {
        return listDaily.size
    }
}