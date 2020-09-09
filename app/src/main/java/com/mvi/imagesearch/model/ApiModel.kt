package com.mvi.imagesearch.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Api")
data class ApiModel(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val keyword: String,
    val small: String? = null,
    val thumb: String? = null,
    val download: String? = null
){

    override fun toString(): String {
        return "ApiModel(id= $id, keyword= '$keyword', small= $small, thumb= $thumb, download= $download)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ApiModel

        if (id != other.id) return false
        if (keyword != other.keyword) return false
        if (small != other.small) return false
        if (thumb != other.thumb) return false
        if (download != other.download) return false

        return true
    }



}