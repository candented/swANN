```
.
├── app
│   ├── src
│   │   ├── main
│   │   │   ├── cpp
│   │   │   │   ├── CMakeLists.txt
│   │   │   │   └── pulsebridge.cpp
│   │   │   ├── java
│   │   │   │   └── com
│   │   │   │       └── example
│   │   │   │           └── pulsebridge
│   │   │   │               ├── AudioCaptureService.java
│   │   │   │               ├── MainActivity.java
│   │   │   │               ├── PulseBridge.java
│   │   │   │               ├── PulseBridgeRenderer.java
│   │   │   │               └── SettingsActivity.java
│   │   │   ├── res
│   │   │   │   ├── layout
│   │   │   │   │   └── activity_settings.xml
│   │   │   │   └── values
│   │   │   │       ├── colors.xml
│   │   │   │       ├── strings.xml
│   │   │   │       └── styles.xml
│   │   │   └── AndroidManifest.xml
│   └── build.gradle
├── HANDOFF.md
└── README.md
```

## Architecture Diagram

```
+-----------------------+
|   Audio Source (Any App)  |
+-----------+-----------+
            |
            v
+-----------+-----------+
|  AudioCaptureService  |
| (MediaProjection)     |
+-----------+-----------+
            |
            v (Broadcast)
+-----------+-----------+
|      MainActivity     |
| (UI, Preset Control)  |
+-----------+-----------+
            |
            v
+-----------+-----------+
|  PulseBridgeRenderer  |
| (OpenGL ES)           |
+-----------+-----------+
            |
            v (JNI)
+-----------+-----------+
|      PulseBridge      |
| (JNI Bridge)          |
+-----------+-----------+
            |
            v
+-----------+-----------+
|      libprojectM      |
| (Visualization Engine)|
+-----------------------+
```
