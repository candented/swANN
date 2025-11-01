
package com.example.pulsebridge;

import android.app.Service;
import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.projection.MediaProjection;
import android.os.IBinder;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class AudioCaptureService extends Service {

    public static final String ACTION_AUDIO_DATA = "com.example.pulsebridge.ACTION_AUDIO_DATA";
    public static final String EXTRA_AUDIO_DATA = "com.example.pulsebridge.EXTRA_AUDIO_DATA";

    private static final int SAMPLE_RATE = 44100;
    private static final int CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;
    private static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_FLOAT;

    private AudioRecord audioRecord;
    private MediaProjection mediaProjection;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaProjection = (MediaProjection) intent.getParcelableExtra("media_projection");
        if (mediaProjection == null) {
            return START_NOT_STICKY;
        }

        audioRecord = new AudioRecord.Builder()
                .setAudioFormat(new AudioFormat.Builder()
                        .setEncoding(AUDIO_FORMAT)
                        .setSampleRate(SAMPLE_RATE)
                        .setChannelMask(CHANNEL_CONFIG)
                        .build())
                .setBufferSizeInBytes(AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT) * 2)
                .build();

        audioRecord.startRecording();

        new Thread(() -> {
            float[] buffer = new float[1024];
            while (audioRecord.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING) {
                int read = audioRecord.read(buffer, 0, buffer.length, AudioRecord.READ_BLOCKING);
                if (read > 0) {
                    Intent broadcastIntent = new Intent(ACTION_AUDIO_DATA);
                    broadcastIntent.putExtra(EXTRA_AUDIO_DATA, buffer);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
                }
            }
        }).start();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (audioRecord != null) {
            audioRecord.stop();
            audioRecord.release();
        }
        if (mediaProjection != null) {
            mediaProjection.stop();
        }
    }
}
