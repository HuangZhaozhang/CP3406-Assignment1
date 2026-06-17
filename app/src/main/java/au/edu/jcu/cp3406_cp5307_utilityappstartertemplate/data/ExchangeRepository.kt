package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data
class ExchangeRepository(private val apiService: ExchangeApiService) {
    suspend fun getExchangeRates() = apiService.getRates()
}

