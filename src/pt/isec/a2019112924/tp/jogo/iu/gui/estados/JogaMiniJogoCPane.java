package pt.isec.a2019112924.tp.jogo.iu.gui.estados;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.isec.a2019112924.tp.jogo.logica.JogoObservavel;
import pt.isec.a2019112924.tp.jogo.utils.Situacao;

import java.awt.*;

import static pt.isec.a2019112924.tp.jogo.iu.gui.ConstantesGUI.LETRA;
import static pt.isec.a2019112924.tp.jogo.iu.gui.recursos.PropsID.PROP_ESTADO;
import static pt.isec.a2019112924.tp.jogo.iu.gui.recursos.PropsID.PROP_MINIJOGO;

public class JogaMiniJogoCPane extends VBox {
    private JogoObservavel jogObs;
    private HBox hBCalculo;
    private Label lbPergunta, lbTitulo, lbTimer;
    private Button btnResponder;
    private TextField tfResposta;


    public JogaMiniJogoCPane(JogoObservavel jogObs){
        this.jogObs = jogObs;
        criaComponentes();
        dispoeVista();
        registaObservador();
        registaListener();
    }

    private void criaComponentes(){
        lbTitulo = new Label("MINI JOGO CALCULO");
        hBCalculo = new HBox(10);
        lbPergunta = new Label();
        tfResposta = new TextField();
        btnResponder = new Button("OK");
    }

    private void dispoeVista(){
        setAlignment(Pos.CENTER);
        setSpacing(15);
        lbTitulo.setFont(LETRA);
        tfResposta.setMaxWidth(50.00);
        hBCalculo.setAlignment(Pos.CENTER);
        hBCalculo.getChildren().addAll(lbPergunta,tfResposta, btnResponder);

        getChildren().addAll(lbTitulo, hBCalculo);
    }

     private void registaObservador(){
        jogObs.addPropertyChangelistener(PROP_ESTADO, evt -> {atualiza();});
        jogObs.addPropertyChangelistener(PROP_MINIJOGO, evt ->{atualizaPergunta();});
    }

    private void registaListener(){
        btnResponder.setOnAction(e -> {
            jogObs.resolveCalculo(tfResposta.getText());
            tfResposta.clear();
            if(jogObs.getMiniJogo().getJogoTerminou()){
                Alert minijogo = new Alert(Alert.AlertType.INFORMATION);
                if(jogObs.getMiniJogo().ganhou()){
                   minijogo.setContentText("Venceu o Mini Jogo. Volte a jogar.");
                }
                else{
                    minijogo.setContentText("Perdeu o Mini Jogo.");
                }
                minijogo.show();
            }
        });
    }

    private void atualizaPergunta(){
        lbPergunta.setText(jogObs.getMiniJogo().getPergunta());
        tfResposta.requestFocus();
    }

    private void atualiza(){
        if(jogObs.getSituacaoAtual() == Situacao.JogaMiniJogoC) {
            lbPergunta.setText(jogObs.getMiniJogo().getPergunta());
            this.setVisible(true);
        }
        else{
            this.setVisible(false);
        }
    }
}
