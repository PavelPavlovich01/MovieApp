package com.example.movieapp.data.network

import java.io.IOException

class ApiException(override val message: String) : IOException(message)