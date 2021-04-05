package android.yushenko.openweather.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.yushenko.openweather.R
import android.yushenko.openweather.data.viewmodel.WeatherViewModel
import android.yushenko.openweather.ui.MainActivity
import android.yushenko.openweather.ui.pager.ViewPagerFragmentStateAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment(R.layout.main_fragment) {

    private lateinit var adapter: ViewPagerFragmentStateAdapter
    private lateinit var viewPager2: ViewPager2
    private val viewModel: WeatherViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewPager2 = view.findViewById(R.id.view_pager_history)

        image_settings_click.setOnClickListener {
            (activity as MainActivity).navController.navigate(R.id.action_mainFragment_to_settingsFragment)
        }

        image_search_click.setOnClickListener {
            (activity as MainActivity).navController.navigate(R.id.action_mainFragment_to_searchFragment)
        }

    }

    override fun onStart() {
        super.onStart()
        if (viewModel.isUserSignIn()) {
            viewModel.liveHistorySearchData.observe(requireActivity()) {
                adapter = ViewPagerFragmentStateAdapter(it, requireActivity())
                    viewPager2.adapter = adapter
            }
            viewModel.loadHistorySearchLocales()
        } else {
            (activity as MainActivity).navController.navigate(R.id.action_mainFragment_to_loginFragment)
        }
    }
}