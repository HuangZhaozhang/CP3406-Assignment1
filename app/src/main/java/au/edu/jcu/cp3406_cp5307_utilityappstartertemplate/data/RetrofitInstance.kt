package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://v6.exchangerate-api.com/"

    val api: ExchangeApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // 使用 Gson 解析 JSON
            .build()
            .create(ExchangeApiService::class.java)
    }
}
