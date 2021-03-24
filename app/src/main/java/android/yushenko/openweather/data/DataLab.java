package android.yushenko.openweather.data;

import android.yushenko.openweather.model.Daily;
import android.yushenko.openweather.model.Hourly;

import java.util.ArrayList;
import java.util.List;

public class DataLab {

    private static DataLab sDataLab;
    private List<Hourly> mHourlyList;
    private List<Daily> mDailyList;

    public static DataLab get() {
        if (sDataLab == null) {
            sDataLab = new DataLab();
        }
        return sDataLab;
    }

    private DataLab() {
        mHourlyList = new ArrayList<>();
        mDailyList = new ArrayList<>();
    }

    public void addHourlyList(List<Hourly> list) {
        mHourlyList = list;
    }

    public List<Hourly> getHourlyList() {
        return mHourlyList;
    }

    public void addDailyList(List<Daily> list) {
        mDailyList = list;
    }

    public List<Daily> getDailyList() {
        return mDailyList;
    }
}
