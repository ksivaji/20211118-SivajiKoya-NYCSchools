package com.safeway.nycschools.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class SchoolDetail(
    @SerialName("dbn")
    val dbn: String? = null,
    @SerialName("num_of_sat_test_takers")
    val numOfSatTestTakers: String? = null,
    @SerialName("sat_critical_reading_avg_score")
    val satCriticalReadingAvgScore: String = "N/A",
    @SerialName("sat_math_avg_score")
    val satMathAvgScore: String? = "N/A",
    @SerialName("sat_writing_avg_score")
    val satWritingAvgScore: String? = "N/A",
    @SerialName("school_name")
    val schoolName: String? = null
)