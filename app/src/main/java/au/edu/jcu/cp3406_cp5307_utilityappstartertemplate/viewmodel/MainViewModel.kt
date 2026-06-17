package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.viewmodel
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data.ExchangeRepository

class MainViewModel(private val repository: ExchangeRepository) : ViewModel() {
    private val _rates = MutableStateFlow<Map<String, Double>>(emptyMap())
    val rates = _rates.asStateFlow()
    // 存储加载状态（可选，用于在 UI 显示加载中）
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        // 在 ViewModel 初始化时获取数据
        fetchRates("USD")
    }

    fun fetchRates(base: String) {

        viewModelScope.launch {
            _isLoading.value = true
            try {
                // 调用 Repository 获取网络数据
                val response = repository.fetchRates(base)
                _rates.value = repository.getRates(base).conversion_rates
            } catch (e: Exception) {
                // 处理错误情况
                e.printStackTrace()
            }finally {
                _isLoading.value = false
            }
        }
    }
}