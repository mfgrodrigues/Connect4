package pt.isec.a2019112924.tp.jogo.iu.gui.estados;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import pt.isec.a2019112924.tp.jogo.iu.gui.recursos.ImageLoader;

public class AguardaInicioPane extends VBox {
    private Label lbTitulo;
    private HBox hBbotoes;
    private Button btnInicia;
    private Button btnCarrega;
    private Button btnReplay;
    private Button btnSair;

    public AguardaInicioPane(){
        criarComponentes();
        disporVista();
    }

    private void criarComponentes(){
        hBbotoes = new HBox();
        btnInicia = new Button("Novo Jogo");
        btnCarrega = new Button("Carregar Jogo");
        btnReplay = new Button("Replay Historico");
        btnSair = new Button("Sair");
    }

    private void disporVista() {
        //TODO resize da image consoante maximizacao e minimizacao
        setAlignment(Pos.CENTER);
        setSpacing(20);

        //Titulo
        Image img = ImageLoader.getImage("connect4.png");
        ImageView imageView = new ImageView(img);
        imageView.setFitHeight(280);
        imageView.setFitWidth(550);
        lbTitulo = new Label("", imageView);
        //Botoes de Opcoes
        hBbotoes.setAlignment(Pos.CENTER);
        hBbotoes.setSpacing(15);
        hBbotoes.getChildren().addAll(btnInicia, btnCarrega, btnReplay, btnSair);

        getChildren().addAll(lbTitulo, hBbotoes);
        setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, null, new BorderWidths(3))));
    }

}
