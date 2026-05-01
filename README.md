# AeroTune - Cyberpunk Music Player

Ultra-premium Android music player with Cyberpunk theme and glassmorphism UI.

## Features

### UI/UX
- Cyberpunk Dark Theme with Neon Cyan and Pink accents
- Glassmorphism (frosted glass) components
- Animated splash screen with particles and glow effects
- Smooth transitions and micro-animations
- Full-screen player with pulsing album art
- Mini player bar with glass effect
- Staggered list item animations

### Animations
- Pulsing logo on splash
- Rotating gradient ring
- Floating background particles
- Scale animations on tracks
- Smooth player transitions
- Loading indicator dots
- Glow effects on player

### Permissions
- Runtime permission handling for audio access
- Android 13+ (Tiramisu) support
- Notification permission

## Tech Stack
- Kotlin 2.1
- Jetpack Compose
- Material 3
- Media3 (ExoPlayer)
- Hilt DI
- Coil Image Loading

## Color Palette
- Background: #060910 (Deep Dark)
- Primary Accent: #00E5FF (Neon Cyan)
- Secondary Accent: #FF1744 (Neon Pink)
- Tertiary: #9C27B0 (Neon Purple)
- Glass: 10% White overlay

## Build

```bash
./gradlew assembleDebug
```

## Screens

1. **Splash Screen** - Animated logo with particles
2. **Library** - Track list with glass cards
3. **Mini Player** - Bottom bar player
4. **Full Player** - Complete playback controls

## Permissions
- READ_MEDIA_AUDIO (Android 13+)
- READ_EXTERNAL_STORAGE (Android 12 and below)
- POST_NOTIFICATIONS (Android 13+)
