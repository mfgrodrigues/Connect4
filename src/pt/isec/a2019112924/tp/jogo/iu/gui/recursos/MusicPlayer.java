package pt.isec.a2019112924.tp.jogo.iu.gui.recursos;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class MusicPlayer {
    static MediaPlayer mediaPlayer;

    public static void playMusic(String nome){
        String path = Recursos.getResourceFilename("sons/" +  nome);
        if (path == null)
            return;
        Media musica = new Media(path);
        mediaPlayer = new MediaPlayer(musica);
        mediaPlayer.setStartTime(Duration.ZERO);
        mediaPlayer.setStopTime(musica.getDuration());
        mediaPlayer.setOnReady(() -> {
            mediaPlayer.play();
        });
        mediaPlayer.setAutoPlay(true);
    }

    public static void stopMusic(String nome){
        mediaPlayer.stop();
    }
}


