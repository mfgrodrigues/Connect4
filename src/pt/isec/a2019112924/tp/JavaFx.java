package pt.isec.a2019112924.tp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.isec.a2019112924.tp.jogo.iu.gui.PrincipalPane;
import pt.isec.a2019112924.tp.jogo.iu.gui.Root;

public class JavaFx extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        PrincipalPane gui = new PrincipalPane();
        stage.setScene(new Scene(gui.obtemRootPane(), 800, 600));
        stage.setTitle("4 em Linha");
        stage.setMinWidth(300);
        stage.setMinHeight(300);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
