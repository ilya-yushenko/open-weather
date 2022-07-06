package android.yushenko.openweather.ui.fragments.settings

import android.os.Bundle
import android.view.View
import android.yushenko.openweather.R.*
import android.yushenko.openweather.ui.activity.MainActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.idapgroup.lifecycle.ktx.observe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.settings_fragment.*

@AndroidEntryPoint
class SettingsFragment : Fragment(layout.settings_fragment) {

    private val viewModel: SettingsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        observe(viewModel.liveUserUserData, {
            textLogin.text = it.name
            textEmail.text = it.email
        })

        baskClick.setOnClickListener {
            (activity as MainActivity).navController.popBackStack()
        }

        buttonOut.setOnClickListener {
            viewModel.signUserOut()
            (activity as MainActivity).navController.popBackStack()
        }
    }
}