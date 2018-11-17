package org.gnvo.climbing.tracking.climbingtracker.data.room

import android.arch.persistence.room.TypeConverter
import java.util.Date
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson


class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time//.toLong()
    }

    @TypeConverter
    fun fromRouteStyleList(routeStyle: List<String>?): String? {
        if (routeStyle == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.toJson(routeStyle, type)
    }

    @TypeConverter
    fun toRouteStyleList(routeStyleString: String?): List<String>? {
        if (routeStyleString == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(routeStyleString, type)
    }
}