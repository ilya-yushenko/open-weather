package android.yushenko.openweather.ui.login

import android.view.MotionEvent
import android.yushenko.openweather.R
import android.yushenko.openweather.databinding.LoginFragmentBinding
import android.yushenko.openweather.ext.observe
import android.yushenko.openweather.shared.BaseFragment
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginFragmentBinding>(
    LoginFragmentBinding::inflate
) {

    private val viewModel: LoginViewModel by viewModels()

    override fun LoginFragmentBinding.onInitListener() {
        buttonLogin.setOnClickListener {
            val email = inputName.text.toString()
            val password = inputPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.signInUser(
                    email = email,
                    password = password
                )
            } else {
                if (email.isEmpty()) inputName.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.style_border_wrang)
                if (password.isEmpty()) inputPassword.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.style_border_wrang)
                showWarnings("Поле не должно быть пустым")
            }
        }

        toRegisterClick.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    override fun LoginFragmentBinding.onInitObserving() {
        observe(viewModel.isSignIn) { infoSignUser(it) }
    }

    private fun LoginFragmentBinding.infoSignUser(it: Boolean) {
        if (it) {
            findNavController().popBackStack()
        } else {
            showWarnings("Ошыбка аутентификации")
        }
    }

    private fun LoginFragmentBinding.showWarnings(text: String) {
        textWrongLogin.setTextColor(
            ContextCompat.getColor(requireContext(), android.R.color.holo_red_dark)
        )
        textWrongLogin.text = text

        inputName.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_UP -> {
                    v.background =
                        ContextCompat.getDrawable(requireContext(), R.drawable.style_border)
                    isInputEmpty()
                }
            }
            false
        }

        inputPassword.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_UP -> {
                    v.background =
                        ContextCompat.getDrawable(requireContext(), R.drawable.style_border)
                    isInputEmpty()
                }
            }
            false
        }
    }

    private fun LoginFragmentBinding.isInputEmpty() {
        if (inputName.text.isNotEmpty() || inputPassword.text.isNotEmpty()) {
            textWrongLogin.text = ""
        }
    }

}