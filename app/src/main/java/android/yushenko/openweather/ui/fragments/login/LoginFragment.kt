package android.yushenko.openweather.ui.fragments.login

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.yushenko.openweather.R
import android.yushenko.openweather.data.model.authentication.User
import android.yushenko.openweather.ui.activity.MainActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.idapgroup.lifecycle.ktx.observe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.login_fragment.*

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.login_fragment) {

    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserving()

        buttonLogin.setOnClickListener {
            val email = inputName.text.toString()
            val password = inputPassword.text.toString()
            val user = User()

            if (!email.isEmpty() && !password.isEmpty()) {
                user.email = email
                user.password = password
                signIn(user)
            } else {
                if (email.isEmpty()) inputName.background = resources.getDrawable(R.drawable.style_border_wrang)
                if (password.isEmpty()) inputPassword.background = resources.getDrawable(R.drawable.style_border_wrang)
                showWarnings("Поле не должно быть пустым")
            }
        }

        toRegisterClick.setOnClickListener {
            (activity as MainActivity).navController.navigate(R.id.action_loginFragment_to_registerFragment)
        }

    }

    private fun setupObserving() {
        observe(viewModel.liveSignInUser, { infoSignUser(it) })
    }

    private fun infoSignUser(it: Boolean) {
        if (it) {
            (activity as MainActivity).navController.popBackStack()
        } else {
            showWarnings("Ошыбка аутентификации")
        }
    }

    private fun signIn(user: User) {
        viewModel.signInUser(user)
    }

    private fun showWarnings(text: String) {
        textWrongLogin.setTextColor(resources.getColor(android.R.color.holo_red_dark))
        textWrongLogin.text = text

        inputName.setOnTouchListener { v, event ->
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

    fun isInputEmpty() {
        if (!inputName.text.isEmpty() || !inputPassword.text.isEmpty()) {
            textWrongLogin.text = ""
        }
    }

}