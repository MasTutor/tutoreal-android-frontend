package com.mastutor.tutoreal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mastutor.tutoreal.data.remote.TutorItem
import com.mastutor.tutoreal.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    fun searchTutors(
        specialization: String = "",
        category: String? = null
    ): Flow<PagingData<TutorItem>> =
        repository.searchTutors(specialization, category).cachedIn(viewModelScope)
}