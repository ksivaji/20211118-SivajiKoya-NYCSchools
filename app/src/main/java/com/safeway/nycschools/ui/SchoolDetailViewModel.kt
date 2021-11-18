package com.safeway.nycschools.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safeway.nycschools.data.Result
import com.safeway.nycschools.data.model.SchoolDetail
import com.safeway.nycschools.repository.SchoolRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SchoolDetailViewModel @Inject constructor(
    private val repository: SchoolRepository,
    state: SavedStateHandle
) : ViewModel() {

    private var dbnId: String = state.get<String>("schoolDbnId").orEmpty()
    private val _satScoreDetails = MutableStateFlow(SchoolDetail())
    val satScoreDetails: StateFlow<SchoolDetail> = _satScoreDetails

    init {
        getSchoolSatScoreDetails()
    }

    private fun getSchoolSatScoreDetails() =
        viewModelScope.launch {
            val response = repository.getSchoolDetails(dbnId = dbnId)
            when (response) {
                is Result.Success -> {
                    val data = response.value
                    _satScoreDetails.value = if (data.isEmpty().not()) data[0] else SchoolDetail()
                }
                else -> {
                }
            }
        }
}