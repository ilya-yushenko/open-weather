package android.yushenko.openweather.ui.view.fragments.authentication

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.yushenko.openweather.R
import android.yushenko.openweather.ui.viewmodel.WeatherViewModel
import android.yushenko.openweather.data.model.authentication.User
import android.yushenko.openweather.ui.view.activity.MainActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe


class RegisterFragment : Fragment(R.layout.register_fragment) {

    private lateinit var inputEmailET: EditText
    private lateinit var inputPasswordET: EditText
    private lateinit var registerBtn: Button
    private lateinit var logInBtn: TextView
    private lateinit var showWarningsTV: TextView

    private val viewModel: WeatherViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserving()

        inputEmailET = view.findViewById(R.id.edit_email)
        inputPasswordET = view.findViewById(R.id.edit_password)
        registerBtn = view.findViewById(R.id.btn_register)
        logInBtn = view.findViewById(R.id.log_in_click)
        showWarningsTV = view.findViewById(R.id.text_wrong_register)

        logInBtn.setOnClickListener {
            (activity as MainActivity).navController.popBackStack()
        }

        registerBtn.setOnClickListener {
            val email = inputEmailET.text.toString()
            val password = inputPasswordET.text.toString()
            val user = User()
            if (!email.isEmpty() && !password.isEmpty()) {
                user.email = email
                user.password = password
                createUser(user)
            } else {
                if (email.isEmpty()) inputEmailET.background = resources.getDrawable(R.drawable.style_border_wrang)
                if (password.isEmpty()) inputPasswordET.background = resources.getDrawable(R.drawable.style_border_wrang)
                showWarnings("Поле не должно быть пустым")
            }

        }

    }

    private fun setupObserving() {
        viewModel.liveCreateUser.observe(requireActivity()) {
            infoCreatedUser(it)
        }
    }
    private fun createUser(user: User) {
        viewModel.createUser(user)
    }


    fun infoCreatedUser(boolean: Boolean) {
        if (boolean) {
            (activity as MainActivity).navController.navigate(R.id.action_registerFragment_to_registerOkFragment)
        } else {
            showWarnings("Ошибка регистрации нового пользователя: Пользователь с таким e-mail уже существует")
        }
    }



    private fun showWarnings(text: String) {
        showWarningsTV.setTextColor(resources.getColor(android.R.color.holo_red_dark))
        showWarningsTV.text = text
        var timer = object : CountDownTimer(6000, 1000) {
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