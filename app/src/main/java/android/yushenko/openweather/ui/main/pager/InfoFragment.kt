package android.yushenko.openweather.ui.main.pager

import android.yushenko.openweather.R
import android.yushenko.openweather.databinding.InfoFragmentBinding
import android.yushenko.openweather.shared.BaseFragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoFragment : BaseFragment<InfoFragmentBinding>(
    InfoFragmentBinding::inflate
) {

    override fun InfoFragmentBinding.onInitListener() {
        openSearchFragment.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_searchFragment)
        }
    }
}