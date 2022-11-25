package com.project.mytask.Activity

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project.mytask.Adapter.RecyclerAdapter
import com.project.mytask.Pojo.Data
import com.project.mytask.R
import com.project.mytask.Url.ApiInterface
import com.project.mytask.Utils.AppSession
import com.project.mytask.Utils.WIFIInternetConnectionDetector
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

class ResultActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FindViewById()
    }

    fun FindViewById() {
        recyclerView = findViewById(R.id.recycleView) as RecyclerView
        apiCall()
    }

    private fun apiCall() {
        if (checkForInternet(this)){
            Toast.makeText(this@ResultActivity, "Network Available", Toast.LENGTH_LONG).show()
        }else {
            Toast.makeText(this@ResultActivity, "Network Not Available", Toast.LENGTH_LONG).show()
        }
    }


    private fun checkForInternet(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }
}