package android.yushenko.openweather.ui.main.pager

import android.os.Bundle
import android.view.View
import android.yushenko.openweather.R
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.info_fragment.*

@AndroidEntryPoint
class InfoFragment : Fragment(R.layout.info_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        openSearchFragment.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_searchFragment)
        }
    }
}