package android.yushenko.openweather.ui.main.pager.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import android.yushenko.openweather.R
import android.yushenko.openweather.databinding.ItemHourlyBinding
import android.yushenko.openweather.model.HourlyWeather
import android.yushenko.openweather.shared.formatToTime
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class HourlyAdapter : RecyclerView.Adapter<HourlyAdapter.HourlyViewHolder>() {
    private val listHourly: MutableList<HourlyWeather> = mutableListOf()

    fun setData(list: List<HourlyWeather>) {
        listHourly.clear()
        listHourly.addAll(list)
        notifyDataSetChanged()
    }

    class HourlyViewHolder(
        private val binding: ItemHourlyBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: HourlyWeather) {
            with(binding) {
                Picasso.with(root.context)
                    .load(data.iconUrl)
                    .into(iconView)
                hourView.text = data.date.formatToTime()
                windView.text = root.context.getString(R.string.text_unit_ms, data.windSpeed)
                tempView.text = root.context.getString(R.string.text_unit_celsius, data.temp)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
        val binding = ItemHourlyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HourlyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {
        holder.bind(listHourly[position])
    }

    override fun getItemCount() = listHourly.size

}