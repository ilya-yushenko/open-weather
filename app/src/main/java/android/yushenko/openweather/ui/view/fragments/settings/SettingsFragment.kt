package android.yushenko.openweather.ui.view.fragments.settings

import android.os.Bundle
import android.view.View
import android.yushenko.openweather.R
import android.yushenko.openweather.ui.viewmodel.WeatherViewModel
import android.yushenko.openweather.ui.view.activity.MainActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import kotlinx.android.synthetic.main.settings_fragment.*

class SettingsFragment : Fragment(R.layout.settings_fragment) {

    private val viewModel: WeatherViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.liveEmailUser.observe(requireActivity(), {
            email_tv.text = it
        })
        viewModel.getEmailUser()

        settings_bask.setOnClickListener {
            (activity as MainActivity).navController.popBackStack()
        }

        btn_out.setOnClickListener {
            viewModel.signUserOut()
            (activity as MainActivity).navController.popBackStack()
        }
    }
}