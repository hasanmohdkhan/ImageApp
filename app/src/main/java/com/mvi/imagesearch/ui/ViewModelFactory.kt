package com.mvi.imagesearch.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mvi.imagesearch.Repository

class ViewModelFactory(private val repository: Repository ): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ImageViewModel::class.java)) {
            return ImageViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }

}
