# GlobalRate - Multi-language Currency Converter (CP3406/CP5307)

This is a functional Android utility application developed for **Assessment 1: Utility App** in CP3406/CP5307.
It has evolved from a basic template into a real-time currency


---

# GlobalRate - Multi-language Currency Converter (CP3406 / CP5307)

This is a functional Android utility application developed for **Assessment 1: Utility App** in CP3406/CP5307.
It has evolved from a basic template into a real-time currency converter featuring modern Android development practices.

---

## Key Features

- **Real-time Conversion**: Fetches the latest exchange rates for over 100+ global currencies using a REST API.
- **Interactive Search**: Enhanced currency selectors allow users to type and filter currency codes (e.g., USD, SGD, CNY) for quick access.
- **Theme Switching**: Built-in support for **Dark Mode** and **Light Mode**, accessible via the settings menu.
- **Bilingual Support**: Fully localized interface with a toggle to switch between **English** and **Simplified Chinese**.
- **Modern UI**: Built entirely with **Jetpack Compose** and **Material Design 3**.

---

## Tech Stack & Architecture

- **Architecture**: MVVM (Model-View-ViewModel) to ensure clean separation of concerns.
- **Networking**: **Retrofit** and **Gson** for fetching and parsing live exchange rate data.
- **State Management**: **StateFlow** and **Coroutines** for reactive UI updates and asynchronous operations.
- **UI Components**: Material3 Scaffold, NavigationBar, ExposedDropdownMenu, and dynamic theme switching.

---

## Key Concepts Covered

| Week | Concept                        | Implementation Status | Used In |
|------|--------------------------------|-----------------------|---------|
| 1    | Kotlin + Android Studio        | ✅ Completed           | MainActivity.kt, Data Classes |
| 2    | Jetpack Compose Layouts        | ✅ Completed           | UtilityScreen, SettingsScreen |
| 3    | Material Design 3              | ✅ Completed           | Theme.kt, Custom UI Components |
| 4    | ViewModel & State Management   | ✅ Completed           | MainViewModel, SettingsViewModel |
| 5    | Retrofit & API Integration     | ✅ Completed           | RetrofitInstance, ExchangeApiService |

---

## Getting Started

### Prerequisites
- Android Studio Ladybug (or newer)
- Active Internet connection (to fetch real-time rates)

### How to Run
1. Clone this repository to your local machine.
2. Open the project in Android Studio.
3. Sync Gradle and run the app on an emulator or physical device (API 26+).

---

## Data Source & Credits
Exchange rate data is provided by [ExchangeRate-API](https://www.exchangerate-api.com).  
This project was extended from the CP3406 Utility App Starter Template.

---

## Self-Reflection Summary
This project demonstrated the power of the MVVM architecture in managing complex UI states (like currency conversion and localization). A key challenge addressed was implementing robust data parsing and Null-Safety when handling external JSON responses, ensuring the app remains stable even during network or data inconsistencies.

---

## 📚 License
This project is developed for educational use in CP3406/CP5307 at James Cook University.