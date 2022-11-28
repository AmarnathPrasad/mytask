package com.project.mytask

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
import com.project.mytask.Url.ApiInterface
import com.project.mytask.Utils.AppSession
import com.project.mytask.Utils.WIFIInternetConnectionDetector
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type


class MainActivity : AppCompatActivity(){
    private var mSession: AppSession? = null
    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: RecyclerAdapter
    var cd: WIFIInternetConnectionDetector? = null
    var isConnectionExist = false
    var list: ArrayList<JSONObject>? = null
    //var responce: ArrayList<Data>? = null
    //var sharedPreferences: SharedPreferences? = null

    var sharedPref: SharedPreferences? =null
    private val PREFS_NAME = "sharedpref"
    lateinit var offline_data: ArrayList<Data>
    lateinit var offline_data2: ArrayList<Data>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FindViewById()
    }

    fun FindViewById() {
        cd = WIFIInternetConnectionDetector(this)
        isConnectionExist = cd!!.checkMobileInternetConn()
       // sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE)
        sharedPref = getSharedPreferences(PREFS_NAME,MODE_PRIVATE)
        mSession = AppSession.getInstance(this)

        recyclerView = findViewById(R.id.recycleView) as RecyclerView
       /* if(recyclerAdapter != null) {
            recyclerAdapter = RecyclerAdapter(ArrayList<Data>(), this)
            recyclerView.adapter = recyclerAdapter
        }*/
       // callApi()
        apiCall2()
    }

    private fun apiCall() {
        isConnectionExist = cd!!.checkMobileInternetConn()
        if (isConnectionExist == true) {
            /* if (mSession!!.getPostData()!!.isEmpty()) {
               setApiData()
           } else {
               getApiData()
           }*/
            setApiData()
        }else{
            Log.d("Sorry ","No Internet Connection")
            /* if (mSession!!.getPostData() != null && !mSession!!.getPostData()!!.isEmpty()) {
                 getApiData()
             }*/
            getApiData()
        }
    }


    private fun apiCall2() {
        if (checkForInternet(this)){
            Log.d("Present ","Internet Connection")
           // Toast.makeText(this@MainActivity, "Network Available", Toast.LENGTH_LONG).show()

             setApiData()
        }else {
            Log.d("Sorry ","No Internet Connection")
           // Toast.makeText(this@MainActivity, "Network Not Available", Toast.LENGTH_LONG).show()

           /* if(offline_data != null && !offline_data.isEmpty()){
                getApiData()
            }*/
            getApiData()
        }

    /*else{
            Log.d("Sorry ","No Internet Connection")
            if(offline_data != null && !offline_data.isEmpty()){
                getApiData()
            }
        }*/
    }

    fun getApiData(){
        try {
            //var list: List<Data?>? = mSession!!.getList()
           // println("GeTOFFILINE:"+list.toString())

            val sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE)
            val gson = Gson()
            val json = sharedPreferences.getString("courses", null)
            val type: Type = object : TypeToken<ArrayList<Data?>?>() {}.type
            offline_data = gson.fromJson<Any>(json, type) as ArrayList<Data>
            println("Offline Data:"+offline_data)
            if (offline_data == null) {
               // offline_data = ArrayList()
               // listData(offline_data)

                recyclerView.setHasFixedSize(true)
                recyclerAdapter = RecyclerAdapter(ArrayList<Data>(), this)
                recyclerView.adapter = recyclerAdapter
                recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                recyclerAdapter.Update(offline_data)
            }



        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }



    fun setApiData(){
        val retrofit = ApiInterface
        val api = retrofit.create()
        val call: Call<ArrayList<Data>> = api.getData()
        call.enqueue(object : Callback<ArrayList<Data>> {
            override fun onResponse(call: Call<ArrayList<Data>>,
                                    response: Response<ArrayList<Data>>) {
                if (response != null) {
                    var list: ArrayList<Data>? = response.body()
                    println("responce: " + response.body())
                   // mSession!!.setPostData(response.body().toString())

                    for (i in list!!.indices) {
                        println("title:" + list.get(i).title)
                    }

                    val sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    val gson = Gson()
                    val json: String = gson.toJson(list)
                    editor.putString("courses", json)
                    editor.apply()

                   // mSession!!.setList("pref",list)

                    listData(list)
                }
            }

            override fun onFailure(call: Call<ArrayList<Data>>, t: Throwable) {
                println("Throwable:"+t.toString())
            }
        })
    }



  /*  fun callApi() {
        val retrofit = ApiInterface
        val api = retrofit.create()
        val call: Call<ArrayList<Data>> = api.getData()
        call.enqueue(object : Callback<ArrayList<Data>> {
            override fun onResponse(call: Call<ArrayList<Data>>,
                response: Response<ArrayList<Data>>) {
                if (response != null) {
                    var list: ArrayList<Data>? = response.body()
                      println("responce: " + response.body())
                     for (i in list!!.indices) {
                         println("title:" + list.get(i).title)
                     }
                     listData(list)
                }
            }

            override fun onFailure(call: Call<ArrayList<Data>>, t: Throwable) {
                println("Throwable:"+t.toString())
            }
        })
    }*/

    private fun listData(list: ArrayList<Data>) {
        if (list != null && list.size > 0) {
            recyclerView.setHasFixedSize(true)
            recyclerAdapter = RecyclerAdapter(ArrayList<Data>(), this)
            recyclerView.adapter = recyclerAdapter
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerAdapter.Update(list)
        }
    }



    private fun checkForInternet(context: Context): Boolean {

        // register activity with the connectivity manager service
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // if the android version is equal to M
        // or greater we need to use the
        // NetworkCapabilities to check what type of
        // network has the internet connection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Returns a Network object corresponding to
            // the currently active default data network.
            val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // Indicates this network uses a Wi-Fi transport,
                // or WiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // Indicates this network uses a Cellular transport. or
                // Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
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


