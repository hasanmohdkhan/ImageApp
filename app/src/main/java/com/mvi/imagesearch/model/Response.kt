package com.mvi.imagesearch.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Response(

    @Json(name = "urls")
    val urls: Urls? = null,

    @Json(name = "links")
    val links: Links? = null,

    @Json(name = "id")
    val id: String? = null
) : Parcelable


@Parcelize
data class Urls(

    @Json(name = "small")
    val small: String? = null,

    @Json(name = "thumb")
    val thumb: String? = null

) : Parcelable

@Parcelize
data class Links(
    @Json(name = "download")
    val download: String? = null
) : Parcelable
