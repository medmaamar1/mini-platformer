package com.miniplatformer.systems;

import javax.sound.sampled.*;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * SoundSystem - generates procedural retro sound effects
 */
public class SoundSystem {
    private static final int SAMPLE_RATE = 22050; // Use a lower sample rate for retro feel
    private static final ExecutorService soundPool = Executors.newCachedThreadPool();

    public static void playJump() {
        soundPool.execute(() -> playSweep(400, 800, 150));
    }

    public static void playCoin() {
        soundPool.execute(() -> {
            playTone(987.77, 50, 0.5); // B5
            playTone(1318.51, 100, 0.5); // E6
        });
    }

    public static void playDamage() {
        soundPool.execute(() -> playSweep(200, 50, 300));
    }

    public static void playShoot() {
        soundPool.execute(() -> playSweep(600, 300, 50));
    }

    public static void playEnemyDefeat() {
        soundPool.execute(() -> playSweep(300, 100, 200));
    }

    public static void playPowerup() {
        soundPool.execute(() -> {
            playTone(440, 50, 0.4);
            playTone(554, 50, 0.4);
            playTone(659, 100, 0.4);
        });
    }

    private static void playTone(double freq, int durationMs, double volume) {
        try {
            byte[] buf = new byte[durationMs * SAMPLE_RATE / 1000];
            for (int i = 0; i < buf.length; i++) {
                double angle = i / (SAMPLE_RATE / freq) * 2.0 * Math.PI;
                buf[i] = (byte) (Math.sin(angle) * 127.0 * volume);
            }
            sendToSource(buf);
        } catch (Exception e) {}
    }

    private static void playSweep(double startFreq, double endFreq, int durationMs) {
        try {
            int samples = durationMs * SAMPLE_RATE / 1000;
            byte[] buf = new byte[samples];
            for (int i = 0; i < samples; i++) {
                double t = (double) i / samples;
                double freq = startFreq + (endFreq - startFreq) * t;
                double angle = i / (SAMPLE_RATE / freq) * 2.0 * Math.PI;
                buf[i] = (byte) (Math.sin(angle) * 127.0 * 0.5);
            }
            sendToSource(buf);
        } catch (Exception e) {}
    }

    private static void sendToSource(byte[] buf) throws LineUnavailableException {
        AudioFormat af = new AudioFormat(SAMPLE_RATE, 8, 1, true, false);
        SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
        sdl.open(af);
        sdl.start();
        sdl.write(buf, 0, buf.length);
        sdl.drain();
        sdl.close();
    }
}
