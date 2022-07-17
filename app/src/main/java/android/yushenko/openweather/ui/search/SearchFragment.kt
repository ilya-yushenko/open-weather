package android.yushenko.openweather.ui.search

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.yushenko.openweather.R
import android.yushenko.openweather.ext.changeObserve
import android.yushenko.openweather.ext.hideKeyboard
import android.yushenko.openweather.ext.observe
import android.yushenko.openweather.model.Search
import android.yushenko.openweather.shared.ViewState
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.idapgroup.lifecycle.ktx.observe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.search_fragment.*

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.search_fragment) {

    private val viewModel: SearchViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        editTextView.changeObserve {
            closeView.visibility = if (it.isEmpty()) View.GONE else View.VISIBLE
            viewModel.setQuery(it)
        }

        setupListener()
        setupObserving()
    }

    private fun setupListener() {
        closeView.setOnClickListener {
            editTextView.text?.clear()
        }
        backClick.setOnClickListener {
            backScreen()
        }
    }

    private fun setupObserving() {
        with(viewModel) {
            observe(viewState) {
                searchView.isVisible = it == ViewState.Idle
                progressView.isVisible = it == ViewState.Loading
            }
            observe(searchData) {
                listShow(it)
            }
        }
    }

    private fun visible(found: Boolean) {
        if (found) {
            textViewInfo.visibility = View.INVISIBLE
        } else {
            textViewInfo.visibility = View.VISIBLE
            textViewInfo.setText(R.string.text_no_found_search)
        }
    }

    private fun listShow(searches: List<Search>) {
        if (searches.isNotEmpty()) {
            visible(true)
            val array = searches.toTypedArray()
                .map { "${it.city}\n${it.country}" }

            val adapter = ArrayAdapter(
                requireActivity(),
                android.R.layout.simple_list_item_1, array
            )
            listView.adapter = adapter
        } else {
            visible(false)

        }

        listView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                viewModel.addItemHistory(searches[position])
                backScreen()
            }
    }

    private fun backScreen() {
        hideKeyboard()
        findNavController().popBackStack()
    }
}
