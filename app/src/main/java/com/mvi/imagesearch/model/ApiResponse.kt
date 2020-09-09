package com.mvi.imagesearch.model

import com.squareup.moshi.Json

data class ApiResponse(


	@Json(name="results")
	val results: List<Response>? = null
)
