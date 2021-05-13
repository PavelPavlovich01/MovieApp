package com.example.movieapp.data.network

import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module{
    factory { AuthInterceptor() }
    factory { RetrofitClient.provideOkHttpClient(get()) }
    factory {  RetrofitClient.provideMovieApi(get()) }
    single {  RetrofitClient.provideRetrofit(get()) }
}

class RetrofitClient {
    companion object{
        fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create()).build()
        }

        fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
            return OkHttpClient().newBuilder().connectTimeout(30, TimeUnit.SECONDS).addInterceptor(authInterceptor).build()
        }

        fun provideMovieApi(retrofit: Retrofit): MovieApi = retrofit.create(MovieApi::class.java)
    }
}
