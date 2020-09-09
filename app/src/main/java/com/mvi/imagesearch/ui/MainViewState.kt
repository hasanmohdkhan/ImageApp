package com.mvi.imagesearch.ui

import com.mvi.imagesearch.model.Response

data class MainViewState(
    var blogPosts: List<Response>? = null

)
