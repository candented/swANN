# Contributing to PulseBridge

We welcome contributions from the community! Whether you're a seasoned developer or just starting, there are many ways to help improve PulseBridge.

## Setting up the Test Environment

To ensure consistency and avoid introducing regressions, please follow these steps to set up your test environment:

1.  **Clone the repository and follow the build instructions in the `README.md` file.**
2.  **Use an Android emulator or a physical device running a stock version of Android for testing.**
3.  **Test with a variety of audio sources to ensure the visualizer responds correctly to different types of music.**

## Debugging Presets

If you encounter a preset that is not rendering correctly, you can use the following steps to debug the issue:

1.  **Isolate the preset:** Test the preset in isolation to determine if the issue is specific to that preset or a more general problem.
2.  **Check the preset code:** Open the `.milk` file and examine the preset's code for any syntax errors or logical issues.
3.  **Use the Android Studio debugger:** Set breakpoints in the `PulseBridgeRenderer` and `PulseBridge` classes to inspect the audio data and the state of the `libprojectM` engine.

## Guidelines for UI Additions and Visual Feedback

When adding new UI elements or visual feedback, please adhere to the following guidelines:

*   **Follow the Material Design guidelines:** Ensure that your UI additions are consistent with the overall look and feel of the application.
*   **Keep it simple:** Avoid cluttering the UI with unnecessary elements. The focus should be on the visualizations.
*   **Provide clear and concise feedback:** Any visual feedback should be easy to understand and should not distract from the main user experience.
