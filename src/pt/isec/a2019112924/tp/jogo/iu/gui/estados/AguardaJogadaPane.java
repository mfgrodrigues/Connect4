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
import javafx.stage.WindowEvent;
import pt.isec.a2019112924.tp.jogo.logica.JogoObservavel;
import pt.isec.a2019112924.tp.jogo.logica.dados.JogadorHumano;
import pt.isec.a2019112924.tp.jogo.logica.dados.JogadorVirtual;
import pt.isec.a2019112924.tp.jogo.utils.Situacao;

import static javafx.scene.input.KeyCode.ENTER;
import static pt.isec.a2019112924.tp.jogo.logica.PropsID.*;

public class AguardaJogadaPane extends BorderPane {
    private JogoObservavel jogObs;

    private VBox vBCentro;
    private HBox hBOpcoes, hBDados;
    private GridPane tabuleiro;
    private Button btnJoga, btnJogaEspecial, btnMiniJogo, btnUndo, btnAvanca;
    private TextField tfTexto;
    private StackPane spComandos = new StackPane();
    private Label lbJogadorAtual, lbJogador1, lbJogador2, lbJogadorVirtual, lbEntrada;
    private boolean jogaPeca, jogaPecaEspecial;


    public AguardaJogadaPane(JogoObservavel jogObs) {
        this.jogObs = jogObs;
        jogaPeca = false;
        jogaPecaEspecial = false;
        criaComponentes();
        dispoeVista();
        registaListeners();
        registaObservador();
    }

    private void criaComponentes() {
        vBCentro = new VBox(40);
        hBOpcoes = new HBox();
        hBDados = new HBox();
        tabuleiro = new GridPane();
        btnJoga = new Button("Jogar Peca");
        btnJogaEspecial = new Button("Jogar Peca Especial");
        btnMiniJogo = new Button("Jogar Mini Jogo");
        btnUndo = new Button("Voltar Atras");
        btnAvanca = new Button("Jogar");
        tfTexto = new TextField();
        lbJogador1 = new Label();
        lbJogador2 = new Label();
        lbJogadorAtual = new Label();
        lbJogadorVirtual = new Label("Pressione [ENTER] para avancar");
        lbJogadorVirtual.setVisible(false);
        lbEntrada = new Label();
    }

    private void dispoeVista() {
        //tabuleiro do jogo
        mostraTabuleiro();


        // Comandos Jogador Humano
        hBOpcoes.getChildren().addAll(btnJoga, btnJogaEspecial, btnMiniJogo, btnUndo);
        hBOpcoes.setAlignment(Pos.CENTER);
        hBOpcoes.setSpacing(10);
        hBOpcoes.setPrefSize(900.00, 150.00);
        hBOpcoes.setMinSize(900.00, 150.00);

        //Insercao de dados (coluna)
        hBDados.setAlignment(Pos.CENTER);
        hBDados.setSpacing(15);
        hBDados.getChildren().addAll(lbEntrada, tfTexto, btnAvanca);
        hBDados.setVisible(false);
        spComandos.getChildren().addAll(hBOpcoes, hBDados, lbJogadorVirtual);
        spComandos.setVisible(false);
        setBottom(spComandos);

        VBox perfilJogadores = new VBox();
        perfilJogadores.setAlignment(Pos.CENTER);
        perfilJogadores.setSpacing(10);
        perfilJogadores.getChildren().addAll(lbJogador1, lbJogador2);
        //TODO alterar letra
        vBCentro.getChildren().addAll(perfilJogadores, tabuleiro, lbJogadorAtual);
        vBCentro.setAlignment(Pos.CENTER);
        setCenter(vBCentro);
    }

    private void registaListeners() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        btnJoga.setOnAction(e -> {
            lbEntrada.setText("Coluna: ");
            hBOpcoes.setVisible(false);
            hBDados.setVisible(true);
            jogaPeca = true;
        });

        btnAvanca.setOnAction(e -> {
            if (tfTexto.getText().isEmpty() || Integer.parseInt(tfTexto.getText()) <= 0 || Integer.parseInt(tfTexto.getText()) > jogObs.getTabuleiro()[0].length) {
                alert.setContentText("Introduza uma coluna valida");
                alert.show();
                return;
            }
            if(jogaPeca == true) {
                jogObs.jogaPeca(Integer.parseInt(tfTexto.getText()) - 1);
                jogaPeca = false;
            }
            else if(jogaPecaEspecial == true){
                jogObs.jogaPecaEspecial(Integer.parseInt(tfTexto.getText()) - 1);
                jogaPecaEspecial = false;
            }
            else{
                if(!jogObs.voltarAtras(Integer.parseInt(tfTexto.getText()))){
                   alert.setContentText("Nao e possivel voltar atras");
                   alert.show();
                   return;
                }
            }
            tfTexto.clear();
            hBDados.setVisible(false);
        });

        btnJogaEspecial.setOnAction(e -> {
            lbEntrada.setText("Coluna: ");
            hBOpcoes.setVisible(false);
            hBDados.setVisible(true);
            jogaPecaEspecial = true;
        });

