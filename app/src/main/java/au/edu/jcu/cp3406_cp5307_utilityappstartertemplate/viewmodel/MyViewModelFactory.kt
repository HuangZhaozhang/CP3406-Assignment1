package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.viewmodel // 请确保包名与你实际一致

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data.ExchangeRepository

class MyViewModelFactory(private val repository: ExchangeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}