package com.safeway.nycschools.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.safeway.nycschools.data.model.School
import com.safeway.nycschools.data.remote.BaseDataSource
import com.safeway.nycschools.data.remote.NYCSchoolsService
import com.safeway.nycschools.paging.SchoolPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SchoolRepository @Inject constructor(private val nycSchoolsService: NYCSchoolsService) :
    BaseDataSource() {

    //    suspend fun getSchools (dataSetId: String = "s3k6-pzi2") = nycSchoolsService.getSchools(dataSetId)

    fun getSchools(): Flow<PagingData<School>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { SchoolPagingSource(nycSchoolsService = nycSchoolsService) }
        ).flow
    }

    suspend fun getSchoolDetails(dataSetId: String = "f9bf-2cp4", dbnId: String) =
        getResult { nycSchoolsService.getSchoolDetails(dataSetId, dbnId) }


    companion object {
        const val NETWORK_PAGE_SIZE = 50
    }

}