package org.example.snakegame.data;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.example.snakegame.view.GamePane;

import java.io.File;

public class GameSound {
    private static GameSound instance;

    MediaPlayer mediaPlayer;
    Media media;

    private GameSound(){
        File file = new File("src/main/resources/org/example/snakegame/data/size inc.mp3");
        media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
    }

    public static GameSound getInstance(){
        return instance != null? instance: new GameSound();
    }

    public void sizeIncrease(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.stop();
                mediaPlayer.play();
            }
        });
        thread.start();
    }
}
