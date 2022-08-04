package android.yushenko.openweather.ui.main.start

import android.view.View
import android.yushenko.openweather.R
import android.yushenko.openweather.databinding.MainFragmentBinding
import android.yushenko.openweather.ext.observe
import android.yushenko.openweather.shared.BaseFragment
import android.yushenko.openweather.ui.main.pager.InfoFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<MainFragmentBinding>(
    MainFragmentBinding::inflate
) {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: WeatherPageAdapter

    override fun MainFragmentBinding.onInitViews() {
        userSignIn()
    }

    override fun MainFragmentBinding.onInitListener() {
        settingsView.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_settingsFragment)
        }
        searchView.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_searchFragment)
        }
    }

    override fun MainFragmentBinding.onInitObserving() {
        observe(viewModel.savedList) {
            val isData = if (it.isNotEmpty()) {
                adapter = WeatherPageAdapter(it, this@MainFragment)
                binding.pagerView.adapter = adapter
                true
            } else {
                parentFragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, InfoFragment()).commit()
                false
            }
            isList(isData)
        }
    }

    private fun isList(boolean: Boolean) {
        with(binding) {
            when (boolean) {
                true -> {
                    fragmentContainer.visibility = View.GONE; pagerView.visibility = View.VISIBLE
                }
                false -> {
                    pagerView.visibility = View.GONE; fragmentContainer.visibility = View.VISIBLE
                }
            }
            progressView.visibility = View.GONE
        }
    }

    private fun userSignIn() {
        if (!viewModel.isUserSignIn()) {
            findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
        }
    }
}