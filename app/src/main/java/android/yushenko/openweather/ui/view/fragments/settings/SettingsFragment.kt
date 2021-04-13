package android.yushenko.openweather.ui.view.fragments.settings

import android.os.Bundle
import android.view.View
import android.yushenko.openweather.R
import android.yushenko.openweather.ui.viewmodel.WeatherViewModel
import android.yushenko.openweather.ui.view.activity.MainActivity
import android.yushenko.openweather.ui.viewmodel.SettingsViewModel
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import kotlinx.android.synthetic.main.settings_fragment.*

class SettingsFragment : Fragment(R.layout.settings_fragment) {

    private val viewModel: SettingsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        email_tv.text = viewModel.getEmailUser()

        settings_bask.setOnClickListener {
            (activity as MainActivity).navController.popBackStack()
        }

        btn_out.setOnClickListener {
            viewModel.signUserOut()
            (activity as MainActivity).navController.popBackStack()
        }
    }
}