package pt.isec.a2019112924.tp.jogo.iu.gui.estados;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pt.isec.a2019112924.tp.jogo.logica.JogoObservavel;
import pt.isec.a2019112924.tp.jogo.utils.Situacao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static pt.isec.a2019112924.tp.jogo.iu.gui.recursos.PropsID.PROP_ESTADO;


public class TerminaJogoPane extends VBox {
    private JogoObservavel jogObs;
    private Label lbVencedor;
    private Button btnNovaTentativa;

    public TerminaJogoPane(JogoObservavel jogObs){
        this.jogObs = jogObs;
       criaComponentes();
       dispoeVista();
       registaObservador();
       registaListener();
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

    private void registaListener(){
        btnNovaTentativa.setOnAction((e)->{
            jogObs.novaTentativa();
        });
    }

    private void registaObservador(){jogObs.addPropertyChangelistener(PROP_ESTADO, evt -> {atualizaEstado();});}

    private void atualizaEstado() {
        if (jogObs.getSituacaoAtual() == Situacao.TerminaJogo) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss");
            if (jogObs.saveReplayJogo("Jogo" + dtf.format(LocalDateTime.now()))) {
                Alert sucesso = new Alert(Alert.AlertType.CONFIRMATION, "Jogo gravado com sucesso");
                sucesso.show();
            }
            lbVencedor.setText("Parabens, " + jogObs.getJogadorAtual().getNome() + ". Es o grande vencedor!");
            this.setVisible(true);
        }
        else{
            this.setVisible(false);
        }
    }
}
