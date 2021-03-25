package android.yushenko.openweather.data

import android.yushenko.openweather.model.Daily
import android.yushenko.openweather.model.Hourly
import java.util.*

class DataLab private constructor() {
    var hourlyList: List<Hourly>
        private set
    var dailyList: List<Daily>
        private set

    fun addHourlyList(list: List<Hourly>?) {
        hourlyList = list!!
    }

    fun addDailyList(list: List<Daily>?) {
        dailyList = list!!
    }

    companion object {
        private var sDataLab: DataLab? = null
        fun get(): DataLab? {
            if (sDataLab == null) {
                sDataLab = DataLab()
            }
            return sDataLab
        }
    }

    init {
        hourlyList = ArrayList()
        dailyList = ArrayList()
    }
}