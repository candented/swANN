# Project PulseBridge: Handoff Document

## 1. Project Overview

PulseBridge is a real-time audio visualization application for Android. It captures system-wide audio output, processes it using the `libprojectM` visualization engine, and renders the output to the screen using OpenGL ES. The project is currently in a "functioning MVP" state, with a solid foundation for future development.

## 2. Architecture

The application is divided into several key components:

*   **`AudioCaptureService`**: A background service that uses `MediaProjection` to capture all system audio. It then broadcasts the raw PCM audio data to the main application.
*   **`MainActivity`**: The main entry point of the application. It manages the `GLSurfaceView` for rendering, handles user input (preset selection, etc.), and receives audio data from the `AudioCaptureService`.
*   **`PulseBridgeRenderer`**: An implementation of `GLSurfaceView.Renderer`. It receives audio data from the `MainActivity` and passes it to the `libprojectM` engine via the JNI bridge. It also calls the `onDrawFrame` method of the engine to render the visualization.
*   **`PulseBridge` (Java class)**: This class defines the native methods that are implemented in the JNI layer. It serves as the bridge between the Java/Kotlin code and the C++ code of `libprojectM`.
*   **JNI Bridge (`pulsebridge.cpp`)**: The C++ code that implements the native methods defined in the `PulseBridge` class. It initializes `libprojectM`, passes audio data to it, and calls its rendering functions.
*   **`libprojectM`**: The core visualization engine. It takes PCM audio data and generates mesmerizing visuals.

## 3. Current Features

*   **Real-time Audio Visualization**: Visualizes any audio playing on the device.
*   **System Audio Capture**: Captures audio from any app (e.g., Spotify, YouTube, etc.).
*   **Preset Management**:
    *   Load a preset from the device's storage.
    *   Clear the current preset.
    *   Cycle through presets in the same directory.
*   **Basic UI**:
    *   A "Select Preset" button to open a file picker.
    *   "Clear Preset", "Previous", and "Next" buttons for preset control.
    *   A "Settings" button that opens a settings screen.
*   **Settings Screen**: A basic settings screen with placeholders for:
    *   Beat sensitivity.
    *   Preset duration.
    *   Automatic visualization activation.

## 4. Build and Run Instructions

To build and run the project, you will need:

*   Android Studio
*   The Android NDK

1.  Clone the repository.
2.  Open the project in Android Studio.
3.  Connect an Android device.
4.  Run the app.

The project uses `cmake` to build the native code. The `CMakeLists.txt` file is located in the `app/src/main/cpp` directory.

## 5. Next Steps & Future Development

The following is a list of suggested next steps for the project, based on the "Remaining Horizons" document.

### 5.1. Preset Expansion & MilkDrop Format Support

*   **Goal**: Support the entire spectrum of MilkDrop presets.
*   **Tasks**:
    *   Investigate the `libprojectM`'s support for `.milk` files and extend it if necessary.
    *   Implement a more robust file loader/browser for importing presets.
    *   Develop a preset profiler to score presets based on their GPU complexity and assign them to performance tiers.
    *   (Optional) Explore using AI to auto-tune visual presets based on audio genre or user behavior.

### 5.2. Performance Optimization & Power Profiling

*   **Goal**: Prepare for real-world deployment.
*   **Tasks**:
    *   Integrate frame timing and GPU usage monitoring for each preset.
    *   Implement performance tiers (Low, Med, High) that adjust rendering quality and frame rate.
    *   Add battery usage logging and a developer overlay for performance tuning.

### 5.3. Preset Tiering Automation

*   **Goal**: Build a self-regulating engine.
*   **Tasks**:
    *   Implement a system that automatically downgrades the preset tier if performance drops or the battery is low.
    *   Create a profiling module to log frame times, memory/GPU usage, and dropped frames.
    *   (Optional) Use AI to score preset responsiveness and adjust accordingly.

### 5.4. Test Harness & CI Integration

*   **Goal**: Harden for production readiness.
*   **Tasks**:
    *   Add unit tests for the audio processing pipeline using mocked FFT data.
    *   Write UI tests for preset loading and UI interactions.
    *   Add instrumentation tests to measure FPS, CPU, and memory usage.
    *   Establish a CI/CD pipeline with different test tiers (smoke, integration, performance).

### 5.5. iOS and Cross-Platform Planning

*   **Goal**: Prepare for iOS deployment.
*   **Tasks**:
    *   Abstract the engine interface to support both OpenGL ES (Android) and Metal (iOS).
    *   Investigate and implement a system audio capture solution for iOS.
    *   Ensure that `libprojectM` can be compiled with the iOS toolchain.

### 5.6. User Feature Layer

*   **Goal**: Expand user control & community.
*   **Tasks**:
    *   Allow users to import presets from various sources.
    *   Add support for preset metadata (author, tags, etc.).
    *   Implement a "share preset" feature.
    *   Add a "favorites" list and auto-rotation of presets.

### 5.7. Production Configuration Modes

*   **Goal**: Establish dev/test/prod environments.
*   **Tasks**:
    *   **Dev mode**: Implement debug overlays (FPS, CPU, etc.) and manual frame stepping.
    *   **Test mode**: Implement automated preset cycling and mocked audio input.
    *   **Prod mode**: Minimize logging, use optimized shaders, and securely store settings.
