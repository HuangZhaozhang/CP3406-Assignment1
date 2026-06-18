package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data.ExchangeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: ExchangeRepository) : ViewModel() {
    private val _rates = MutableStateFlow<Map<String, Double>>(emptyMap())
    val rates = _rates.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        fetchRates("USD")
    }

    fun fetchRates(base: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.getRates(base)
                // 确保数据不为 null
                _rates.value = response.conversionRates ?: emptyMap()
            } catch (e: Exception) {
                e.printStackTrace()
                _rates.value = emptyMap()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
