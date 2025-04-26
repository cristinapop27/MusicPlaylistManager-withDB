package model;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import java.io.File;

public class AudioPlayer {
    private BasicPlayer player;

    public AudioPlayer() {
        player = new BasicPlayer();
    }

    public void play(String filePath) {
        try{
            if (filePath == null || filePath.isEmpty()) {
                System.err.println("Cannot play song; filePath is null or empty.");
                return;
            }

            File mp3 = new File(filePath);
            player.open(mp3);
            player.play();
        }catch (BasicPlayerException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        try {
            player.pause();
        } catch (BasicPlayerException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        try {
            player.resume();
        } catch (BasicPlayerException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            player.stop();
        } catch (BasicPlayerException e) {
            e.printStackTrace();
        }
    }

    public void setVolume(double gain) {
        try {
            player.setGain(gain); 
        } catch (BasicPlayerException e) {
            e.printStackTrace();
        }
    }

}
