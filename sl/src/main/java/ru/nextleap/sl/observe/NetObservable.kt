package ru.nextleap.sl.observe

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import ru.nextleap.common.ApplicationUtils
import ru.nextleap.common.Connectivity
import ru.nextleap.sl.provider.ApplicationProvider
import ru.nextleap.sl.provider.IObservableSubscriber


class NetObservable : AbsObservable() {
    companion object {
        const val NAME = "NetObservable"
    }

    private var broadcastReceiver: BroadcastReceiver? = null
    private var callback: ConnectivityManager.NetworkCallback? = null

    init {
        val context = ApplicationProvider.appContext
        if (ApplicationUtils.hasLollipop()) {
            val builder = NetworkRequest.Builder()
            builder.addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            builder.addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            builder.addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            builder.addTransportType(NetworkCapabilities.TRANSPORT_VPN)

            callback = object : ConnectivityManager.NetworkCallback() {

                override fun onAvailable(network: Network) {
                    super.onAvailable(network)

                    onChange(true)
                }

                override fun onLost(network: Network) {
                    super.onLost(network)

                    onChange(false)
                }
            }

            val connectivityManager =
                ApplicationUtils.getSystemService<ConnectivityManager>(
                    context,
                    Context.CONNECTIVITY_SERVICE
                )
            connectivityManager.registerNetworkCallback(
                builder.build(),
                callback!!
            )
        } else {
            val intentFilter = IntentFilter()
            intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
            broadcastReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    if (Connectivity.isNetworkConnected(context)) {
                        onChange(true)
                    } else {
                        onChange(false)
                    }
                }
            }
            context.registerReceiver(broadcastReceiver, intentFilter)
        }
    }


    override fun getName(): String {
        return NAME
    }

    override fun addObserver(subscriber: IObservableSubscriber) {
        super.addObserver(subscriber)

        val context = ApplicationProvider.appContext
        if (Connectivity.isNetworkConnected(context)) {
            onChange(true)
        } else {
            onChange(false)
        }
    }

    override fun stop() {
        super.stop()

        if (broadcastReceiver != null) {
            val context = ApplicationProvider.appContext
            context.unregisterReceiver(broadcastReceiver)
        }

        if (callback != null) {
            if (ApplicationUtils.hasLollipop()) {
                val context = ApplicationProvider.appContext
                val connectivityManager = Connectivity.getConnectivityManager(context)
                connectivityManager.unregisterNetworkCallback(callback!!)
            }
        }
    }

}
