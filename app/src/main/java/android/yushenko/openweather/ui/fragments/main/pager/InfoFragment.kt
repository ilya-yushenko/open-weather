package android.yushenko.openweather.ui.fragments.main.pager

import android.os.Bundle
import android.util.Log
import android.view.View
import android.yushenko.openweather.R
import android.yushenko.openweather.ui.activity.MainActivity
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.info_fragment.*

@AndroidEntryPoint
class InfoFragment : Fragment(R.layout.info_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        openSearchFragment.setOnClickListener {
            Log.i("TAG", "Open search")
            (activity as MainActivity).navController.navigate(R.id.action_mainFragment_to_searchFragment)
        }

    }
}