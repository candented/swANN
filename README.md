# PulseBridge MVP

## Overview

PulseBridge is a sophisticated mobile audio visualizer for Android. It brings the classic MilkDrop experience to modern devices by capturing system audio in real-time and rendering stunning visualizations using the `libprojectM` engine. The application is designed to be passive, automatically detecting and visualizing any audio playing on the device.

This MVP is built on a clean, modular architecture that ensures stability and extensibility. It respects user privacy by using the `MediaProjection` API for audio capture, which requires user consent and does not involve microphone access.

## Build Instructions

### Prerequisites

*   Android Studio (latest version recommended)
*   Android NDK (installed via the Android Studio SDK Manager)
*   A physical Android device or emulator running Android 10 (API level 29) or higher.

### Building and Running

1.  **Clone the repository:**
    ```bash
    git clone <repository-url>
    ```
2.  **Open the project in Android Studio.**
3.  **Sync Gradle:** Allow Android Studio to download all required dependencies.
4.  **Build the project:** Use the `Build > Make Project` menu item.
5.  **Run the application:** Select a target device and run the app from Android Studio.

## Runtime Behavior

The application's architecture is designed for a one-way data flow, ensuring modularity and clarity:

1.  **Audio Capture:** The `AudioCaptureService` runs in the background, using the `MediaProjection` API to capture system audio.
2.  **Broadcast:** The raw PCM audio data is broadcast to the main application using a `LocalBroadcastManager`.
3.  **UI and Control:** The `MainActivity` receives the audio data and manages the `GLSurfaceView` for rendering. It also handles user interactions, such as preset selection.
4.  **Rendering:** The `PulseBridgeRenderer` receives the audio data and passes it to the `libprojectM` engine via a JNI bridge.
5.  **Visualization:** The `libprojectM` engine processes the audio data and generates the visualization, which is then rendered on the screen.

## Performance and Preset Tiering

While not fully implemented, the architecture is designed to support performance tiering for presets (e.g., low, medium, high). This would allow the application to adjust the complexity of the visualizations based on the device's capabilities, ensuring a smooth user experience across a wide range of devices.
