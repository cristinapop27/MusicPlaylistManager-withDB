import model.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

public class AudioPlayerTest {
    private static AudioPlayer audioPlayer;

    @BeforeAll
    static void setup() {
        audioPlayer = new AudioPlayer();
    }

    @Test
    void testPlayWithValidFile() {
        // Replace with the path to a valid MP3 or WAV file for testing
        String validFilePath = "C:\\Users\\crist\\OneDrive\\Desktop\\utcn\\an II sem I\\oop\\proiect\\Deep Purple - Smoke On the Water (Official Music Video).wav";

        File file = new File(validFilePath);
        Assertions.assertTrue(file.exists(), "The test file should exist.");

        Assertions.assertDoesNotThrow(() -> audioPlayer.play(validFilePath),
                "Playing a valid audio file should not throw an exception.");
    }

    @Test
    void testPlayWithInvalidFile() {
        String invalidFilePath = "C:\\nothing";

        File file = new File(invalidFilePath);
        Assertions.assertFalse(file.exists(), "The test file should not exist.");

        Assertions.assertDoesNotThrow(() -> audioPlayer.play(invalidFilePath),
                "Playing an invalid audio file path should not throw an exception.");
    }

    @Test
    void testPlayWithNullFilePath() {
        Assertions.assertDoesNotThrow(() -> audioPlayer.play(null),
                "Playing a null file path should not throw an exception.");
    }

    @Test
    void testPauseWithoutPlaying() {
        Assertions.assertDoesNotThrow(() -> audioPlayer.pause(),
                "Pausing without playing should not throw an exception.");
    }

    @Test
    void testResumeWithoutPlaying() {
        Assertions.assertDoesNotThrow(() -> audioPlayer.resume(),
                "Resuming without playing should not throw an exception.");
    }

    @Test
    void testStopWithoutPlaying() {
        Assertions.assertDoesNotThrow(() -> audioPlayer.stop(),
                "Stopping without playing should not throw an exception.");
    }

    @Test
    void testSetVolumeWithinRange() {
        Assertions.assertDoesNotThrow(() -> audioPlayer.setVolume(0.5),
                "Setting volume within the range should not throw an exception.");
    }

    @Test
    void testSetVolumeOutOfRange() {
        Assertions.assertDoesNotThrow(() -> audioPlayer.setVolume(-0.5),
                "Setting volume below the range should not throw an exception.");
        Assertions.assertDoesNotThrow(() -> audioPlayer.setVolume(1.5),
                "Setting volume above the range should not throw an exception.");
    }
}
