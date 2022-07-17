package android.yushenko.openweather.ui.main.pager.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.yushenko.openweather.R
import android.yushenko.openweather.model.DailyWeather
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class DailyAdapter : RecyclerView.Adapter<DailyAdapter.DailyHolder>() {
    private val listDaily: MutableList<DailyWeather> = mutableListOf()

    fun setData(list: List<DailyWeather>) {
        listDaily.clear()
        listDaily.addAll(list)
        notifyDataSetChanged()
    }

    class DailyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var mNameDayTV: TextView = itemView.findViewById(R.id.nameDayView)
        private var mWindTV: TextView = itemView.findViewById(R.id.windView)
        private var mIconIV: ImageView = itemView.findViewById(R.id.iconView)
        private var mTempMaxTV: TextView = itemView.findViewById(R.id.tempMaxView)
        private var mTempMinTV: TextView = itemView.findViewById(R.id.tempMinView)

        fun bind(data: DailyWeather) {
            Picasso.with(itemView.context)
                .load(data.iconUrl)
                .into(mIconIV)
            mNameDayTV.text = getTime(data.date)

            mTempMaxTV.text = "${data.tempMax}°"
            mTempMinTV.text = "${data.tempMin}°"
            mWindTV.text = "${data.windSpeed} м/с"
        }

        private fun getTime(seconds: Long): String? {
            val date = Date(seconds * 1000L)
            return SimpleDateFormat("EEEE").format(date)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_daily, parent, false)
        return DailyHolder(view)
    }

    override fun onBindViewHolder(holder: DailyHolder, position: Int) {
        holder.bind(listDaily[position])
    }

    override fun getItemCount(): Int {
        return listDaily.size
    }
}