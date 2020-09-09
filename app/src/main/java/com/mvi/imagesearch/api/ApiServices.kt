package com.mvi.imagesearch.api

import androidx.lifecycle.LiveData
import com.mvi.imagesearch.model.ApiResponse
import com.mvi.imagesearch.utils.GenericApiResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search/photos?")
    fun getSearchAsync(
        @Query("page") page: String,
        @Query("query") query: String,
        @Query("client_id") id: String
    ): Deferred<ApiResponse> /*LiveData<GenericApiResponse<ApiResponse>>*/

}