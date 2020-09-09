package com.mvi.imagesearch.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.mvi.imagesearch.utils.LiveDataCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitBuilder {

    const val BASE_URL: String = "https://api.unsplash.com/"

    val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            /*.addCallAdapterFactory(LiveDataCallAdapterFactory())*/
            .addCallAdapterFactory(CoroutineCallAdapterFactory())

    }

    val apiService: ApiService by lazy {
        retrofitBuilder.build().create(ApiService::class.java)
    }


}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()