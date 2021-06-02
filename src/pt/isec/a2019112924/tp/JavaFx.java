package pt.isec.a2019112924.tp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.isec.a2019112924.tp.jogo.iu.gui.Root;

public class JavaFx extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Root root = new Root();
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("4 em Linha");
        stage.setMinWidth(300);
        stage.setMinHeight(300);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
