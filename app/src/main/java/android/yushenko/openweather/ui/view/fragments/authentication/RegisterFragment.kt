package android.yushenko.openweather.ui.view.fragments.authentication

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.yushenko.openweather.R
import android.yushenko.openweather.ui.viewmodel.WeatherViewModel
import android.yushenko.openweather.data.model.authentication.User
import android.yushenko.openweather.ui.view.activity.MainActivity
import android.yushenko.openweather.ui.viewmodel.RegisterViewModel
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.idapgroup.lifecycle.ktx.observe
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.android.synthetic.main.login_fragment.inputEmail
import kotlinx.android.synthetic.main.login_fragment.inputPassword
import kotlinx.android.synthetic.main.register_fragment.*


class RegisterFragment : Fragment(R.layout.register_fragment) {

    private val viewModel: RegisterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserving()

        toLoginClick.setOnClickListener {
            (activity as MainActivity).navController.popBackStack()
        }

        buttonRegister.setOnClickListener {
            val email = inputEmail.text.toString()
            val password = inputPassword.text.toString()
            val user = User()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                user.email = email
                user.password = password
                createUser(user)
            } else {
                isInputEmpty()
            }
        }
    }

    private fun setupObserving() {
      observe(viewModel.liveCrateUserData, {
          infoCreated(it)
      })
    }

    private fun createUser(user: User) {
        viewModel.createUser(user)
    }

    fun infoCreated(boolean: Boolean) {
        if (boolean) {
            (activity as MainActivity).navController.navigate(R.id.action_registerFragment_to_registerOkFragment)
        } else {
            showWarnings("Ошибка регистрации нового пользователя: Пользователь с таким e-mail уже существует")
        }
    }

    private fun showWarnings(text: String) {
        textWrongRegister.setTextColor(resources.getColor(android.R.color.holo_red_dark))
        textWrongRegister.text = text

        inputEmail.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_UP -> {
                    v.background = resources.getDrawable(R.drawable.style_border)
                    isInputEmpty()
                }
            }
            false
        }

        inputPassword.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_UP -> {
                    v.background = resources.getDrawable(R.drawable.style_border)
                    isInputEmpty()
                }
            }
            false
        }
    }

    private fun isInputEmpty() {
        when {
            inputEmail.text.isEmpty() && inputPassword.text.isEmpty() -> {
                inputEmail.background = resources.getDrawable(R.drawable.style_border_wrang)
                inputPassword.background = resources.getDrawable(R.drawable.style_border_wrang)
                showWarnings("Поле не должно быть пустым")
            }
            inputEmail.text.isEmpty() -> {
                inputEmail.background = resources.getDrawable(R.drawable.style_border_wrang)
                showWarnings("Поле не должно быть пустым")
            }
            inputPassword.text.isEmpty() -> {
                inputPassword.background = resources.getDrawable(R.drawable.style_border_wrang)
                showWarnings("Поле не должно быть пустым")
            }
            inputEmail.text.isNotEmpty() && inputPassword.text.isNotEmpty() ->
                textWrongRegister.text = ""
        }

    }
}