package ru.nextleap.common;

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build


class Connectivity {
    companion object {
        /**
         * Indicates whether network connectivity exists and it is possible to establish
         * connections and pass data.
         *
         * Always call this before attempting to perform data transactions.
         *
         * @return `true` if network connectivity exists, `false` otherwise.
         */
        @JvmStatic
        fun isNetworkConnected(context: Context): Boolean {
            val connectivityManager = getConnectivityManager(context)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val nw = connectivityManager.activeNetwork ?: return false
                val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
                return when {
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    //for other device how are able to connect with Ethernet
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    //for check internet over Bluetooth
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                    else -> false
                }
            } else {
                val nwInfo = connectivityManager.activeNetworkInfo ?: return false
                return nwInfo.isConnected
            }
        }

        /**
         * Returns details about the currently active default data network. When
         * connected, this network is the default route for outgoing connections.
         * You should always check [NetworkInfo.isConnected] before initiating
         * network traffic. This may return `null` when there is no default
         * network.
         *
         * This method requires the caller to hold the permission
         * [android.Manifest.permission.ACCESS_NETWORK_STATE].
         *
         * @return a [NetworkInfo] object for the current default network
         * or `null` if no default network is currently active
         */
        @JvmStatic
        fun getActiveNetworkInfo(context: Context): NetworkInfo? {
            val connectivityManager = getConnectivityManager(context)
            return connectivityManager.activeNetworkInfo
        }

        @JvmStatic
        fun getConnectivityManager(context: Context): ConnectivityManager {
            return ApplicationUtils.getSystemService(context, Context.CONNECTIVITY_SERVICE)
        }

    }

}
