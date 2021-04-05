package android.yushenko.openweather.ui.pager

import android.yushenko.openweather.model.search.Search
import android.yushenko.openweather.ui.main.WeatherFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerFragmentStateAdapter(searches: List<Search>, activity: FragmentActivity) : FragmentStateAdapter(activity) {

    private val list = searches

    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment {
        val fragment = WeatherFragment.newInstance()
        fragment.setData(list[position])
        return fragment
    }

}