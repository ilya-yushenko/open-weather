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

    override fun init() {
        setupListener()
        setupObserves()
    }

    private fun setupListener() {
        binding.baskClick.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.buttonOut.setOnClickListener {
            viewModel.signUserOut()
            findNavController().popBackStack()
        }
    }

    private fun setupObserves() {
        observe(viewModel.currentUser) {
            setData(it)
        }
    }

    private fun setData(user: UserInitial) {
        binding.textLogin.text = user.name
        binding.textEmail.text = user.email
    }
}