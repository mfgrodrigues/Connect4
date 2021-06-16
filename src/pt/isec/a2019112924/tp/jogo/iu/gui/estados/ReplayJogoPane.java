package pt.isec.a2019112924.tp.jogo.iu.gui.estados;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
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

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Stack;

import static pt.isec.a2019112924.tp.jogo.iu.gui.recursos.PropsID.*;


public class ReplayJogoPane extends BorderPane {
    private JogoObservavel jogObs;
    private GridPane tabuleiro;
    private Button btnAvanca, btnSair;
    private ListView<String> lista;
    private int tam;

    public ReplayJogoPane(JogoObservavel jogObs) {
        this.jogObs = jogObs;
        criaComponentes();
        dispoeVista();
        registaObservador();
        registaListener();
    }

    private void criaComponentes() {
        tabuleiro = new GridPane();
        btnAvanca = new Button("Avançar");
        btnSair = new Button ("Sair");
        lista = new ListView<>();
    }

    private void dispoeVista() {
        lista.setMaxSize(900, 70);
        lista.setPadding(new Insets(10));
        setMargin(lista, new Insets(10, 10, 10, 10));
        setTop(lista);

        VBox centro = new VBox();
        HBox botoes = new HBox();
        mostraTabuleiro();
        botoes.setAlignment(Pos.CENTER);
        botoes.setSpacing(20);
        botoes.getChildren().addAll(btnAvanca, btnSair);
        centro.setSpacing(30);
        centro.setAlignment(Pos.CENTER);
        centro.getChildren().addAll(tabuleiro, botoes);
        setCenter(centro);
    }

    private void registaListener() {
        btnAvanca.setOnAction(e -> {
            jogObs.avancaReplay();
        });

        btnSair.setOnAction(e->{
            jogObs.terminaReplay();
        });
    }

    private void registaObservador() {
        jogObs.addPropertyChangelistener(PROP_STARTREPLAY, evt -> {
            for (int i = 0; i < jogObs.getTabuleiro().length; i++) {
                for (int j = 0; j < jogObs.getTabuleiro()[0].length; j++) {
                    ((Circle) tabuleiro.getChildren().get(i * jogObs.getTabuleiro()[0].length + j)).setFill(Color.WHITE);
                }
            }
            tam = 0;
            btnAvanca.setDisable(false);
            this.setVisible(true);
        });

        jogObs.addPropertyChangelistener(PROP_AVANCAREPLAY, evt -> {
            System.out.println("estou no avanca replay");
            atualizaTabuleiro();
            atualizaLog();
        });

        jogObs.addPropertyChangelistener(PROP_STACKSIZE, evt -> {
            System.out.println("estou no stack size");
            btnAvanca.setDisable(true);
        });

        jogObs.addPropertyChangelistener(PROP_STOPREPLAY, evt -> {
            System.out.println("estou no stop replay");
            lista.getItems().clear();
            this.setVisible(false);
        });
    }

    private void mostraTabuleiro() {
        tabuleiro.setAlignment(Pos.CENTER);
        tabuleiro.setPrefSize(400.0, 350.0);
        tabuleiro.setMinSize(400.0, 350.0);
        tabuleiro.setMaxSize(400.0, 350.0);
        tabuleiro.setVgap(5);
        tabuleiro.setHgap(5);

        for (int i = 0; i < jogObs.getTabuleiro().length; i++) {
            for (int j = 0; j < jogObs.getTabuleiro()[0].length; j++) {
                Circle circulo = new Circle(25, Color.WHITE);
                tabuleiro.add(circulo, j, i);
            }
        }
        tabuleiro.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, new CornerRadii(10), Insets.EMPTY)));
        tabuleiro.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(3))));
    }

    private void atualizaTabuleiro() {
        System.out.println("entrei aqui");
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

    private void atualizaLog() {
        if(lista == null) return;
        List<String> mensagens = new ArrayList<>();
        for(int i = tam; i < jogObs.getLog().size(); i++){
            mensagens.add(jogObs.getLog().get(i));
        }
        tam = jogObs.getLog().size();
        lista.getItems().clear();
        lista.getItems().addAll(mensagens);
    }
}
