package com.phantasmist.dineout.Remote

import com.phantasmist.dineout.Utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class FourSquareService {
    companion object Factory {

        fun makeFourSquareService(isDebug: Boolean): FourSquareApiInterface {
            val okHttpClient = makeOkHttpclient(makeLoggingInterceptor(isDebug))
            return makeFourSquareService(okHttpClient)
        }

        private fun makeFourSquareService(okHttpClient: OkHttpClient): FourSquareApiInterface {
            val retrofit = Retrofit.Builder()
                    .baseUrl(Constants.API_URL)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            return retrofit.create(FourSquareApiInterface::class.java)
        }

        /**
         * this function returns an instance of ok http client
         * */
        private fun makeOkHttpclient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
            return OkHttpClient.Builder()
                    .addInterceptor(httpLoggingInterceptor)
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .build()
        }

        /**
         * ENable logging when we are using debug build
         * */
        private fun makeLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
            val logging = HttpLoggingInterceptor()
            logging.level = if (isDebug) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
            return logging
        }

    }
}