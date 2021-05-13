package com.example.movieapp.data.network

import android.accounts.NetworkErrorException
import java.io.IOException
import java.lang.RuntimeException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ApiException(override val message: String) : IOException(message)
class NoInternetExсeption(override val message: String) : NetworkErrorException(message)
class DataBaseException(override val message: String) : RuntimeException(message)