package com.project.mytask.Utils

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project.mytask.Pojo.Data
import java.lang.reflect.Type


class SharedPreferenceManager(val context: Context) {

    private val PREFS_NAME = "sharedpref"
    val sharedPref: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)


    fun saveUSer(user: Data) {
        sharedPref.edit().putString("user", Gson().toJson(user)).apply()
    }

    fun getUser(): Data? {
        val data = sharedPref.getString("user", null)
        if (data == null) {
            return null
        }
        return Gson().fromJson(data, Data::class.java)
    }

    fun <T> setList(key: String?, list: List<T>?) {
        val gson = Gson()
        val json = gson.toJson(list)
        set(key, json)
    }

    operator fun set(key: String?, value: String?) {
        sharedPref.edit().putString(key, value)
        sharedPref.edit().commit()
    }

    fun getList(): List<Data?>? {
        val arrayItems: List<Data>
        // val serializedObject: String = sharedPreferences.getString(KEY_PREFS, null)
        val prefs = context.getSharedPreferences(PREFS_NAME, AppCompatActivity.MODE_PRIVATE)
        val serializedObject = prefs.getString("text", null)
        if (serializedObject != null) {
            val gson = Gson()
            val type: Type = object : TypeToken<List<Data?>?>() {}.type
            arrayItems = gson.fromJson<List<Data>>(serializedObject, type)
        }
        return getList()
    }
}