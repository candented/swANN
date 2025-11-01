
package com.example.pulsebridge;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.projection.MediaProjectionManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MainActivity extends Activity {

    private static final int REQUEST_MEDIA_PROJECTION = 1;
    private static final int REQUEST_PICK_PRESET = 2;
    private static final int REQUEST_PERMISSIONS = 3;

    private GLSurfaceView glSurfaceView;
    private PulseBridgeRenderer renderer;

    private final BroadcastReceiver audioDataReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(AudioCaptureService.ACTION_AUDIO_DATA)) {
                float[] pcm = intent.getFloatArrayExtra(AudioCaptureService.EXTRA_AUDIO_DATA);
                if (renderer != null) {
                    renderer.setPCM(pcm);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS);
        }

        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);

        glSurfaceView = new GLSurfaceView(this);
        glSurfaceView.setEGLContextClientVersion(2);
        renderer = new PulseBridgeRenderer(this);
        glSurfaceView.setRenderer(renderer);
        mainLayout.addView(glSurfaceView);

        LinearLayout buttonLayout = new LinearLayout(this);
        buttonLayout.setOrientation(LinearLayout.HORIZONTAL);

        Button selectPresetButton = new Button(this);
        selectPresetButton.setText("Select Preset");
        selectPresetButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, FilePickerActivity.class);
            startActivityForResult(intent, REQUEST_PICK_PRESET);
        });
        buttonLayout.addView(selectPresetButton);

        Button clearPresetButton = new Button(this);
        clearPresetButton.setText("Clear Preset");
        clearPresetButton.setOnClickListener(v -> renderer.clearPreset());
        buttonLayout.addView(clearPresetButton);

        Button prevPresetButton = new Button(this);
        prevPresetButton.setText("Previous");
        prevPresetButton.setOnClickListener(v -> renderer.prevPreset());
        buttonLayout.addView(prevPresetButton);

        Button nextPresetButton = new Button(this);
        nextPresetButton.setText("Next");
        nextPresetButton.setOnClickListener(v -> renderer.nextPreset());
        buttonLayout.addView(nextPresetButton);

        Button settingsButton = new Button(this);
        settingsButton.setText("Settings");
        settingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
        buttonLayout.addView(settingsButton);

        mainLayout.addView(buttonLayout);
        setContentView(mainLayout);

        MediaProjectionManager mediaProjectionManager = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);
        startActivityForResult(mediaProjectionManager.createScreenCaptureIntent(), REQUEST_MEDIA_PROJECTION);

        LocalBroadcastManager.getInstance(this).registerReceiver(audioDataReceiver, new IntentFilter(AudioCaptureService.ACTION_AUDIO_DATA));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_MEDIA_PROJECTION) {
            if (resultCode == RESULT_OK) {
                Intent intent = new Intent(this, AudioCaptureService.class);
                intent.putExtra("media_projection", data.getParcelableExtra(MediaProjectionManager.EXTRA_MEDIA_PROJECTION));
                startService(intent);
            }
        } else if (requestCode == REQUEST_PICK_PRESET) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    String path = data.getStringExtra("result");
                    if (path != null) {
                        renderer.loadPreset(path);
                    }
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permissions granted, proceed with app initialization
            } else {
                // Permissions denied, handle accordingly
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        glSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        glSurfaceView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(audioDataReceiver);
    }
}
