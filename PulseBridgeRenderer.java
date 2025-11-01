
package com.example.pulsebridge;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.widget.Toast;

import java.io.IOException;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class PulseBridgeRenderer implements GLSurfaceView.Renderer {

    private final PulseBridge pulseBridge = new PulseBridge();
    private final Context context;
    private float[] pcm;

    public PulseBridgeRenderer(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // The native init() function is called from onSurfaceChanged()
        // because the width and height are not known until then.
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        pulseBridge.init(width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        if (pcm != null) {
            pulseBridge.addPCM(pcm);
        }

        pulseBridge.onDrawFrame();
    }

    public void setPCM(float[] pcm) {
        this.pcm = pcm;
    }

    public void loadPreset(String path) {
        try {
            pulseBridge.loadPreset(path);
        } catch (IOException e) {
            Toast.makeText(context, "Failed to load preset", Toast.LENGTH_SHORT).show();
        }
    }

    public void clearPreset() {
        pulseBridge.clearPreset();
    }

    public void prevPreset() {
        pulseBridge.prevPreset();
    }

    public void nextPreset() {
        pulseBridge.nextPreset();
    }
}
