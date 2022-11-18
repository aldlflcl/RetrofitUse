package com.example.retrofituse.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofituse.api.Image
import com.example.retrofituse.api.ImageApi
import kotlinx.coroutines.launch

class ImageViewModel : ViewModel() {

    private val _images = MutableLiveData<List<Image>>()
    val images: LiveData<List<Image>>
        get() = _images

    init {
        viewModelScope.launch {
            _images.value = ImageApi.retrofitService.getImages(30)
        }
    }
}