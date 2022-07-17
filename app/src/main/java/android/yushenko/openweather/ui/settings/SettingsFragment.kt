package android.yushenko.openweather.ui.settings

import android.os.Bundle
import android.view.View
import android.yushenko.openweather.R.layout
import android.yushenko.openweather.data.model.authentication.UserInitial
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.idapgroup.lifecycle.ktx.observe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.settings_fragment.*

@AndroidEntryPoint
class SettingsFragment : Fragment(layout.settings_fragment) {

    private val viewModel: SettingsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        observe(viewModel.liveCurrentUser) {
            setData(it)
        }

        baskClick.setOnClickListener {
            findNavController().popBackStack()
        }

        buttonOut.setOnClickListener {
            viewModel.signUserOut()
            findNavController().popBackStack()
        }
    }

    private fun setData(user: UserInitial) {
        textLogin.text = user.name
        textEmail.text = user.email
    }
}