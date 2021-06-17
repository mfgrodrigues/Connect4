package pt.isec.a2019112924.tp.jogo.iu.gui.estados;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
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
import pt.isec.a2019112924.tp.jogo.iu.gui.recursos.ImageLoader;
import pt.isec.a2019112924.tp.jogo.logica.JogoObservavel;
import pt.isec.a2019112924.tp.jogo.logica.dados.JogadorHumano;
import pt.isec.a2019112924.tp.jogo.logica.dados.JogadorVirtual;
import pt.isec.a2019112924.tp.jogo.utils.Situacao;

import static javafx.scene.input.KeyCode.ENTER;
import static pt.isec.a2019112924.tp.jogo.iu.gui.recursos.ConstantesGUI.*;
import static pt.isec.a2019112924.tp.jogo.iu.gui.recursos.PropsID.*;

public class AguardaJogadaPane extends VBox {
    private JogoObservavel jogObs;

    private VBox vBCentro;
    private HBox hBOpcoes, hBDados;
    private GridPane tabuleiro;
    private Button btnJogaEspecial, btnMiniJogo, btnUndo, btnAvanca;
    private TextField tfTexto;
    private StackPane spComandos = new StackPane();
    private Label lbJogadorAtual, lbJogador1, lbJogador2, lbJogadorVirtual, lbEntrada;
    private boolean jogaPecaEspecial, voltaAtras;
    private ImageView imgAmarelo, imgAzul;


    public AguardaJogadaPane(JogoObservavel jogObs) {
        this.jogObs = jogObs;
        jogaPecaEspecial = false;
        voltaAtras = false;
        criaComponentes();
        dispoeVista();
        registaListeners();
        registaObservador();
    }

    private void criaComponentes() {
        hBOpcoes = new HBox();
        hBDados = new HBox();
        tabuleiro = new GridPane();
        btnJogaEspecial = new Button("Jogar Peca Especial");
        btnMiniJogo = new Button("Jogar Mini Jogo");
        btnUndo = new Button("Voltar Atrás");
        btnAvanca = new Button("Jogar");
        tfTexto = new TextField();
        lbJogador1 = new Label();
        lbJogador2 = new Label();
        lbJogadorAtual = new Label();
        lbJogadorVirtual = new Label("Pressione [ENTER] para avançar");
        lbJogadorVirtual.setVisible(false);
        lbEntrada = new Label();
    }

    private void dispoeVista() {
        //tabuleiro do jogo
        mostraTabuleiro();

        // Comandos Jogador Humano
        hBOpcoes.getChildren().addAll(btnJogaEspecial, btnMiniJogo, btnUndo);
        hBOpcoes.setAlignment(Pos.CENTER);
        hBOpcoes.setSpacing(10);

        //Insercao de dados (coluna)
        hBDados.setAlignment(Pos.CENTER);
        hBDados.setSpacing(15);
        hBDados.getChildren().addAll(lbEntrada, tfTexto, btnAvanca);
        hBDados.setVisible(false);
        spComandos.getChildren().addAll(hBOpcoes, hBDados, lbJogadorVirtual);
        spComandos.setVisible(false);

        //VBOX: Perfil dos Jogadores
        VBox perfilJogadores = new VBox();
        perfilJogadores.setAlignment(Pos.CENTER);
        perfilJogadores.setSpacing(10);
        lbJogador1.setFont(LETRA_JOGO);
        lbJogador2.setFont(LETRA_JOGO);
        perfilJogadores.getChildren().addAll(lbJogador1, lbJogador2);


        //Imagem jogador amarelo
        Image jogAmarelo = ImageLoader.getImage(AMARELO);
        imgAmarelo = new ImageView(jogAmarelo);
        imgAmarelo.setFitHeight(230);
        imgAmarelo.setFitWidth(170);

        //Imagem jogador azul
        Image jogAzul = ImageLoader.getImage(AZUL);
        imgAzul = new ImageView(jogAzul);
        imgAzul.setFitHeight(230);
        imgAzul.setFitWidth(170);


        //HBOX: imagens jogadores + tabuleiro
        HBox hBvistaJogo = new HBox();
        hBvistaJogo.setAlignment(Pos.CENTER);
        hBvistaJogo.setSpacing(40);
        hBvistaJogo.getChildren().addAll(imgAmarelo, tabuleiro, imgAzul);

        //VBOX: perfil jogadores + vista do jogo + label jogador atual + comandos
        setAlignment(Pos.CENTER);
        setSpacing(50);
        getChildren().addAll(perfilJogadores, hBvistaJogo, lbJogadorAtual, spComandos);
    }

