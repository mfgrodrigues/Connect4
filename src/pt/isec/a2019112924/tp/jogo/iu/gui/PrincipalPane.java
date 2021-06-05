package pt.isec.a2019112924.tp.jogo.iu.gui;

import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import pt.isec.a2019112924.tp.jogo.iu.gui.estados.AguardaInicioPane;
import pt.isec.a2019112924.tp.jogo.iu.gui.estados.AguardaJogadaPane;
import pt.isec.a2019112924.tp.jogo.iu.gui.estados.JogaMiniJogoCPane;
import pt.isec.a2019112924.tp.jogo.iu.gui.estados.JogaMiniJogoPPane;
import pt.isec.a2019112924.tp.jogo.iu.gui.estados.TerminaJogoPane;
import pt.isec.a2019112924.tp.jogo.logica.JogoObservavel;


public class PrincipalPane extends BorderPane {
    private JogoObservavel jogObs;
    private StackPane rootPane;

    public PrincipalPane(JogoObservavel jogObs){
        this.jogObs = jogObs;
        criarLayout();
    }

    public Parent obtemRootPane(){ return rootPane;}

    //Criar componentes
    //Dispor Vista

    private void criarLayout(){

        //Paineis de Estado
        AguardaInicioPane aguardaInicioPane = new AguardaInicioPane(jogObs);
        AguardaJogadaPane aguardaJogadaPane = new AguardaJogadaPane(jogObs);
        JogaMiniJogoCPane jogaMiniJogoCPane = new JogaMiniJogoCPane(jogObs);
        JogaMiniJogoPPane jogaMiniJogoPPane = new JogaMiniJogoPPane(jogObs);
        TerminaJogoPane terminaJogoPane = new TerminaJogoPane(jogObs);

        //Stack com os paineis dos estados
        rootPane = new StackPane();
        aguardaInicioPane.setVisible(true);
        aguardaJogadaPane.setVisible(false);
        terminaJogoPane.setVisible(false);
        jogaMiniJogoPPane.setVisible(false);
        jogaMiniJogoCPane.setVisible(false);
        rootPane.getChildren().addAll(aguardaInicioPane, aguardaJogadaPane, jogaMiniJogoPPane, jogaMiniJogoCPane, terminaJogoPane);
    }
}
