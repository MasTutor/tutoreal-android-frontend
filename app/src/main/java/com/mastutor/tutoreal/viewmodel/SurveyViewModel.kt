package com.mastutor.tutoreal.viewmodel

import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.mastutor.tutoreal.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SurveyViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _answers = mutableStateListOf(
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3
    )
    val answers: SnapshotStateList<Int> get() = _answers
    fun addAnswers(answer: MutableIntState, currentIndex: MutableIntState) {
        _answers[currentIndex.intValue] = answer.intValue
    }
}