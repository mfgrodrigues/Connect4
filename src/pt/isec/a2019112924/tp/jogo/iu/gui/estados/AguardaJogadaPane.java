package pt.isec.a2019112924.tp.jogo.iu.gui.estados;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import pt.isec.a2019112924.tp.jogo.logica.JogoObservavel;
import pt.isec.a2019112924.tp.jogo.logica.dados.JogadorHumano;
import pt.isec.a2019112924.tp.jogo.logica.dados.JogadorVirtual;
import pt.isec.a2019112924.tp.jogo.utils.Situacao;

import static javafx.scene.input.KeyCode.ENTER;
import static pt.isec.a2019112924.tp.jogo.logica.PropsID.*;

public class AguardaJogadaPane extends BorderPane{
    private JogoObservavel jogObs;

    private VBox centro;
    private HBox opcoes, hBDados;
    private GridPane tabuleiro;
    private Button btnJoga, btnJogaEspecial, btnMiniJogo, btnUndo, btnAvanca;
    private TextField tfColuna;
    private StackPane comandos = new StackPane();
    private Label jogadorAtual, jogador1, jogador2, jogadorVirtual;


    public AguardaJogadaPane(JogoObservavel jogObs){
        this.jogObs = jogObs;

        criaComponentes();
        dispoeVista();
        registaListeners();
        registaObservador();
    }

