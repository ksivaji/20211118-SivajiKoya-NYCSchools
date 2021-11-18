package com.safeway.nycschools.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class School(
    @SerialName("building_code")
    val buildingCode: String? = null,
    @SerialName("census_tract")
    val censusTract: String? = null,
    @SerialName("city")
    val city: String? = null,
    @SerialName("community_board")
    val communityBoard: String? = null,
    @SerialName("council_district")
    val councilDistrict: String? = null,
    @SerialName("dbn")
    val dbn: String? = null,
    @SerialName("end_time")
    val endTime: String? = null,
    @SerialName("fax_number")
    val faxNumber: String? = null,
    @SerialName("latitude")
    val latitude: String? = null,
    @SerialName("longitude")
    val longitude: String? = null,
    @SerialName("phone_number")
    val phoneNumber: String? = null,
    @SerialName("primary_address_line_1")
    val primaryAddressLine1: String? = null,
    @SerialName("school_email")
    val schoolEmail: String? = null,
    @SerialName("school_name")
    val schoolName: String? = null,
    @SerialName("start_time")
    val startTime: String? = null,
    @SerialName("state_code")
    val stateCode: String? = null,
    @SerialName("website")
    val website: String? = null,
    @SerialName("zip")
    val zip: String? = null
)