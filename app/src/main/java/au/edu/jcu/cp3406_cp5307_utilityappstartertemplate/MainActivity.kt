package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data.ExchangeRepository
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data.RetrofitInstance
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui.theme.CP3406_CP5307UtilityAppStarterTemplateTheme
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.viewmodel.Language
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.viewmodel.MainViewModel
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.viewmodel.MyViewModelFactory
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.viewmodel.SettingsViewModel
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val settingsViewModel: SettingsViewModel = viewModel()
            val isDarkMode by settingsViewModel.isDarkMode.collectAsState()

            CP3406_CP5307UtilityAppStarterTemplateTheme(darkTheme = isDarkMode) {
                UtilityApp(settingsViewModel)
            }
        }
    }
}

@Composable
fun UtilityApp(settingsViewModel: SettingsViewModel) {
    var selectedTab by remember { mutableStateOf("Utility") }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text(settingsViewModel.getString("Utility", "工具")) },
                    selected = selectedTab == "Utility",
                    onClick = { selectedTab = "Utility" }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Settings, contentDescription = null) },
                    label = { Text(settingsViewModel.getString("Settings", "设置")) },
                    selected = selectedTab == "Settings",
                    onClick = { selectedTab = "Settings" }
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedTab) {
                "Utility" -> UtilityScreen(settingsViewModel)
                "Settings" -> SettingsScreen(settingsViewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UtilityScreen(
    settingsViewModel: SettingsViewModel,
    viewModel: MainViewModel = viewModel(
        factory = MyViewModelFactory(ExchangeRepository(RetrofitInstance.api))
    )
) {
    val rates by viewModel.rates.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    var amountStr by remember { mutableStateOf("1") }
    var fromCurrency by remember { mutableStateOf("SGD") }
    var toCurrency by remember { mutableStateOf("CNY") }

    val currencyList = remember(rates) { rates.keys.toList().sorted() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = settingsViewModel.getString("Currency Converter", "汇率转换器"),
            style = MaterialTheme.typography.headlineMedium
        )

        if (isLoading) {
            Text(settingsViewModel.getString("Syncing latest exchange rates...", "正在同步最新汇率..."))
        } else if (currencyList.isEmpty()) {
            Text(settingsViewModel.getString("Unable to fetch exchange rates. Please check your network.", "无法获取汇率数据，请检查网络。"))
        } else {
            // Main Amount Input
            OutlinedTextField(
                value = amountStr,
                onValueChange = { newValue ->
                    // Only allow numeric/decimal input
                    if (newValue.isEmpty() || newValue.toDoubleOrNull() != null || newValue == ".") {
                        amountStr = newValue
                    }
                },
                label = { Text(settingsViewModel.getString("Input Amount", "输入金额")) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true
            )

            // Select currency (From)
            CurrencySelector(
                label = settingsViewModel.getString("From (Original Currency)", "从 (原始币种)"),
                selectedCurrency = fromCurrency,
                currencyList = currencyList,
                onCurrencySelected = { fromCurrency = it }
            )

            // Select currency (To)
            CurrencySelector(
                label = settingsViewModel.getString("To (Target Currency)", "到 (目标币种)"),
                selectedCurrency = toCurrency,
                currencyList = currencyList,
                onCurrencySelected = { toCurrency = it }
            )

            // Calculation result
            val amount = amountStr.toDoubleOrNull() ?: 0.0
            val result = viewModel.calculateConversion(amount, fromCurrency, toCurrency)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = settingsViewModel.getString("Conversion Result", "转换结果"),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = String.format(Locale.getDefault(), "%.2f %s = %.2f %s", amount, fromCurrency, result, toCurrency),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp
                    ),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencySelector(
    label: String,
    selectedCurrency: String,
    currencyList: List<String>,
    onCurrencySelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    // Internal state for the text field to allow "searching"
    var searchQuery by remember(selectedCurrency) { mutableStateOf(selectedCurrency) }

    val filteredList = remember(searchQuery, currencyList) {
        if (expanded) {
            currencyList.filter { it.contains(searchQuery, ignoreCase = true) }
        } else {
            currencyList
        }
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { 
                searchQuery = it.uppercase()
                expanded = true
            },
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryEditable, true)
                .fillMaxWidth()
        )

        if (filteredList.isNotEmpty()) {
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { 
                    expanded = false
                    searchQuery = selectedCurrency // Reset to selected if nothing picked
                }
            ) {
                filteredList.forEach { currency ->
                    DropdownMenuItem(
                        text = { Text(currency) },
                        onClick = {
                            onCurrencySelected(currency)
                            searchQuery = currency
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SettingsScreen(settingsViewModel: SettingsViewModel) {
    val isDarkMode by settingsViewModel.isDarkMode.collectAsState()
    val currentLang by settingsViewModel.language.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = settingsViewModel.getString("Settings", "设置"),
            style = MaterialTheme.typography.headlineMedium
        )

        HorizontalDivider()

        // Dark Mode Toggle
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = settingsViewModel.getString("Dark Mode", "深色模式"),
                style = MaterialTheme.typography.bodyLarge
            )
            Switch(
                checked = isDarkMode,
                onCheckedChange = { settingsViewModel.toggleDarkMode(it) }
            )
        }

        HorizontalDivider()

        // Language Selection
        Text(
            text = settingsViewModel.getString("Language", "语言"),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )

        LanguageOption(
            label = "English",
            selected = currentLang == Language.ENGLISH,
            onClick = { settingsViewModel.setLanguage(Language.ENGLISH) }
        )

        LanguageOption(
            label = "中文",
            selected = currentLang == Language.CHINESE,
            onClick = { settingsViewModel.setLanguage(Language.CHINESE) }
        )
    }
}

@Composable
fun LanguageOption(label: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = selected, onClick = onClick)
        Spacer(modifier = Modifier.size(8.dp))
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
    }
}
