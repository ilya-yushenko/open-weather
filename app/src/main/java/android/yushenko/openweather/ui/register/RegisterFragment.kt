package android.yushenko.openweather.ui.register

import android.view.MotionEvent
import android.yushenko.openweather.R
import android.yushenko.openweather.data.model.authentication.UserInitial
import android.yushenko.openweather.databinding.RegisterFragmentBinding
import android.yushenko.openweather.ext.observe
import android.yushenko.openweather.shared.BaseFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<RegisterFragmentBinding>(
    RegisterFragmentBinding::inflate
) {

    private val viewModel: RegisterViewModel by viewModels()

    override fun RegisterFragmentBinding.onInitListener() {
        buttonRegister.setOnClickListener {
            val name = inputName.text.toString()
            val email = inputEmail.text.toString()
            val password = inputPassword.text.toString()
            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.createUser(
                    UserInitial(
                        name = name,
                        email = email,
                        password = password,
                        locatoin = ""
                    )
                )
            } else {
                isInputEmpty()
            }
        }
        toLoginClick.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun RegisterFragmentBinding.onInitObserving() {
        observe(viewModel.isCrateUser, ::infoCreated)
    }

    private fun infoCreated(boolean: Boolean) {
        if (boolean) {
            findNavController().navigate(R.id.action_registerFragment_to_registerOkFragment)
        } else {
            showWarnings("Ошибка регистрации нового пользователя: Пользователь с таким e-mail уже существует")
        }
    }

    private fun showWarnings(text: String) {
        with(binding) {
            textWrongRegister.setTextColor(resources.getColor(android.R.color.holo_red_dark))
            textWrongRegister.text = text

            inputName.setOnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_UP -> {
                        v.background = resources.getDrawable(R.drawable.style_border)
                        isInputEmpty()
                    }
                }
                false
            }

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
    }

    private fun isInputEmpty() {
        with(binding) {
            when {
                inputName.text.isEmpty() && inputEmail.text.isEmpty() && inputPassword.text.isEmpty() -> {
                    inputName.background = resources.getDrawable(R.drawable.style_border_wrang)
                    inputEmail.background = resources.getDrawable(R.drawable.style_border_wrang)
                    inputPassword.background = resources.getDrawable(R.drawable.style_border_wrang)
                    showWarnings("Поля не должны быть пустыми")
                }

                inputEmail.text.isEmpty() -> {
                    inputEmail.background = resources.getDrawable(R.drawable.style_border_wrang)
                    showWarnings("Поле не должно быть пустым")
                }

                inputName.text.isEmpty() -> {
                    inputName.background = resources.getDrawable(R.drawable.style_border_wrang)
                    showWarnings("Поле не должно быть пустым")
                }

                inputPassword.text.isEmpty() -> {
                    inputPassword.background = resources.getDrawable(R.drawable.style_border_wrang)
                    showWarnings("Поле не должно быть пустым")
                }

                inputName.text.isNotEmpty() && inputPassword.text.isNotEmpty() ->
                    textWrongRegister.text = ""

            }
        }
    }
}