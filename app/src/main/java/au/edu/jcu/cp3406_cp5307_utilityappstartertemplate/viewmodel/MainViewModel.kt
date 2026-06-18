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
        // Initial load with USD as base
        fetchRates("USD")
    }

    fun fetchRates(base: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.getRates(base)
                _rates.value = response.conversionRates ?: emptyMap()
            } catch (e: Exception) {
                e.printStackTrace()
                _rates.value = emptyMap()
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Calculate converted amount
    fun calculateConversion(amount: Double, fromCurrency: String, toCurrency: String): Double {
        val currentRates = _rates.value
        if (currentRates.isEmpty()) return 0.0

        // Logic: All rates are relative to USD (assuming fetchRates("USD"))
        // 1 [From] = (1 / rates[From]) USD
        // (1 / rates[From]) USD = (1 / rates[From]) * rates[To] [To]
        val fromRate = currentRates[fromCurrency] ?: return 0.0
        val toRate = currentRates[toCurrency] ?: return 0.0

        return (amount / fromRate) * toRate
    }
}
