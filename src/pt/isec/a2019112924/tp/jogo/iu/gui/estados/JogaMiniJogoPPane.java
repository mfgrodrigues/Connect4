package pt.isec.a2019112924.tp.jogo.iu.gui.estados;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import pt.isec.a2019112924.tp.jogo.logica.JogoObservavel;
import pt.isec.a2019112924.tp.jogo.utils.Situacao;

import static pt.isec.a2019112924.tp.jogo.iu.gui.ConstantesGUI.LETRA;
import static pt.isec.a2019112924.tp.jogo.iu.gui.recursos.PropsID.PROP_ESTADO;

public class JogaMiniJogoPPane extends VBox {
    private JogoObservavel jogObs;
    private Label lbTitulo, lbInstrucoes;
    private Text txtPergunta;
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
        txtPergunta = new Text();
        lbInstrucoes = new Label();
        btnResponder = new Button("OK");
        tfResposta = new TextField();
    }

    private void dispoeVista(){
        setAlignment(Pos.CENTER);
        setSpacing(15);
        lbTitulo.setFont(LETRA);
        tfResposta.setMaxWidth(550.00);
        getChildren().addAll(lbTitulo, lbInstrucoes, txtPergunta, tfResposta, btnResponder);
    }

    private void registaListener(){
        btnResponder.setOnAction((e)->{
            jogObs.digitaPalavra(tfResposta.getText());
        });
    }

    private void registaObservador(){
        jogObs.addPropertyChangelistener(PROP_ESTADO, evt -> {atualiza();});
    }

    public void atualiza(){
        if(jogObs.getSituacaoAtual() == Situacao.JogaMiniJogoP) {
            lbInstrucoes.setText(jogObs.getJogadorAtual().getNome() + "digita as palavras apresentadas no menor tempo possível");
            txtPergunta.setText(jogObs.getMiniJogo().getPergunta());
            txtPergunta.setFill(Color.CORNFLOWERBLUE);
            this.setVisible(true);
        }
        else{
            this.setVisible(false);
        }
    }



}
