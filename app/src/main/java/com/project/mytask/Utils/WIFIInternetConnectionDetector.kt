package com.project.mytask.Utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class WIFIInternetConnectionDetector(private val _context: Context) {
    fun checkMobileInternetConn(): Boolean {
        //Create object for ConnectivityManager class which returns network related info
        val connectivity = _context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        //If connectivity object is not null
        if (connectivity != null) {
            //Get network info - WIFI internet access
            val info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
            if (info != null) {
                //Look for whether device is currently connected to WIFI network
                if (info.isConnected) {
                    return true
                }
            }
        }
        if (connectivity != null) {
            //Get network info - MOBILE internet access
            val info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
            if (info != null) {
                //Look for whether device is currently connected to WIFI network
                if (info.isConnected) {
                    return true
                }
            }
        }
        return false
    }
}