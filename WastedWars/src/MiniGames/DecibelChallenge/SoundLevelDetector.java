package WastedWars.src.MiniGames.DecibelChallenge;

import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;

public class SoundLevelDetector {
    private TargetDataLine microphone;
    private AudioFormat format;

    public SoundLevelDetector() throws LineUnavailableException {
        // Define the audio format (sample rate, 16-bit, mono, signed, big-endian)
        format = new AudioFormat(44100.0f, 16, 1, true, true);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        microphone = (TargetDataLine) AudioSystem.getLine(info);
    }

    // Start capturing sound from the microphone
    public void startCapturing() throws LineUnavailableException {
        microphone.open(format);
        microphone.start();
    }

    // Read the sound data and return the volume level
    public float getSoundLevel() {
        byte[] buffer = new byte[2048]; // Buffer for reading sound data
        int bytesRead = microphone.read(buffer, 0, buffer.length);

        if (bytesRead <= 0) {
            return 0; // If no bytes are read, return 0
        }

        long sum = 0;
        for (int i = 0; i < bytesRead / 2; i++) {
            int sample = (buffer[2 * i] << 8) | (buffer[2 * i + 1] & 0xFF); // Convert to signed 16-bit
            sum += sample * sample;
        }

        // Calculate RMS (Root Mean Square) for loudness
        float rms = (float) Math.sqrt(sum / (bytesRead / 2));

        // Convert RMS to decibels, ensure we clip it to avoid -infinity when very quiet
        float decibels = (rms > 0) ? 20.0f * (float) Math.log10(rms) : -80.0f;

        // Normalize the decibel value to avoid extreme negative values, adjust scaling as needed
        return Math.max(decibels, 0.0f); // Clip to 0 if too quiet
    }

    // Stop capturing sound from the microphone
    public void stopCapturing() {
        microphone.stop();
        microphone.close();
    }
}
