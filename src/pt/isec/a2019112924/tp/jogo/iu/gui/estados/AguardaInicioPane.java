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
import javafx.stage.FileChooser;
import pt.isec.a2019112924.tp.jogo.iu.gui.recursos.ImageLoader;
import pt.isec.a2019112924.tp.jogo.logica.JogoObservavel;
import pt.isec.a2019112924.tp.jogo.utils.Situacao;

import java.io.File;

import static com.sun.tools.attach.VirtualMachine.list;
import static pt.isec.a2019112924.tp.jogo.iu.gui.ConstantesGUI.FICHEIRO_CARREGAJOGO;
import static pt.isec.a2019112924.tp.jogo.iu.gui.ConstantesGUI.IMAGEM;
import static pt.isec.a2019112924.tp.jogo.iu.gui.recursos.PropsID.*;

public class AguardaInicioPane extends VBox {
    private JogoObservavel jogObs;

    private Label lbTitulo;
    private HBox hBbotoes, hBJogadores, hBDados;
    private Button btnInicia, btnCarrega, btnReplay;
    private Button btnJog1, btnJog2, btnJogV;
    private TextField tfNome;
    private Button btnAvanca;
    private int nrJogadores, jogadoresAdicionados;

    public AguardaInicioPane(JogoObservavel jogObs) {
        this.jogObs = jogObs;
        nrJogadores = 0;
        jogadoresAdicionados = 0;
        criaComponentes();
        dispoeVista();
        registaListener();
        registaObservador();
        atualiza();
    }

    private void criaComponentes() {
        hBbotoes = new HBox();
        btnInicia = new Button("Novo Jogo");
        btnCarrega = new Button("Carregar Jogo");
        btnReplay = new Button("Replay Histórico");

        hBJogadores = new HBox();
        btnJog1 = new Button("1 Jogador");
        btnJog2 = new Button("2 Jogadores");
        btnJogV = new Button("Jogo Virtual");

        hBDados = new HBox();
        tfNome = new TextField();
        btnAvanca = new Button("Avançar");

    }

    private void dispoeVista() {
        setAlignment(Pos.CENTER);
        setSpacing(20);

        //Titulo
        Image img = ImageLoader.getImage(IMAGEM);
        ImageView imageView = new ImageView(img);
        imageView.setFitHeight(280);
        imageView.setFitWidth(650);
        lbTitulo = new Label("", imageView);

        StackPane opcoes = new StackPane();
        //Botoes de Opcoes
        hBbotoes.setAlignment(Pos.CENTER);
        hBbotoes.setSpacing(15);
        hBbotoes.getChildren().addAll(btnInicia, btnCarrega, btnReplay);
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

    private void registaObservador() {
        jogObs.addPropertyChangelistener(PROP_ESTADO, evt -> {
            atualiza();
        });
        jogObs.addPropertyChangelistener(PROP_STARTREPLAY, evt -> {
            this.setVisible(false);
        });
        jogObs.addPropertyChangelistener(PROP_STOPREPLAY, evt -> {
            this.setVisible(true);
        });
    }

    public void registaListener() {
        btnInicia.setOnAction(e -> {
            hBbotoes.setVisible(false);
            hBJogadores.setVisible(true);
        });

        btnCarrega.setOnAction(e->{
            if(!jogObs.loadEstadoJogoFicheiro(FICHEIRO_CARREGAJOGO)){
                Alert carregaJogo = new Alert(Alert.AlertType.ERROR, "Não existem jogos guardados");
                carregaJogo.show();
                return;
            }
        });

        btnReplay.setOnAction(e->{
            File file = new File("./replays");
            FileChooser chooser = new FileChooser();
            chooser.setInitialDirectory(file);
            chooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("replays", "*.bin")
            );
            File fichSelecionado = chooser.showOpenDialog(null);
            String path;
            if(fichSelecionado != null){
                path = fichSelecionado.getPath();
                jogObs.loadReplayJogo(path);
            }
        });

        btnJog1.setOnAction(e -> {
            nrJogadores = 1;
            hBJogadores.setVisible(false);
            hBDados.setVisible(true);
        });

        btnJog2.setOnAction(e -> {
            nrJogadores = 2;
            hBJogadores.setVisible(false);
            hBDados.setVisible(true);
        });

        btnJogV.setOnAction(e -> {
            jogObs.iniciaJogo();
        });

        btnAvanca.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            if (tfNome.getText().isEmpty()) {
                alert.setContentText("Insira um nome");
                alert.show();
                return;
            }
            if (!jogObs.adicionaJogador(tfNome.getText())) {
                alert.setContentText("Nome de jogador já existe");
                alert.show();
                return;
            }
            tfNome.clear();
            jogadoresAdicionados++;
            tfNome.requestFocus();
            if(nrJogadores == jogadoresAdicionados){
                jogadoresAdicionados = 0;
                jogObs.iniciaJogo();
            }

        });

    }

    public void atualiza() {
        if (jogObs.getSituacaoAtual() == Situacao.AguardaInicio) {
            this.setVisible(true);
            hBbotoes.setVisible(true);
            hBJogadores.setVisible(false);
            hBDados.setVisible(false);
        } else {
            this.setVisible(false);
        }
    }

}
