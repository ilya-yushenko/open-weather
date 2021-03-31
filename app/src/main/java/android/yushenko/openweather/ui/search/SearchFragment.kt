package android.yushenko.openweather.ui.search

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import android.yushenko.openweather.R
import android.yushenko.openweather.data.viewmodel.WeatherViewModel
import android.yushenko.openweather.search.Search
import android.yushenko.openweather.ui.main.WeatherFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer


class SearchFragment : Fragment(R.layout.search_fragment) {

    private lateinit var searchView: SearchView
    private lateinit var infoTV: TextView
    private lateinit var listView: ListView
    private val viewModel: WeatherViewModel by viewModels()

    companion object {
        fun newInstance(): SearchFragment {
            return SearchFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupObserving()
        listView = view.findViewById(R.id.list_view)
        infoTV = view.findViewById(R.id.local_tv)
        infoTV.visibility = View.INVISIBLE

        searchView = view.findViewById(R.id.search_locality)

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
        view.findViewById<View>(R.id.edit_bask).setOnClickListener {
            toBack()
        }
    }

    private fun toBack() {
        val fm = requireActivity().supportFragmentManager
        fm.beginTransaction().replace(R.id.fragment_container,
                WeatherFragment.newInstance()).commit()
    }

    private fun setupObserving() {
        viewModel.liveSearchData.observe(requireActivity(), Observer {
            listShow(it)

        })
        viewModel.liveIsFound.observe(requireActivity(), { visible(it) })
    }

    private fun visible(found: Boolean) {
        if (found) {
            infoTV.visibility = View.INVISIBLE
        } else {
            infoTV.visibility = View.VISIBLE
            infoTV.setText(R.string.text_no_found_search)
        }
    }

    private fun local(locality: String) {
        viewModel.loadSearchRepository(locality)
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
