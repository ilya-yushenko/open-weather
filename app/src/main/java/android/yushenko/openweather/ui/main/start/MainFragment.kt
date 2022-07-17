package android.yushenko.openweather.ui.main.start

import android.os.Bundle
import android.view.View
import android.yushenko.openweather.R
import android.yushenko.openweather.ext.observe
import android.yushenko.openweather.ui.main.pager.InfoFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_fragment.*

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.main_fragment) {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: ViewPagerFragmentStateAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        image_settings_click.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_settingsFragment)
        }

        image_search_click.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_searchFragment)
        }

        setupObserving()
    }

    override fun onStart() {
        super.onStart()
        userSignIn()
    }

    private fun setupObserving() {
        observe(viewModel.savedList) {
            val isData = if (it.isNotEmpty()) {
                adapter = ViewPagerFragmentStateAdapter(it, requireActivity())
                viewPager.adapter = adapter
                true
            } else {
                requireActivity().supportFragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, InfoFragment()).commit()
                false
            }
            isList(isData)
        }
    }

    private fun isList(boolean: Boolean) {
        when (boolean) {
            true -> {
                fragmentContainer.visibility = View.GONE; viewPager.visibility = View.VISIBLE
            }
            false -> {
                viewPager.visibility = View.GONE; fragmentContainer.visibility = View.VISIBLE
            }
        }
        progress_bar.visibility = View.GONE
    }

    private fun userSignIn() {
        when (viewModel.isUserSignIn()) {
            false -> {
                findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
            }
        }
    }

}