        btnMiniJogo.setOnAction(e -> {
            jogObs.escolheOpMiniJogo();
        });

        setFocusTraversable(true);
        setOnKeyPressed(e -> {
            if (e.getCode() == ENTER && jogObs.getJogadorAtual() instanceof JogadorVirtual) {
                jogObs.jogaPeca();
            }
        });

        btnUndo.setOnAction(e -> {
            lbEntrada.setText("Nr. Vezes: ");
            hBOpcoes.setVisible(false);
            hBDados.setVisible(true);
        });
    }

    private void registaObservador() {
        jogObs.addPropertyChangelistener(PROP_ESTADO, evt -> {
            atualiza();
        });
        jogObs.addPropertyChangelistener(PROP_TABULEIRO, evt -> {
            atualizaTabuleiro();
        });
        jogObs.addPropertyChangelistener(PROP_JOGADORES, evt -> {
            atualizaJogadores();
        });

    }

    private void atualiza() {
        if (jogObs.getSituacaoAtual() == Situacao.AguardaJogada) {
            atualizaTabuleiro();
            configuraJogAtual();

            if (jogObs.getJogadorAtual() instanceof JogadorHumano) {
                spComandos.setVisible(true);
                hBOpcoes.setVisible(true);
                lbJogadorVirtual.setVisible(false);
                configurahBOpcoes();
            } else if (jogObs.getJogadorAtual() instanceof JogadorVirtual) {
                spComandos.setVisible(true);
                hBOpcoes.setVisible(false);
                lbJogadorVirtual.setVisible(true);
            }

            this.setVisible(true);
        } else {
            this.setVisible(false);
        }
    }

    private void atualizaTabuleiro() {
        for (int i = 0; i < jogObs.getTabuleiro().length; i++) {
            for (int j = 0; j < jogObs.getTabuleiro()[0].length; j++) {
                if (jogObs.getTabuleiro()[i][j] == 'X') {
                    ((Circle) tabuleiro.getChildren().get(i * jogObs.getTabuleiro()[0].length + j)).setFill(Color.YELLOW);
                } else if (jogObs.getTabuleiro()[i][j] == '0') {
                    ((Circle) tabuleiro.getChildren().get(i * jogObs.getTabuleiro()[0].length + j)).setFill(Color.DARKBLUE);
                } else {
                    ((Circle) tabuleiro.getChildren().get(i * jogObs.getTabuleiro()[0].length + j)).setFill(Color.WHITE);
                }
            }
        }
    }

    private void atualizaJogadores() {

        configuraJogAtual();
        if (jogObs.getJogadorAtual() instanceof JogadorHumano) {
            spComandos.setVisible(true);
            hBOpcoes.setVisible(true);
            lbJogadorVirtual.setVisible(false);
            configurahBOpcoes();
        } else if (jogObs.getJogadorAtual() instanceof JogadorVirtual) {
            spComandos.setVisible(true);
            hBOpcoes.setVisible(false);
            lbJogadorVirtual.setVisible(true);
        }
    }

    private void mostraTabuleiro() {
        tabuleiro.setAlignment(Pos.CENTER);
        tabuleiro.setPadding(new Insets(10, 10, 10, 10));
        tabuleiro.setPrefSize(270.0, 230.0);
        tabuleiro.setMinSize(270.0, 230.0);
        tabuleiro.setMaxSize(270.0, 230.0);
        tabuleiro.setVgap(5);
        tabuleiro.setHgap(5);

        for (int i = 0; i < jogObs.getTabuleiro().length; i++) {
            for (int j = 0; j < jogObs.getTabuleiro()[0].length; j++) {
                Circle circulo = new Circle(15, Color.WHITE);
                tabuleiro.add(circulo, j, i);
            }
        }
        tabuleiro.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, new CornerRadii(10), Insets.EMPTY)));
        tabuleiro.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(3))));
    }

    private void configurahBOpcoes() {
        btnJogaEspecial.setDisable(((JogadorHumano) jogObs.getJogadorAtual()).getNrPecasEspeciais() == 0);
        btnMiniJogo.setDisable(!(jogObs.getJogadorAtual().getNrJogadas() % 4 == 0 && jogObs.getJogadorAtual().getNrJogadas() > 0));
        btnUndo.setDisable(((JogadorHumano) jogObs.getJogadorAtual()).getNrCreditos() == 0);
    }

    private void configuraJogAtual(){
        lbJogador1.setText(jogObs.getJogadores().get(0).toString());
        lbJogador2.setText(jogObs.getJogadores().get(1).toString());
        lbJogadorAtual.setText(" JOGA " + jogObs.getJogadorAtual().getNome().toUpperCase());
        if(jogObs.getJogadorAtual().getPeca() == 'X') {
            lbJogadorAtual.setBorder(new Border(new BorderStroke(Color.YELLOW, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(3))));
        }
        else{
            lbJogadorAtual.setBorder(new Border(new BorderStroke(Color.DARKBLUE, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(3))));

        }
    }

}
