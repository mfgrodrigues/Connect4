package pt.isec.a2019112924.tp.jogo.iu.gui.estados;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pt.isec.a2019112924.tp.jogo.logica.JogoObservavel;
import pt.isec.a2019112924.tp.jogo.utils.Situacao;

import static pt.isec.a2019112924.tp.jogo.logica.PropsID.PROP_ESTADO;


public class TerminaJogoPane extends VBox {
    private JogoObservavel jogObs;
    private Label lbVencedor;
    private Button btnNovaTentativa;

    public TerminaJogoPane(JogoObservavel jogObs){
        this.jogObs = jogObs;
       criaComponentes();
       dispoeVista();
       registaObservador();
    }

    private void criaComponentes(){
        lbVencedor= new Label();
        btnNovaTentativa = new Button("Novo Jogo");
    }

    private void dispoeVista(){
        setAlignment(Pos.CENTER);
        setPadding(new Insets(25, 25, 25, 25));
        setSpacing(20);
        getChildren().addAll(lbVencedor, btnNovaTentativa);
    }

    private void registaObservador(){jogObs.addPropertyChangelistener(PROP_ESTADO, evt -> {atualizaEstado();});}

    private void atualizaEstado(){
        lbVencedor.setText("Parabens, " + jogObs.getJogadorAtual().getNome() + " .Es o grande vencedor!");
        this.setVisible(jogObs.getSituacaoAtual() == Situacao.TerminaJogo);}
}
