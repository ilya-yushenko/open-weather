package android.yushenko.openweather.ui.hourly;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.yushenko.openweather.data.DataLab;
import android.yushenko.openweather.R;
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

public class HourlyFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private List<Hourly> mList;
    private HourlyAdapter mAdapter;

    public static HourlyFragment newInstance() {
        return new HourlyFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);

        mRecyclerView = view.findViewById(R.id.rec_view);
        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
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
        mList = DataLab.get().getHourlyList();
        if (mAdapter == null) {
            mAdapter = new HourlyAdapter();
            mRecyclerView.setAdapter(mAdapter);
        }
    }



    private class HourlyHolder extends RecyclerView.ViewHolder{

        private TextView mHourTV;
        private TextView mWindTV;
        private ImageView mIconIV;
        private TextView mTempTV;

        public HourlyHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_list, parent, false));
            mHourTV = itemView.findViewById(R.id.hour_text);
            mWindTV = itemView.findViewById(R.id.wind_text);
            mIconIV = itemView.findViewById(R.id.icon_image);
            mTempTV = itemView.findViewById(R.id.temp_tv);
        }

        public void bind(Hourly hourly) {
            Picasso.with(getActivity())
                    .load("http://openweathermap.org/img/wn/" + hourly.getWeather().get(0).getIcon() + "@2x.png")
                    .into(mIconIV);
            mHourTV.setText(getTime(hourly.getDt()));
            mWindTV.setText(hourly.getWindSpeed() + " м/с");
            mTempTV.setText(hourly.getTemp().intValue() + "°");
        }
    }

    private class HourlyAdapter extends RecyclerView.Adapter<HourlyHolder> {

        @NonNull
        @Override
        public HourlyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new HourlyHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull HourlyHolder holder, int position) {
            holder.bind(mList.get(position));
        }

        @Override
        public int getItemCount() {
            return 24;
        }
    }

    private String getTime(long seconds) {
        Date date = new Date(seconds * 1000L);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(date);
    }

}
