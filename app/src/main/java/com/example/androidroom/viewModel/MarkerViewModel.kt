package com.example.androidroom.viewModel

import androidx.lifecycle.*
import com.example.androidroom.database.MarkerEntity
import com.example.androidroom.repository.MarkerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarkerViewModel @Inject constructor(private val repository: MarkerRepository) : ViewModel() {

    val allMarker: LiveData<List<MarkerEntity>> = repository.allMarkers.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(marker: MarkerEntity) = viewModelScope.launch {
        repository.insert(marker)
    }
}