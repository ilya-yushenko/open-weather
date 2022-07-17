package android.yushenko.openweather.ui.register

import android.os.Bundle
import android.view.View
import android.yushenko.openweather.R
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.register_ok_fragment.*

@AndroidEntryPoint
class RegisterOkFragment : Fragment(R.layout.register_ok_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginBack.setOnClickListener {
            findNavController().navigate(R.id.action_registerOkFragment_to_loginFragment)
        }
    }
}