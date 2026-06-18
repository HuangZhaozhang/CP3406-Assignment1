package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data

import com.google.gson.annotations.SerializedName

data class ExchangeRateResponse(
    @SerializedName("result")
    val result: String?,
    @SerializedName("base_code")
    val baseCode: String?,
    @SerializedName("rates")
    val conversionRates: Map<String, Double>?
)
