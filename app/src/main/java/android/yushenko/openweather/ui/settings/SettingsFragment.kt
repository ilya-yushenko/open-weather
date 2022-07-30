package android.yushenko.openweather.ui.settings

import android.yushenko.openweather.data.model.authentication.UserInitial
import android.yushenko.openweather.databinding.SettingsFragmentBinding
import android.yushenko.openweather.ext.observe
import android.yushenko.openweather.shared.BaseFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseFragment<SettingsFragmentBinding>(
    SettingsFragmentBinding::inflate
) {

    private val viewModel: SettingsViewModel by viewModels()

    override fun SettingsFragmentBinding.onInitListener() {
        binding.baskClick.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.buttonOut.setOnClickListener {
            viewModel.signUserOut()
            findNavController().popBackStack()
        }
    }

    override fun SettingsFragmentBinding.onInitObserving() {
        observe(viewModel.currentUser, ::setData)
    }

    private fun setData(user: UserInitial) {
        binding.textLogin.text = user.name
        binding.textEmail.text = user.email
    }
}