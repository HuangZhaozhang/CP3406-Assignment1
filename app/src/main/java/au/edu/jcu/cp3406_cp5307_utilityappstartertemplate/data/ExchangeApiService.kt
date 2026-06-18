package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data
import retrofit2.http.GET
import retrofit2.http.Path
interface ExchangeApiService {
    // Assuming use of ExchangeRate-API
    @GET("v6/latest/{base}")
    suspend fun getLatestRates(@Path("base") base: String): ExchangeRateResponse
}
