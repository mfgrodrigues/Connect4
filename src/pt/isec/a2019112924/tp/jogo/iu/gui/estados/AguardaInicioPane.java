package pt.isec.a2019112924.tp.jogo.iu.gui.estados;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import pt.isec.a2019112924.tp.jogo.iu.gui.recursos.ImageLoader;
import pt.isec.a2019112924.tp.jogo.logica.JogoObservavel;
import pt.isec.a2019112924.tp.jogo.utils.Situacao;

import static pt.isec.a2019112924.tp.jogo.logica.PropsID.PROP_ESTADO;

public class AguardaInicioPane extends VBox {
    private JogoObservavel jogObs;

    private Label lbTitulo;
    private HBox hBbotoes, hBJogadores, hBDados;
    private Button btnInicia, btnCarrega, btnReplay, btnSair;
    private Button btnJog1, btnJog2, btnJogV;
    private TextField tfNome;
    private Button btnAvanca;

    public AguardaInicioPane(JogoObservavel jogObs){
        this.jogObs = jogObs;
        criaComponentes();
        dispoeVista();
        registaListener();
        registaObservador();
        atualiza();
    }

    private void criaComponentes(){
        hBbotoes = new HBox();
        btnInicia = new Button("Novo Jogo");
        btnCarrega = new Button("Carregar Jogo");
        btnReplay = new Button("Replay Historico");
        btnSair = new Button("Sair");

        hBJogadores = new HBox();
        btnJog1 = new Button("1 Jogador");
        btnJog2 = new Button("2 Jogadores");
        btnJogV = new Button("Jogo Virtual");

        hBDados = new HBox();
        tfNome = new TextField();
        btnAvanca = new Button("Avancar");

    }

    private void dispoeVista() {
        //TODO resize da image consoante maximizacao e minimizacao
        setAlignment(Pos.CENTER);
        setSpacing(20);

        //Titulo
        Image img = ImageLoader.getImage("connect4.png");
        ImageView imageView = new ImageView(img);
        imageView.setFitHeight(280);
        imageView.setFitWidth(650);
        lbTitulo = new Label("", imageView);

        StackPane opcoes = new StackPane();
        //Botoes de Opcoes
        hBbotoes.setAlignment(Pos.CENTER);
        hBbotoes.setSpacing(15);
        hBbotoes.getChildren().addAll(btnInicia, btnCarrega, btnReplay, btnSair);
        hBbotoes.setVisible(true);

        //Botoes Jogadores
        hBJogadores.setAlignment(Pos.CENTER);
        hBJogadores.setSpacing(15);
        hBJogadores.getChildren().addAll(btnJog1, btnJog2, btnJogV);
        hBJogadores.setVisible(false);

        //Insercao de Dados
        hBDados.setAlignment(Pos.CENTER);
        hBDados.setSpacing(15);
        hBDados.getChildren().addAll(new Label("Jogador:"), tfNome, btnAvanca);
        hBDados.setVisible(false);

        opcoes.getChildren().addAll(hBbotoes, hBJogadores, hBDados);

        //BotoesJogadores
        getChildren().addAll(lbTitulo, opcoes);
        setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, null, new BorderWidths(3))));
    }

    private void registaObservador(){
        jogObs.addPropertyChangelistener(PROP_ESTADO, evt -> {atualiza();});
    }

    public void registaListener(){
        btnInicia.setOnAction((e)->{
            hBbotoes.setVisible(false);
            hBJogadores.setVisible(true);
        });

        btnJog1.setOnAction((e)->{
            hBJogadores.setVisible(false);
            hBDados.setVisible(true);
        });

        btnJog2.setOnAction((e)->{

        });

        btnJogV.setOnAction((e)->{
            jogObs.iniciaJogo();
        });

        btnAvanca.setOnAction((e)->{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            if(tfNome.getText().isEmpty()){
                alert.setContentText("Insira um nome");
                alert.show();
            }
            else if(!jogObs.adicionaJogador(tfNome.getText())){
                alert.setContentText("Nome de jogador ja existe");
                alert.show();
            }
            else {
                jogObs.iniciaJogo();
            }
        });

    }

    public void atualiza(){
        this.setVisible(jogObs.getSituacaoAtual() == Situacao.AguardaInicio);
    }

}
