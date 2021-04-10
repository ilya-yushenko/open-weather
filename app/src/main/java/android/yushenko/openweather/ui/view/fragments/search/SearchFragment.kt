package android.yushenko.openweather.ui.view.fragments.search

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import android.yushenko.openweather.R
import android.yushenko.openweather.ui.viewmodel.WeatherViewModel
import android.yushenko.openweather.data.model.search.Search
import android.yushenko.openweather.ui.view.activity.MainActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.search_fragment.*


class SearchFragment : Fragment(R.layout.search_fragment) {
    private val viewModel: WeatherViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupObserving()
        textViewInfo.visibility = View.INVISIBLE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Log.i("TAG", "" + query)
                searchView.clearFocus()
                local(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                local(newText)
                return true
            }
        })

        backClick.setOnClickListener { toBack() }
    }

    private fun toBack() {
        val imm = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
        (activity as MainActivity).navController.popBackStack()
    }

    private fun setupObserving() {
        viewModel.liveSearchData.observe(requireActivity(), Observer {
            listShow(it)

        })
        viewModel.liveIsFound.observe(requireActivity()) { visible(it) }
    }

    private fun visible(found: Boolean) {
        if (found) {
            textViewInfo.visibility = View.INVISIBLE
        } else {
            textViewInfo.visibility = View.VISIBLE
            textViewInfo.setText(R.string.text_no_found_search)
        }
    }

    private fun local(locality: String) {
        viewModel.loadSearch(locality)
    }

    private fun listShow(searches: List<Search>) {
        val array = searches.toTypedArray().map { "${it.localNames?.ru}\n${it.localNames?.ascii} ${it.country}" }
        val adapter = ArrayAdapter(requireActivity(),
                android.R.layout.simple_list_item_1, array)
        listView.adapter = adapter
        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            viewModel.savePreference(searches[position])
            toBack()
        }
    }
}
