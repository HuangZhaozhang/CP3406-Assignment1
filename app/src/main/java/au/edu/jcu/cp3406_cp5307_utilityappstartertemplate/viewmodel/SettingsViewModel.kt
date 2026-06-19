package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class Language(val labelEn: String, val labelZh: String) {
    ENGLISH("English", "英文"),
    CHINESE("Chinese", "中文")
}

class SettingsViewModel : ViewModel() {
    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode = _isDarkMode.asStateFlow()

    private val _language = MutableStateFlow(Language.ENGLISH)
    val language = _language.asStateFlow()

    fun toggleDarkMode(enabled: Boolean) {
        _isDarkMode.value = enabled
    }

    fun setLanguage(lang: Language) {
        _language.value = lang
    }

    // String helper based on current language
    fun getString(en: String, zh: String): String {
        return if (_language.value == Language.CHINESE) zh else en
    }
}
