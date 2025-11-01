
package com.example.pulsebridge;

import java.io.IOException;

public class PulseBridge {

    static {
        System.loadLibrary("pulse-bridge");
    }

    public native void init(int width, int height);

    public native void onDrawFrame();

    public native void addPCM(float[] pcm);

    public native void loadPreset(String path) throws IOException;

    public native void clearPreset();

    public native void prevPreset();

    public native void nextPreset();
}
