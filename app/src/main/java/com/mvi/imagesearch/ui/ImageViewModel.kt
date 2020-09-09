package com.mvi.imagesearch.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvi.imagesearch.Repository
import com.mvi.imagesearch.api.RetrofitBuilder
import com.mvi.imagesearch.constants.Constant.Companion.CLIENT_ID
import com.mvi.imagesearch.model.ApiModel
import com.mvi.imagesearch.model.ViewState
import com.mvi.imagesearch.utils.Mapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ImageViewModel(private val repository: Repository) : ViewModel() {

    lateinit var searchKeyWord: String

    val list: LiveData<List<ApiModel>> = repository.list

    val viewState: LiveData<ViewState> = repository.viewState

    fun query(query: String) {
        searchKeyWord = query
        viewModelScope.launch(Dispatchers.IO){
            repository.query(query)
        }
    }

    fun nextPage() {
        if (this::searchKeyWord.isInitialized)
        viewModelScope.launch(Dispatchers.IO){
            repository.nextPage()
        }

    }


}