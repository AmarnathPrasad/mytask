package com.project.mytask.Utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project.mytask.Pojo.Data
import retrofit2.Response
import java.lang.reflect.Type

class AppSession(val context: Context?) {
    private val prefs: SharedPreferences
    private val PREFS_NAME = "sharedpref"

    val sharedPreferences: SharedPreferences? = context!!.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)



    /*------------------Permission---------------*/
    var permission: Boolean
        get() = prefs.getBoolean("firstRun", false)
        set(value) {
            prefs.edit().putBoolean("firstRun", value).commit()
        }

    /*------------------Save Events API DATA---------------*/
    fun getPostData(): String? {
        return prefs.getString("posts", "")
    }

    fun setPostData(value: String?) {
        prefs.edit().putString("posts", value).commit()
    }



    private val editor = sharedPreferences!!.edit()

    fun <T> setList(key: String?, list: List<T>?) {
        val gson = Gson()
        val json = gson.toJson(list)
        set(key, json)
    }

    operator fun set(key: String?, value: String?) {
        editor.putString(key, value)
        editor.commit()
    }


    fun getList(): ArrayList<Data?>? {
        val arrayItems: List<Data>
        // val serializedObject: String = sharedPreferences.getString(KEY_PREFS, null)
        val prefs = context!!.getSharedPreferences(PREFS_NAME, AppCompatActivity.MODE_PRIVATE)
        val serializedObject = prefs.getString("pref", null)
        if (serializedObject != null) {
            val gson = Gson()
            val type: Type = object : TypeToken<List<Data?>?>() {}.type
            arrayItems = gson.fromJson<List<Data>>(serializedObject, type)
        }
        return getList()
    }





    companion object {
        private var session: AppSession? = null
        fun getInstance(context: Context?): AppSession? {
            if (session == null) {
                session = AppSession(context)
            }
            return session
        }
    }

    init {
        prefs = PreferenceManager.getDefaultSharedPreferences(context)
    }
}