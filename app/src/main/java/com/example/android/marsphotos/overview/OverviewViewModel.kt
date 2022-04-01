/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.marsphotos.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.marsphotos.network.MarsApi
import com.example.android.marsphotos.network.MarsPhoto
import com.example.android.marsphotos.overview.MarsApiStatus.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<MarsApiStatus>()
    private val _photos = MutableLiveData<List<MarsPhoto>>()
    private val _errorMsg = MutableLiveData<String>()

    // The external immutable LiveData for the request status
    val status: LiveData<MarsApiStatus> = _status
    val photos: LiveData<List<MarsPhoto>> = _photos
    val errorMsg: LiveData<String> = _errorMsg

    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */
    init {
        getMarsPhotos()
    }

    /**
     * Gets Mars photos information from the Mars API Retrofit service and updates the
     *  [List] [LiveData].
     */
    private fun getMarsPhotos() {
        viewModelScope.launch {
            try {
                _status.value = LOADING
                val response = getPhotosFromApi()
                if (response.isSuccessful) {
                    _photos.value = response.body()
                    _status.value = DONE
                } else {
                    _errorMsg.value = "Server error: ${response.code()}"
                    _status.value = ERROR
                }
            } catch (e: Exception) {
                _errorMsg.value = "Network failure: ${e.message.toString()}"
                _status.value = ERROR
            }
        }
    }

    private suspend fun getPhotosFromApi() =
        withContext(Dispatchers.IO) {
            MarsApi.retrofitService.getPhotos()
        }
}

enum class MarsApiStatus { LOADING, ERROR, DONE }
