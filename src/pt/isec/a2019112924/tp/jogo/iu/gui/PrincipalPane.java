package pt.isec.a2019112924.tp.jogo.iu.gui;

import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import pt.isec.a2019112924.tp.jogo.iu.gui.estados.AguardaInicioPane;
import pt.isec.a2019112924.tp.jogo.iu.gui.estados.AguardaJogadaPane;
import pt.isec.a2019112924.tp.jogo.iu.gui.estados.JogaMiniJogoCPane;
import pt.isec.a2019112924.tp.jogo.iu.gui.estados.JogaMiniJogoPPane;
import pt.isec.a2019112924.tp.jogo.iu.gui.estados.TerminaJogoPane;


public class PrincipalPane extends BorderPane {
    private StackPane rootPane;

    public PrincipalPane(){
        criarLayout();
    }

    public Parent obtemRootPane(){ return rootPane;}

    //Criar componentes
    //Dispor Vista

    private void criarLayout(){

        //Paineis de Estado
        AguardaInicioPane aguardaInicioPane = new AguardaInicioPane();
        AguardaJogadaPane aguardaJogadaPane = new AguardaJogadaPane();
        JogaMiniJogoCPane jogaMiniJogoCPane = new JogaMiniJogoCPane();
        JogaMiniJogoPPane jogaMiniJogoPPane = new JogaMiniJogoPPane();
        TerminaJogoPane terminaJogoPane = new TerminaJogoPane();

        //Stack com os paineis dos estados
        rootPane = new StackPane();
        aguardaInicioPane.setVisible(true);
        rootPane.getChildren().addAll(aguardaInicioPane);
    }
}
