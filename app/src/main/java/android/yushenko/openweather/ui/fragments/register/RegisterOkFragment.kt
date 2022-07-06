package android.yushenko.openweather.ui.fragments.register

import android.os.Bundle
import android.view.View
import android.yushenko.openweather.R
import android.yushenko.openweather.ui.activity.MainActivity
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.register_ok_fragment.*

@AndroidEntryPoint
class RegisterOkFragment : Fragment(R.layout.register_ok_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginBack.setOnClickListener {
            (activity as MainActivity).navController.navigate(R.id.action_registerOkFragment_to_loginFragment)
        }
    }
}