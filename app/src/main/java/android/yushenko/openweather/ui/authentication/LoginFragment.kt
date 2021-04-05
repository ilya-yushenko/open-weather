package android.yushenko.openweather.ui.authentication

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.yushenko.openweather.R
import android.yushenko.openweather.data.viewmodel.WeatherViewModel
import android.yushenko.openweather.model.authentication.User
import android.yushenko.openweather.ui.MainActivity
import android.yushenko.openweather.ui.main.MainFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe


class LoginFragment : Fragment(R.layout.login_fragment) {

    private lateinit var inputEmailET: EditText
    private lateinit var inputPasswordET: EditText

    private lateinit var loginInBtn: Button
    private lateinit var registerBtn: TextView

    private lateinit var showWarningsTV: TextView

    private val viewModel: WeatherViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserving()

        inputEmailET = view.findViewById(R.id.edit_email)
        inputPasswordET = view.findViewById(R.id.edit_password)
        loginInBtn = view.findViewById(R.id.btn_login)
        registerBtn = view.findViewById(R.id.register_click)
        showWarningsTV = view.findViewById(R.id.text_wrong_login)

        loginInBtn.setOnClickListener {
            val email = inputEmailET.text.toString()
            val password = inputPasswordET.text.toString()
            val user = User()

            if (!email.isEmpty() && !password.isEmpty()) {
                user.email = email
                user.password = password
                signIn(user)
            } else {
                if (email.isEmpty()) inputEmailET.background = resources.getDrawable(R.drawable.style_border_wrang)
                if (password.isEmpty()) inputPasswordET.background = resources.getDrawable(R.drawable.style_border_wrang)
                showWarnings("Поле не должно быть пустым")
            }
        }

        registerBtn.setOnClickListener {
            (activity as MainActivity).navController.navigate(R.id.action_loginFragment_to_registerFragment)
        }

    }

    private fun setupObserving() {
        viewModel.liveSignInUser.observe(requireActivity()) {
            infoSignUser(it)
        }
    }

    private fun infoSignUser(it: Boolean) {
        if (it) {
            (activity as MainActivity).navController.navigate(R.id.action_loginFragment_to_mainFragment)
        } else {
            showWarnings("Ошыбка аутентификации")
        }
    }

    private fun signIn(user: User) {
        viewModel.signInUser(user)
    }

    private fun showWarnings(text: String) {
        showWarningsTV.setTextColor(resources.getColor(android.R.color.holo_red_dark))
        showWarningsTV.text = text
        val timer = object : CountDownTimer(6000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                inputEmailET.background = resources.getDrawable(R.drawable.style_border)
                inputPasswordET.background = resources.getDrawable(R.drawable.style_border)
                showWarningsTV.text = ""
            }
        }
        timer.start()
    }

}