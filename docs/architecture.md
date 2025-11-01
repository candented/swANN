# Application Architecture

## High-Level Diagram

```
+-----------------------+
| AudioCaptureService   |
+-----------------------+
           |
           | (LocalBroadcastManager)
           v
+-----------------------+
|     MainActivity      |
+-----------------------+
           |
           v
+-----------------------+
| PulseBridgeRenderer   |
+-----------------------+
           |
           | (JNI)
           v
+-----------------------+
|     libprojectM       |
+-----------------------+
```

## Component Breakdown

*   **AudioCaptureService:** A background service responsible for capturing system audio using the `MediaProjection` API. It runs independently of the main UI and broadcasts the captured audio data.

*   **MainActivity:** The main entry point of the application. It manages the UI, including the `GLSurfaceView` for rendering, and handles user interactions. It receives audio data from the `AudioCaptureService` and passes it to the `PulseBridgeRenderer`.

*   **PulseBridgeRenderer:** A `GLSurfaceView.Renderer` implementation that acts as the bridge between the Android framework and the native `libprojectM` engine. It receives audio data from the `MainActivity` and uses a JNI interface to communicate with the native code.

*   **libprojectM:** The core visualization engine. It's a C++ library that takes audio data as input and generates the visual presets. The communication with the `PulseBridgeRenderer` is handled through a JNI bridge.

## JNI Interaction

The `PulseBridge` class serves as the JNI bridge between the Java/Kotlin world and the native C++ code. It declares native methods that are implemented in `jni/pulse-bridge.cpp`. This allows the `PulseBridgeRenderer` to call C++ functions to initialize the engine, send audio data, and manage presets.

## LibprojectM Configuration

`libprojectM` is configured to receive raw PCM audio data. The `PulseBridgeRenderer` is responsible for converting the audio data to the format expected by the engine before passing it through the JNI bridge.
