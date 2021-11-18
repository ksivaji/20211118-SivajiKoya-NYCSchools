package com.safeway.nycschools.data.remote

import com.safeway.nycschools.data.model.School
import com.safeway.nycschools.data.model.SchoolDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NYCSchoolsService {

    companion object {
        const val ENDPOINT = "https://data.cityofnewyork.us/"
    }

    @GET("resource/{dataSetId}")
    suspend fun getSchools(@Path(value = "dataSetId") dataSetId: String): Response<List<School>>

    @GET("resource/{dataSetId}")
    suspend fun getSchoolDetails(
        @Path(value = "dataSetId") dataSetId: String,
        @Query("dbn") dbnId: String
    ): Response<List<SchoolDetail>>
}