package android.yushenko.openweather.ui.main.pager.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import android.yushenko.openweather.R
import android.yushenko.openweather.databinding.ItemDailyBinding
import android.yushenko.openweather.model.DailyWeather
import android.yushenko.openweather.shared.formatToDay
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class DailyAdapter : RecyclerView.Adapter<DailyAdapter.DailyViewHolder>() {
    private val listDaily: MutableList<DailyWeather> = mutableListOf()

    fun setData(list: List<DailyWeather>) {
        listDaily.clear()
        listDaily.addAll(list)
        notifyDataSetChanged()
    }

    class DailyViewHolder(
        private val binding: ItemDailyBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: DailyWeather) {
            with(binding) {
                Picasso.with(root.context)
                    .load(data.iconUrl)
                    .into(iconView)
                nameDayView.text = data.date.formatToDay()
                tempMaxView.text = root.context.getString(R.string.text_unit_celsius, data.tempMax)
                tempMinView.text = root.context.getString(R.string.text_unit_celsius, data.tempMin)
                windView.text = root.context.getString(R.string.text_unit_ms, data.windSpeed)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        val binding = ItemDailyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DailyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {
        holder.bind(listDaily[position])
    }

    override fun getItemCount(): Int {
        return listDaily.size
    }
}