package android.yushenko.openweather;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.yushenko.openweather.search.Search;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private SearchView mSearchView;
    private TextView mInfoTV;
    private SharedPreferences mSettings;
    private ListView mListView;

    public static Intent newIntent(Context context) {
        return new Intent(context, SearchActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mSettings = getSharedPreferences(Preferences.APP_PREFERENCES, Context.MODE_PRIVATE);

        mListView = findViewById(R.id.list_view);

        mInfoTV = findViewById(R.id.local_tv);
        mInfoTV.setText("Hello");
        mInfoTV.setVisibility(View.INVISIBLE);

        mSearchView = findViewById(R.id.search_locality);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i("TAG", "" + query);
                mSearchView.clearFocus();
                getСoordLocal(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getСoordLocal(newText);
                return true;
            }
        });

        findViewById(R.id.edit_bask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getСoordLocal(String locality) {
        Call<List<Search>> call = NetworkService.getInstance().getJsonApi().getSearch(locality);

        call.enqueue(new Callback<List<Search>>() {
            @Override
            public void onResponse(Call<List<Search>> call, Response<List<Search>> response) {
                List<Search> searches = response.body();
                if (response.isSuccessful()) {
                    try {
                        mInfoTV.setVisibility(View.INVISIBLE);
                        listShow(searches);
                    } catch (Exception i) {
                        mInfoTV.setVisibility(View.VISIBLE);
                        mInfoTV.setText(R.string.text_no_found_search);
                    }
                } else {
                    mInfoTV.setVisibility(View.VISIBLE);
                    mInfoTV.setText(R.string.text_no_found_search);
                }

            }

            @Override
            public void onFailure(Call<List<Search>> call, Throwable t) {
                Log.i("TAG", "T: " + t);
            }
        });

    }

    private void listShow(List<Search> searches) {
        String[] sear = new String[searches.size()];

        for (int i = 0; i < searches.size(); i++) {
            sear[i] = searches.get(i).getLocalNames().getRu() + "\n" +
                    searches.get(i).getLocalNames().getAscii() + " " + searches.get(i).getCountry();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(SearchActivity.this,
                android.R.layout.simple_list_item_1, sear);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSettings.edit().putString(Preferences.APP_PREFERENCES_NAME, searches.get(position).getLocalNames().getRu()).apply();
                mSettings.edit().putFloat(Preferences.APP_PREFERENCES_LAT, searches.get(position).getLat()).apply();
                mSettings.edit().putFloat(Preferences.APP_PREFERENCES_LON, searches.get(position).getLon()).apply();
                finish();
            }
        });
    }

}
