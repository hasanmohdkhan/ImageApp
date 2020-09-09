package com.mvi.imagesearch.utils

import com.mvi.imagesearch.model.ApiModel
import com.mvi.imagesearch.model.Response

object Mapper {

    fun apiListToCacheList(apiList: List<Response>, query: String ): List<ApiModel> {

        val list : ArrayList<ApiModel> = ArrayList()

        for(i in apiList){
           if(i.id != null){
               list.add(ApiModel(id = i.id,
                   keyword = query ,
                   small = i.urls?.small ,
                   thumb =i.urls?.small ,
                   download = i.links?.download))
           }
        }

        return list
    }


}