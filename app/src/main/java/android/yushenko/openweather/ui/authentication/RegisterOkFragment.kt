package android.yushenko.openweather.ui.authentication

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.yushenko.openweather.R
import android.yushenko.openweather.ui.MainActivity
import androidx.fragment.app.Fragment

class RegisterOkFragment : Fragment(R.layout.register_ok_fragment) {

    private lateinit var loginBack: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginBack = view.findViewById(R.id.btn_login_back)
        loginBack.setOnClickListener {
            (activity as MainActivity).navController.navigate(R.id.action_registerOkFragment_to_loginFragment)
        }
    }
}