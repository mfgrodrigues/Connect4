package pt.isec.a2019112924.tp.jogo.iu.gui.estados;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import pt.isec.a2019112924.tp.jogo.iu.gui.recursos.ImageLoader;
import pt.isec.a2019112924.tp.jogo.iu.gui.recursos.MusicPlayer;
import pt.isec.a2019112924.tp.jogo.logica.JogoObservavel;
import pt.isec.a2019112924.tp.jogo.utils.Situacao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static pt.isec.a2019112924.tp.jogo.iu.gui.ConstantesGUI.*;
import static pt.isec.a2019112924.tp.jogo.iu.gui.recursos.PropsID.PROP_ESTADO;


public class TerminaJogoPane extends VBox {
    private JogoObservavel jogObs;
    private Label lbVencedor;
    private ImageView venceAmarelo, venceAzul;
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
        StackPane imagemVencedores = new StackPane();
        Image amarelo = ImageLoader.getImage(VENCEAMARELO);
        Image azul = ImageLoader.getImage(VENCEAZUL);
        venceAmarelo = new ImageView(amarelo);
        venceAzul = new ImageView(azul);
        venceAmarelo.setFitHeight(350);
        venceAmarelo.setFitWidth(290);
        venceAzul.setFitHeight(350);
        venceAzul.setFitWidth(290);
        imagemVencedores.getChildren().addAll(venceAmarelo, venceAzul);
        getChildren().addAll(lbVencedor, imagemVencedores, btnNovaTentativa);
        setAlignment(Pos.CENTER);
        setSpacing(20);
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
            if(jogObs.getJogadorAtual().getPeca() == 'X'){
                venceAmarelo.setVisible(true);
                venceAzul.setVisible(false);
            }
            else{
                venceAmarelo.setVisible(false);
                venceAzul.setVisible(true);
            }
            MusicPlayer.playMusic(MUSICA);
            lbVencedor.setFont(LETRA_JOGO);
            lbVencedor.setText("PARABÉNS " + jogObs.getJogadorAtual().getNome().toUpperCase());
            this.setVisible(true);
        }
        else{
            this.setVisible(false);
        }
    }
}
