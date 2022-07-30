package android.yushenko.openweather.ui.register

import android.yushenko.openweather.R
import android.yushenko.openweather.databinding.RegisterOkFragmentBinding
import android.yushenko.openweather.shared.BaseFragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterOkFragment : BaseFragment<RegisterOkFragmentBinding>(
    RegisterOkFragmentBinding::inflate
) {

    override fun RegisterOkFragmentBinding.onInitListener() {
        loginBack.setOnClickListener {
            findNavController().navigate(R.id.action_registerOkFragment_to_loginFragment)
        }
    }
}