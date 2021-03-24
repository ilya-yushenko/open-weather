package android.yushenko.openweather.ui.daily;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.yushenko.openweather.R;
import android.yushenko.openweather.data.DataLab;
import android.yushenko.openweather.model.Daily;
import android.yushenko.openweather.model.Hourly;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DailyFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private List<Daily> mList;
    private DailyAdapter mAdapter;

    public static DailyFragment newInstance() {
        return new DailyFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_daily_fragment, container, false);

        mRecyclerView = view.findViewById(R.id.rec_daily_view);
        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(getActivity()));
        update();

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("TAG", "Pause Frag");
    }

    @Override
    public void onResume() {
        super.onResume();
        update();
        Log.i("TAG", "Resume Frag");
    }

    private void update() {
        mList = DataLab.get().getDailyList();
        if (mAdapter == null) {
            mAdapter = new DailyAdapter();
            mRecyclerView.setAdapter(mAdapter);
        }
    }


    private class DailyHolder extends RecyclerView.ViewHolder {

        private TextView mNameDayTV;
        private TextView mWindTV;
        private ImageView mIconIV;
        private TextView mTempMaxTV;
        private TextView mTempMinTV;

        public DailyHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_daily_list, parent, false));
            mNameDayTV = itemView.findViewById(R.id.name_day);
            mWindTV = itemView.findViewById(R.id.wind_daily_text);
            mIconIV = itemView.findViewById(R.id.icon_image);
            mTempMaxTV = itemView.findViewById(R.id.temp_max);
            mTempMinTV = itemView.findViewById(R.id.temp_min);
        }

        public void bind(Daily daily) {
            Picasso.with(getActivity())
                    .load("http://openweathermap.org/img/wn/" + daily.getWeather().get(0).getIcon() + "@2x.png")
                    .into(mIconIV);
            mNameDayTV.setText(getTime(daily.getDt()));

            mTempMaxTV.setText(daily.getTemp().getMax().intValue() + "°");
            mTempMinTV.setText(daily.getTemp().getMin().intValue() + "°");
            mWindTV.setText(daily.getWindSpeed() + " м/с");
        }
    }

    private class DailyAdapter extends RecyclerView.Adapter<DailyHolder> {
        @NonNull
        @Override
        public DailyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new DailyHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull DailyHolder holder, int position) {
            holder.bind(mList.get(position));
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }

    private String getTime(long seconds) {
        Date date = new Date(seconds * 1000L);
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE");
        return dateFormat.format(date);
    }

}
