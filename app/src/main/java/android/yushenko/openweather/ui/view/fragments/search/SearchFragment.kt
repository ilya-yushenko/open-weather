package android.yushenko.openweather.ui.view.fragments.search

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import android.yushenko.openweather.R
import android.yushenko.openweather.data.model.search.Search
import android.yushenko.openweather.ui.view.activity.MainActivity
import android.yushenko.openweather.ui.viewmodel.SearchViewModel
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.search_fragment.*

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.search_fragment) {

    private val viewModel: SearchViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupObserving()
        textViewInfo.visibility = View.INVISIBLE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.length > 1){
                    loadSearch(query)
                    visible(true)
                } else {
                    visible(false)
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.length > 1) {
                    loadSearch(newText)
                    visible(true)
                } else {
                    visible(false)
                }
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
        viewModel.liveSearchData.observe(requireActivity(), Observer { listShow(it) })
    }

    private fun visible(found: Boolean) {
        if (found) {
            textViewInfo.visibility = View.INVISIBLE
        } else {
            textViewInfo.visibility = View.VISIBLE
            textViewInfo.setText(R.string.text_no_found_search)
        }
    }

    private fun loadSearch(locality: String) {
        viewModel.loadSearch(locality)
    }

    private fun listShow(searches: List<Search>) {
        if (searches.isNotEmpty()) {
            visible(true)
            val array = searches.toTypedArray().map { "${it.localNames?.ru}\n${it.localNames?.ascii} ${it.country}" }
            val adapter = ArrayAdapter(requireActivity(),
                    android.R.layout.simple_list_item_1, array)
            listView.adapter = adapter
        } else {
            visible(false)

        }

        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            viewModel.addItemHistory(searches[position])
            toBack()
        }
    }
}
