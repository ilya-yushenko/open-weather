package android.yushenko.openweather.data;

import android.yushenko.openweather.model.Hourly;

import java.util.ArrayList;
import java.util.List;

public class DataLab {

    private static DataLab sDataLab;
    private List<Hourly> mList;

    public static DataLab get() {
        if (sDataLab == null) {
            sDataLab = new DataLab();
        }
        return sDataLab;
    }

    private DataLab() {
        mList = new ArrayList<>();
    }

    public void addHourlyList(List<Hourly> list) {
        mList = list;
    }

    public List<Hourly> getHourlyList() {
        return mList;
    }
}
