package pt.isec.a2019112924.tp.jogo.iu.gui.estados;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import pt.isec.a2019112924.tp.jogo.iu.gui.recursos.ImageLoader;
import pt.isec.a2019112924.tp.jogo.iu.gui.recursos.MusicPlayer;
import pt.isec.a2019112924.tp.jogo.logica.JogoObservavel;
import pt.isec.a2019112924.tp.jogo.utils.Situacao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static pt.isec.a2019112924.tp.jogo.iu.gui.recursos.ConstantesGUI.*;
import static pt.isec.a2019112924.tp.jogo.iu.gui.recursos.PropsID.PROP_ESTADO;
import static pt.isec.a2019112924.tp.jogo.iu.gui.recursos.PropsID.PROP_STOPREPLAY;


public class TerminaJogoPane extends VBox {
    private JogoObservavel jogObs;
    private Label lbVencedor;
    private ImageView venceAmarelo, venceAzul, empate;
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
        Image balanca = ImageLoader.getImage(EMPATE);
        venceAmarelo = new ImageView(amarelo);
        venceAzul = new ImageView(azul);
        empate = new ImageView(balanca);
        empate.setFitHeight(350);
        empate.setFitWidth(290);
        venceAmarelo.setFitHeight(350);
        venceAmarelo.setFitWidth(290);
        venceAzul.setFitHeight(350);
        venceAzul.setFitWidth(290);
        imagemVencedores.getChildren().addAll(venceAmarelo, venceAzul, empate);
        getChildren().addAll(lbVencedor, imagemVencedores, btnNovaTentativa);
        setAlignment(Pos.CENTER);
        setSpacing(20);
    }

    private void registaListener(){
        btnNovaTentativa.setOnAction((e)->{
            jogObs.novaTentativa();
        });
    }

    private void registaObservador(){
        jogObs.addPropertyChangelistener(PROP_ESTADO, evt -> {
            atualizaEstado();
        });

        jogObs.addPropertyChangelistener(PROP_STOPREPLAY, evt -> {
            atualizaAposReplay();
        });
    }

    private void atualizaEstado() {
        if (jogObs.getSituacaoAtual() == Situacao.TerminaJogo) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss");
            if (jogObs.saveReplayJogo("Jogo" + dtf.format(LocalDateTime.now()))) {
                Alert sucesso = new Alert(Alert.AlertType.CONFIRMATION, "Jogo gravado com sucesso");
                sucesso.show();
            }
            lbVencedor.setFont(LETRA_JOGO);
            if (jogObs.getEmpate()){
                empate.setVisible(true);
                venceAmarelo.setVisible(false);
                venceAzul.setVisible(false);
                lbVencedor.setText("EMPATE");
            }
            else{
                if(jogObs.getJogadorAtual().getPeca() == 'X'){
                    venceAmarelo.setVisible(true);
                    venceAzul.setVisible(false);
                    empate.setVisible(false);
                }
                else{
                    venceAmarelo.setVisible(false);
                    venceAzul.setVisible(true);
                    empate.setVisible(false);
                }
                MusicPlayer.playMusic(MUSICA);
                lbVencedor.setText("PARABÉNS " + jogObs.getJogadorAtual().getNome().toUpperCase());
            }
            this.setVisible(true);
        }
        else{
            this.setVisible(false);
        }
    }

    private void atualizaAposReplay(){
        if(jogObs.getJogadorAtual().getPeca() == 'X'){
            venceAmarelo.setVisible(true);
            venceAzul.setVisible(false);
            empate.setVisible(false);
        }
        else{
            venceAmarelo.setVisible(false);
            venceAzul.setVisible(true);
            empate.setVisible(false);
        }
        lbVencedor.setFont(LETRA_JOGO);
        lbVencedor.setText("PARABÉNS " + jogObs.getJogadorAtual().getNome().toUpperCase());
        this.setVisible(true);
    }
}
