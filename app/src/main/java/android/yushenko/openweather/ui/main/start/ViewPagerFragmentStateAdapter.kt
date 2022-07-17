package android.yushenko.openweather.ui.main.start

import android.yushenko.openweather.model.Location
import android.yushenko.openweather.ui.main.pager.WeatherFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerFragmentStateAdapter(private val list: List<Location>, activity: FragmentActivity) :
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment = WeatherFragment(list[position])
}