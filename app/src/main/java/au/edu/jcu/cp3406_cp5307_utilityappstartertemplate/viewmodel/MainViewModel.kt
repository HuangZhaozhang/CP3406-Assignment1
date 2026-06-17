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

    init {
        viewModelScope.launch {
            _rates.value = repository.getExchangeRates().rates
        }
    }
}