package pt.isec.a2019112924.tp.jogo.iu.gui.estados;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.isec.a2019112924.tp.jogo.logica.JogoObservavel;

import static pt.isec.a2019112924.tp.jogo.iu.gui.ConstantesGUI.LETRA;

public class JogaMiniJogoCPane extends VBox {
    private JogoObservavel jogObs;
    private HBox hBCalculo;
    private Label lbPergunta, lbTitulo;
    private Button btnResponder;
    private TextField tfResposta;


    public JogaMiniJogoCPane(JogoObservavel jogObs){
        this.jogObs = jogObs;
        criaComponentes();
        dispoeVista();
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
}
