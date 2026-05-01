# AeroTune - Android Music Player

<p align="center">
  <img src="app/src/main/res/drawable/ic_launcher_foreground.xml" width="120" alt="AeroTune Logo">
</p>

<p align="center">
  <strong>Ultra-Premium Android Music Player</strong>
</p>

<p align="center">
  <a href="https://github.com/senkuboy0-cyber/AeroTune-Android-Player/releases">
    <img src="https://img.shields.io/github/v/release/senkuboy0-cyber/AeroTune-Android-Player?color=00D4FF&label=Release&logo=github" alt="Release">
  </a>
  <a href="https://github.com/senkuboy0-cyber/AeroTune-Android-Player/blob/main/LICENSE">
    <img src="https://img.shields.io/github/license/senkuboy0-cyber/AeroTune-Android-Player?color=00D4FF&label=License" alt="License">
  </a>
  <a href="https://github.com/senkuboy0-cyber/AeroTune-Android-Player/actions">
    <img src="https://img.shields.io/github/actions/workflow/status/senkuboy0-cyber/AeroTune-Android-Player/build-release.yml?color=00D4FF&label=Build" alt="Build">
  </a>
  <a href="https://kotlinlang.org/">
    <img src="https://img.shields.io/badge/Kotlin-2.1.0-7F52FF?logo=kotlin&logoColor=7F52FF" alt="Kotlin">
  </a>
  <a href="https://developer.android.com/compose">
    <img src="https://img.shields.io/badge/Jetpack%20Compose-1.9-00D4FF?logo=jetpackcompose&logoColor=00D4FF" alt="Jetpack Compose">
  </a>
</p>

---

## Features

- **Local Audio Scanning**: Automatically scans and displays all audio files from your device
- **Playback Controls**: Full play, pause, skip, previous, shuffle, and repeat functionality
- **Now Playing Screen**: Beautiful album art display with progress slider
- **Ultra-Premium Dark Mode**: Glassmorphism-inspired UI with cyan accent color scheme
- **Mini Player**: Persistent bottom player bar for quick controls
- **Smooth Animations**: Fluid transitions and playback state animations

## Screenshots

| Library | Now Playing |
|---------|-------------|
| ![Library](docs/screenshots/library.png) | ![Now Playing](docs/screenshots/now_playing.png) |

## Tech Stack

| Category | Technology |
|----------|------------|
| **Language** | Kotlin 2.1.0 |
| **UI Framework** | Jetpack Compose + Material 3 |
| **Architecture** | MVVM + Clean Architecture |
| **DI** | Hilt (Dagger) |
| **Media Playback** | AndroidX Media3 (ExoPlayer) |
| **Image Loading** | Coil |
| **Async** | Kotlin Coroutines + Flow |
| **Build System** | Gradle 8.11.1 |
| **Min SDK** | 26 (Android 8.0) |
| **Target SDK** | 35 (Android 15) |

## Project Structure

```
com.aerotune.player/
├── data/
│   ├── model/           # Data models (AudioTrack, PlaybackState)
│   └── repository/      # Data repositories (AudioRepository)
├── di/                  # Hilt dependency injection modules
├── service/             # Media playback service (MediaSessionService)
├── ui/
│   ├── screens/         # Compose screens (MainScreen)
│   ├── components/      # Reusable UI components
│   └── theme/          # Material theme & colors
└── MainActivity.kt     # Entry point
```

## Getting Started

### Prerequisites

- Android Studio Hedgehog (2024.1.1) or later
- JDK 17 or later
- Android SDK with API 35

### Build

```bash
# Clone the repository
git clone https://github.com/senkuboy0-cyber/AeroTune-Android-Player.git

# Navigate to project directory
cd AeroTune-Android-Player

# Build debug APK
./gradlew assembleDebug

# Build release APK (requires signing config)
./gradlew assembleRelease
```

### Install

```bash
# Install debug APK on connected device
adb install app/build/outputs/apk/debug/app-debug.apk
```

## Dependencies

| Dependency | Version | Purpose |
|-----------|---------|----------|
| androidx.media3 | 1.5.1 | Audio playback with ExoPlayer |
| androidx.compose | BOM 2024.12.01 | Modern declarative UI |
| androidx.material3 | 1.3.1 | Material Design 3 components |
| androidx.navigation | 2.8.5 | In-app navigation |
| com.google.dagger | 2.56.1 | Dependency injection |
| io.coil-kt | 2.7.0 | Image loading |
| org.jetbrains.kotlinx | 1.9.0 | Coroutines |

## CI/CD

This project uses GitHub Actions for automated builds:

- **Build Trigger**: Every push to `main` branch
- **Artifacts**: Debug APK automatically generated and released
- **Workflow**: `.github/workflows/build-release.yml`

## Permissions

```xml
<uses-permission android:name="android.permission.READ_MEDIA_AUDIO" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE_MEDIA_PLAYBACK" />
```

## Design System

### Color Palette

| Name | Hex Code | Usage |
|------|----------|-------|
| Primary | `#00D4FF` | Accent color, buttons, progress |
| Background | `#0D0D1A` | Main background |
| Surface | `#121225` | Cards, elevated surfaces |
| On Surface | `#E3E3E8` | Primary text |
| On Surface Variant | `#B8B8C7` | Secondary text |

### Typography

Uses system default font with Material 3 typography scale:
- Display: Headlines and large titles
- Body: Content text
- Label: Timestamps, metadata

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- [Android Developers](https://developer.android.com/) - Official Android documentation
- [Jetpack Compose](https://developer.android.com/compose) - Modern Android UI toolkit
- [Media3](https://developer.android.com/media/media3) - ExoPlayer successor
- [Material Design](https://m3.material.io/) - Design system guidelines

---

<p align="center">
  Made with Kotlin & Jetpack Compose
</p>
