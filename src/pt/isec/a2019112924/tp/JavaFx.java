package pt.isec.a2019112924.tp;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pt.isec.a2019112924.tp.jogo.iu.gui.PrincipalPane;
import pt.isec.a2019112924.tp.jogo.iu.gui.Root;
import pt.isec.a2019112924.tp.jogo.logica.Gestor;
import pt.isec.a2019112924.tp.jogo.logica.JogoObservavel;
import pt.isec.a2019112924.tp.jogo.utils.Situacao;

public class JavaFx extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Gestor gestorJogo = new Gestor();
        JogoObservavel jogObs = new JogoObservavel(gestorJogo);

        PrincipalPane gui = new PrincipalPane(jogObs);
        stage.setScene(new Scene(gui.obtemRootPane(), 900, 600));
        stage.setTitle("4 em Linha");
        stage.setMinWidth(300);
        stage.setMinHeight(300);
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                if(jogObs.getSituacaoAtual() == Situacao.AguardaJogada) {
                    jogObs.saveEstadoJogoFicheiro("jogo.bin");
                }
                stage.close();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