    private void criaComponentes(){


        centro = new VBox(40);
        opcoes = new HBox();
        hBDados = new HBox();
        tabuleiro = new GridPane();
        btnJoga = new Button("Jogar Peca");
        btnJogaEspecial = new Button("Jogar Peca Especial");
        btnMiniJogo = new Button("Jogar Mini Jogo");
        btnUndo = new Button("Voltar Atras");
        btnAvanca = new Button("Jogar");
        tfColuna = new TextField();
        jogador1 = new Label();
        jogador2 = new Label();
        jogadorAtual = new Label();
        jogadorVirtual = new Label("Pressione [ENTER] para avancar");
        jogadorVirtual.setVisible(false);

        centro.setAlignment(Pos.CENTER);
        //tabuleiro de jogo

        tabuleiro.setAlignment(Pos.CENTER);
        tabuleiro.setPadding(new Insets(10, 10, 10, 10));
        tabuleiro.setPrefSize(270.0, 230.0);
        tabuleiro.setMinSize(270.0, 230.0);
        tabuleiro.setMaxSize(270.0, 230.0);
        tabuleiro.setVgap(5);
        tabuleiro.setHgap(5);

        for(int i = 0; i < jogObs.getTabuleiro().length; i++){
            for (int j = 0; j < jogObs.getTabuleiro()[0].length; j++){
                Circle circulo = new Circle(15, Color.WHITE);
                tabuleiro.add(circulo, j, i);
            }
        }
        tabuleiro.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, new CornerRadii(10), Insets.EMPTY)));
        tabuleiro.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(3))));
        //centro.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        setCenter(centro);
    }

    private void dispoeVista(){
        // Comandos Jogador Humano
        opcoes.getChildren().addAll(btnJoga, btnJogaEspecial, btnMiniJogo, btnUndo);
        opcoes.setAlignment(Pos.CENTER);
        opcoes.setSpacing(10);
        opcoes.setPrefSize(900.00, 150.00);
        opcoes.setMinSize(900.00, 150.00);

        //Insercao da coluna
        hBDados.setAlignment(Pos.CENTER);
        hBDados.setSpacing(15);
        hBDados.getChildren().addAll(new Label("Coluna: "), tfColuna, btnAvanca);
        hBDados.setVisible(false);
        comandos.getChildren().addAll(opcoes,hBDados, jogadorVirtual);
        //comandos.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
        comandos.setVisible(false);
        setBottom(comandos);
    }

    private void registaListeners(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        btnJoga.setOnAction( (e)->{
            opcoes.setVisible(false);
            hBDados.setVisible(true);
        });

        btnAvanca.setOnAction((e)->{
            if (tfColuna.getText().isEmpty() || Integer.parseInt(tfColuna.getText()) <= 0 || Integer.parseInt(tfColuna.getText()) > jogObs.getTabuleiro()[0].length) {
                alert.setContentText("Introduza uma coluna valida");
                alert.show();
                return;
            }
            jogObs.jogaPeca(Integer.parseInt(tfColuna.getText())-1);
            hBDados.setVisible(false);
        });

        btnJogaEspecial.setOnAction((e)->{
            opcoes.setVisible(false);
            hBDados.setVisible(true);
            if (tfColuna.getText().isEmpty() || Integer.parseInt(tfColuna.getText()) <= 0 || Integer.parseInt(tfColuna.getText()) > jogObs.getTabuleiro()[0].length) {
                alert.setContentText("Introduza uma coluna valida");
                alert.show();
                return;
            }
            jogObs.jogaPecaEspecial(Integer.parseInt(tfColuna.getText())-1);
            hBDados.setVisible(false);
        });

        setFocusTraversable(true);
        setOnKeyPressed((e)->{
            if(e.getCode() == ENTER){
                jogObs.jogaPeca();
            }
        });
    }

    private void registaObservador(){
        jogObs.addPropertyChangelistener(PROP_ESTADO, evt -> {atualiza();});
        jogObs.addPropertyChangelistener(PROP_TABULEIRO, evt ->{atualizaTabuleiro();});
        jogObs.addPropertyChangelistener(PROP_JOGADORES, evt ->{atualizaInfoJogadores();});

    }

    private void atualiza(){
        VBox perfilJogadores = new VBox();
        perfilJogadores.setAlignment(Pos.CENTER);
        perfilJogadores.setSpacing(10);
        jogador1 = new Label(jogObs.getJogadores().get(0).toString());
        jogador2 = new Label(jogObs.getJogadores().get(1).toString());
        perfilJogadores.getChildren().addAll(jogador1, jogador2);
        jogadorAtual = new Label(" " + jogObs.getJogadorAtual().getNome() + " ");
        jogadorAtual.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, null, new BorderWidths(3))));
        centro.getChildren().addAll(perfilJogadores, tabuleiro, jogadorAtual);

        if(jogObs.getJogadorAtual() instanceof JogadorHumano){
           comandos.setVisible(true);
        } else if (jogObs.getJogadorAtual() instanceof JogadorVirtual) {
            comandos.setVisible(true);
            opcoes.setVisible(false);
            jogadorVirtual.setVisible(true);
        }

        this.setVisible(jogObs.getSituacaoAtual() == Situacao.AguardaJogada);
    }

    private void atualizaTabuleiro(){
        for(int i = 0; i < jogObs.getTabuleiro().length; i++){
            for (int j = 0; j < jogObs.getTabuleiro()[0].length; j++){
                if(jogObs.getTabuleiro()[i][j] == 'X'){
                    ((Circle)tabuleiro.getChildren().get(i * jogObs.getTabuleiro()[0].length + j)).setFill(Color.YELLOW);
                }
                else if(jogObs.getTabuleiro()[i][j] == '0'){
                    ((Circle)tabuleiro.getChildren().get(i * jogObs.getTabuleiro()[0].length + j)).setFill(Color.DARKBLUE);
                }
            }
        }
    }

    private void atualizaInfoJogadores(){
        jogador1.setText(jogObs.getJogadores().get(0).toString());
        jogador2.setText(jogObs.getJogadores().get(1).toString());
        jogadorAtual.setText(" " + jogObs.getJogadorAtual().getNome() + " ");
        if(jogObs.getJogadorAtual() instanceof JogadorHumano){
            comandos.setVisible(true);
            opcoes.setVisible(true);
            jogadorVirtual.setVisible(false);
        }else if(jogObs.getJogadorAtual() instanceof JogadorVirtual){
            comandos.setVisible(true);
            opcoes.setVisible(false);
            jogadorVirtual.setVisible(true);
        }
    }

}
