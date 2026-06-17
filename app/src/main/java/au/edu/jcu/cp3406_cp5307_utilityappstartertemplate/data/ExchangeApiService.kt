package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data
import retrofit2.http.GET
import retrofit2.http.Path
interface ExchangeApiService {
    // 假设使用 ExchangeRate-API，需替换为你申请的 API Key
    @GET("v6/YOUR_API_KEY/latest/{base}")
    suspend fun getLatestRates(@Path("base") base: String): ExchangeRateResponse
}
