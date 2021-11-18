package com.safeway.nycschools.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.safeway.nycschools.repository.SchoolRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SchoolsListViewModel @Inject constructor(
    private val repository: SchoolRepository
) : ViewModel() {

    fun getSchoolsList() = repository.getSchools().cachedIn(viewModelScope)

}