package com.safeway.nycschools.data.remote.interceptors

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import com.safeway.nycschools.R
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConnectivityInterceptor @Inject constructor(
    @ApplicationContext private val context: Context,
    private val connectivityManager: ConnectivityManager
) : Interceptor, ConnectivityManager.NetworkCallback() {

    private var online = false

    init {
        if (Build.VERSION.SDK_INT >= 24) {
            connectivityManager.registerDefaultNetworkCallback(this)
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        if (Build.VERSION.SDK_INT < 24) {
            online = connectivityManager.activeNetworkInfo?.isConnected ?: false
        }

        if (online) {
            return chain.proceed(chain.request())
        } else {
            throw NoConnectivityException()
        }
    }

    override fun onCapabilitiesChanged(
        network: Network,
        capabilities: NetworkCapabilities
    ) {
        online = capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }


    inner class NoConnectivityException : IOException() {
        override val message: String
            get() = context.getString(R.string.error_no_network)
    }
}