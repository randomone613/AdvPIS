package WastedWars.src.MiniGames.DecibelChallenge;

import javax.sound.sampled.*;

/**
 * Captures sound levels from the microphone to determine current sound intensity.
 */
public class SoundLevelDetector implements Runnable {
    private TargetDataLine microphone;
    private AudioFormat format;
    private volatile boolean capturing = false;
    private float currentSoundLevel = 0.0f;

    public SoundLevelDetector() throws LineUnavailableException {
        format = new AudioFormat(44100.0f, 16, 1, true, true);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        microphone = (TargetDataLine) AudioSystem.getLine(info);
    }

    /**
     * Starts capturing sound from the microphone in a separate thread.
     */
    public void startCapturing() throws LineUnavailableException {
        microphone.open(format);
        microphone.start();
        capturing = true;

        Thread captureThread = new Thread(this);
        captureThread.start();
    }

    @Override
    public void run() {
        byte[] buffer = new byte[2048];
        while (capturing) {
            int bytesRead = microphone.read(buffer, 0, buffer.length);

            if (bytesRead > 0) {
                long sum = 0;
                for (int i = 0; i < bytesRead / 2; i++) {
                    int sample = (buffer[2 * i] << 8) | (buffer[2 * i + 1] & 0xFF);
                    sum += sample * sample;
                }

                float rms = (float) Math.sqrt(sum / (bytesRead / 2));

                float decibels = (rms > 0) ? 20.0f * (float) Math.log10(rms) : -80.0f;
                currentSoundLevel = Math.max(decibels, 0.0f);
            }
        }
    }

    public float getSoundLevel() {
        return currentSoundLevel;
    }

    /**
     * Stops capturing sound and closes the microphone line.
     */
    public void stopCapturing() {
        capturing = false;
        microphone.stop();
        microphone.close();
    }
}
