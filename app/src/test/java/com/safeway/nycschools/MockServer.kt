package com.safeway.nycschools

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest

class MockServer {
    companion object {
        val server = MockWebServer()

        fun dispatcher(): Dispatcher {
            return object : Dispatcher() {
                override fun dispatch(request: RecordedRequest): MockResponse {
                    return when (request.path) {
                        //TODO: store response json in assets and load from there
                        "resource/s3k6-pzi2" -> MockResponse().setResponseCode(200).setBody(
                            """
                            [
                              {
                                "dbn": "02M260",
                                "school_name": "Clinton School Writers & Artists, M.S. 260"
                              },
                              {
                                "dbn": "21K728",
                                "school_name": "Liberation Diploma Plus High School"
                              }
                            ]
                        """.trimIndent()
                        )
                        "resource/f9bf-2cp4.json?dbn=31R080" -> MockResponse().setResponseCode(200)
                            .setBody(
                                """
                            [
                                {
                                    "dbn": "31R080",
                                    "school_name": "THE MICHAEL J. PETRIDES SCHOOL",
                                    "num_of_sat_test_takers": "107",
                                    "sat_critical_reading_avg_score": "472",
                                    "sat_math_avg_score": "488",
                                    "sat_writing_avg_score": "466"
                                }
                            ]
                        """.trimIndent()
                            )
                        else -> MockResponse().setResponseCode(404)
                    }
                }
            }
        }
    }

}