    private void registaListeners() {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        btnAvanca.setOnAction(e -> {
            int nBack = 0;
            try {
               nBack = Integer.parseInt(tfTexto.getText());
            }catch(NumberFormatException exception){
                alert.setContentText("Introduza um número válido");
                alert.show();
            }
            if (tfTexto.getText().isEmpty() || nBack <= 0 || nBack > jogObs.getTabuleiro()[0].length) {
                alert.setContentText("Introduza o número de vezes que pretende voltar atrás");
                alert.show();
            } else if (!jogObs.voltarAtras(nBack)) {
                alert.setContentText("Não é possível voltar atrás");
                alert.show();
            }
            voltaAtras = false;
            tfTexto.clear();
            hBDados.setVisible(false);
            if(jogObs.getJogadorAtual() instanceof JogadorHumano) {
                hBOpcoes.setVisible(true);
            }
        });

        btnJogaEspecial.setOnAction(e -> {
            hBOpcoes.setVisible(false);
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
            voltaAtras = true;
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

        final Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Coluna completa");

        for (int i = 0; i < jogObs.getTabuleiro().length; i++) {
            for (int j = 0; j < jogObs.getTabuleiro()[0].length; j++) {
                Circle circulo = new Circle(15, Color.WHITE);
                final int coluna = j;
                circulo.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                    if (jogObs.getJogadorAtual() instanceof JogadorHumano && jogaPecaEspecial && !voltaAtras) {
                        jogObs.jogaPecaEspecial(coluna);
                        jogaPecaEspecial = false;
                    } else if (jogObs.getJogadorAtual() instanceof JogadorHumano && !jogaPecaEspecial && !voltaAtras) {
                        jogObs.jogaPeca(coluna);
                        if (jogObs.getColunaCompleta()) {
                            alert.show();
                        }
                    }
                });

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

    private void configuraJogAtual() {
        lbJogador1.setText(jogObs.getJogadores().get(0).toString());
        lbJogador2.setText(jogObs.getJogadores().get(1).toString());
        lbJogadorAtual.setText(jogObs.getJogadorAtual().getNome().toUpperCase());

        if (jogObs.getJogadorAtual().getPeca() == 'X') {
            imgAmarelo.setVisible(true);
            imgAzul.setVisible(false);
            lbJogadorAtual.setFont(LETRA_JOGO);
            lbJogadorAtual.setTextFill(Color.BLACK);
            lbJogadorAtual.setBackground(new Background((new BackgroundFill(Color.YELLOW, new CornerRadii(10), Insets.EMPTY))));
            lbJogadorAtual.setBorder(new Border(new BorderStroke(Color.YELLOW, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(3))));
        } else {
            imgAmarelo.setVisible(false);
            imgAzul.setVisible(true);
            lbJogadorAtual.setFont(LETRA_JOGO);
            lbJogadorAtual.setTextFill(Color.WHITE);
            lbJogadorAtual.setBackground(new Background((new BackgroundFill(Color.DARKBLUE, new CornerRadii(10), Insets.EMPTY))));
            lbJogadorAtual.setBorder(new Border(new BorderStroke(Color.DARKBLUE, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(3))));
        }
    }
}
