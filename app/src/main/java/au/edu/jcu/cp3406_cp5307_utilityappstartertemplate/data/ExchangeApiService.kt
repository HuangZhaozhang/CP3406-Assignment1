package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data
import retrofit2.http.GET
interface ExchangeApiService {
    @GET("latest?base=USD") // 示例路径，请根据所选 API 修改
    suspend fun getRates(): ExchangeRateResponse
}
