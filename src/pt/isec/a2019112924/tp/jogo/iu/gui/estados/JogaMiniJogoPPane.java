package pt.isec.a2019112924.tp.jogo.iu.gui.estados;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import pt.isec.a2019112924.tp.jogo.logica.JogoObservavel;
import pt.isec.a2019112924.tp.jogo.utils.Situacao;

import static pt.isec.a2019112924.tp.jogo.iu.gui.ConstantesGUI.LETRA;
import static pt.isec.a2019112924.tp.jogo.logica.PropsID.PROP_ESTADO;

public class JogaMiniJogoPPane extends VBox {
    private JogoObservavel jogObs;
    private Label lbPergunta, lbTitulo, lbInstrucoes;
    private Button btnResponder;
    private TextField tfResposta;


    public JogaMiniJogoPPane(JogoObservavel jogObs){
        this.jogObs = jogObs;
        criaComponentes();
        dispoeVista();
        registaListener();
        registaObservador();
    }

    private void criaComponentes(){
        lbTitulo = new Label("MINI JOGO PALAVRAS");
        lbPergunta = new Label();
        lbInstrucoes = new Label("Digite as palavras apresentadas no menor tempo possível");
        btnResponder = new Button("OK");
        tfResposta = new TextField();
    }

    private void dispoeVista(){
        setAlignment(Pos.CENTER);
        setSpacing(15);
        lbTitulo.setFont(LETRA);
        tfResposta.setMaxWidth(550.00);

        getChildren().addAll(lbTitulo, lbInstrucoes, lbPergunta, tfResposta, btnResponder);
    }

    private void registaListener(){
        btnResponder.setOnAction((e)->{

        });
    }

    private void registaObservador(){
        jogObs.addPropertyChangelistener(PROP_ESTADO, evt -> {atualiza();});
    }

    public void atualiza(){
        lbPergunta.setText(jogObs.getMiniJogo().getPergunta());
        this.setVisible(jogObs.getSituacaoAtual() == Situacao.AguardaInicio);
    }



}
