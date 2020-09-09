package com.mvi.imagesearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mvi.imagesearch.api.RetrofitBuilder
import com.mvi.imagesearch.cache.LiveDatabase
import com.mvi.imagesearch.constants.Constant
import com.mvi.imagesearch.model.ApiModel
import com.mvi.imagesearch.model.ViewState
import com.mvi.imagesearch.utils.Mapper
import com.mvi.imagesearch.utils.NetworkHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Repository(private val database: LiveDatabase ,private val networkHelper: NetworkHelper) {

    lateinit var searchKeyWord: String
    var pageNumber = 1
    private var isLoading: Boolean = false

    private val _list = MutableLiveData<List<ApiModel>>()
    val list: LiveData<List<ApiModel>> = _list

    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> = _viewState


   suspend fun query(query: String) {
        searchKeyWord = query
        isLoading = true
        _viewState.postValue(ViewState.Loading(true))

            if(networkHelper.isNetworkConnected()){
                val response = RetrofitBuilder.apiService.getSearchAsync(
                    pageNumber.toString(),
                    searchKeyWord,
                    Constant.CLIENT_ID
                ).await()

                if (response.results != null) {
                    val apiListToCacheList = Mapper.apiListToCacheList(response.results, searchKeyWord)
                    database.dao.insert(apiListToCacheList)
                    val localResult = database.dao.searchKeyWord(searchKeyWord)

                    _viewState.postValue(ViewState.Success(localResult))
                    isLoading = false
                }else {
                    _viewState.postValue(ViewState.Failed("Network Error"))
                    isLoading = false
                }

            }else{

                val cache = database.dao.searchKeyWord(searchKeyWord)
                _viewState.postValue(ViewState.Success(cache))
                isLoading = false
            }



    }

   suspend fun nextPage() {
        if (this::searchKeyWord.isInitialized)
            pageNumber += 1
        query(searchKeyWord)

    }
}