package com.canhbbaochayrung.services

import com.canhbbaochayrung.utils.CSVRequest
import com.google.gson.JsonObject
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * https://firms.modaps.eosdis.nasa.gov/api/country/csv/d7c203ddb2ee38970268de4938bdefdc/VIIRS_SNPP_NRT/VNM/2
 */
interface NasaService {
    @GET("country/csv/d7c203ddb2ee38970268de4938bdefdc/VIIRS_SNPP_NRT/VNM/{range}/{date}")
    @CSVRequest
    suspend fun getData(@Path("range") range: Int,@Path("date") date: String = ""): JsonObject
}