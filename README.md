# Random String Generator App

A simple Android app that queries a slow and unreliable content provider to fetch random strings and display them with metadata. Built in Kotlin using modern Android development practices.

---

## 📸 Screenshots

> <table>
  <tr>
    <td><img src= "https://github.com/user-attachments/assets/6c34ecf3-49a1-4649-ab1d-5ebae817366c" width=270 height=480></td>
    <td><img src="https://github.com/user-attachments/assets/7ff5c76f-5b37-4c4a-a5f1-75ff11fb8841" width=270 height=480></td>
    <td><img src="https://github.com/user-attachments/assets/ac282487-65e5-4884-b8f8-8045ade4d87b" width=270 height=480></td>
 </table>


---

## 🧩 Features

- 📏 Set desired string length
- 🔄 Fetch a random string from content provider
- 🧾 Display string with:
  - The string value
  - Its length
  - The timestamp it was created
- 🗂 Maintain list of fetched strings
- ❌ Delete individual strings
- 🧹 Delete all strings at once
- ⚠️ Error handling with proper UI feedback
- ⏳ Loading indicators to show progress during data fetch

---

## 🏗 Architecture

The app is structured using **MVVM** combined with **Clean Architecture** principles:

```
Presentation Layer (UI)
├── MainScreen (Jetpack Compose Composable)
├── ViewModel (RandomStringViewModel)
│   └── Exposes UI state using StateFlow
│
Domain Layer
├── UseCase (FetchRandomStringUseCase)
│   └── Handles business logic and abstracts data operations
│
Data Layer
└── Repository (RandomStringRepository)
    └── Queries ContentProvider and parses JSON response
```

## 🔧 Tech Stack

- **Kotlin**
- **Jetpack Compose** – Declarative UI framework
- **MVVM Architecture** – Separation of concerns
- **Clean Architecture Principles** – Use cases, repositories, models
- **StateFlow / MutableStateFlow** – Reactive state handling
- **Hilt (Dagger)** – Dependency injection
- **Coroutines** – Asynchronous programming
- **Material3** – UI components
- **JUnit5 + Mockito + Turbine** – Unit testing support


---

## 🔗 Content Provider Details

- **Authority**: `com.iav.contestdataprovider`
- **MIME-Type**: `vnd.android.cursor.dir/text`
- **URI**: `content://com.iav.contestdataprovider/text`
- **Parameter**: `ContentResolver.QUERY_ARG_LIMIT`
- **Permissions**:
  - Read: `com.iav.contestdataprovider.READ`

**Sample Response JSON:**
```json
{
  "randomText": {
    "value": "randomStringHere",
    "length": 12,
    "created": "2024-10-02T07:38:49Z"
  }
}
```

---

## 🚀 Future Enhancements

- Add swipe-to-delete support
- Persist string history with Room
- UI Tests using Compose Testing
- Better error parsing & retries

---

## 🛠 Requirements

- Android Studio Giraffe or newer
- Kotlin 1.9+
- Gradle 8.0+
- Android API 24+

---

## 📄 License

MIT License. Feel free to use and extend.
