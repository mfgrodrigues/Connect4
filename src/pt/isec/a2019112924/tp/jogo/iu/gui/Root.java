package pt.isec.a2019112924.tp.jogo.iu.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

import static pt.isec.a2019112924.tp.jogo.iu.gui.ConstantesGUI.ALTURA;
import static pt.isec.a2019112924.tp.jogo.iu.gui.ConstantesGUI.LARGURA;

// Base
public class Root extends BorderPane {
    private MenuItem novoJogoMI;
    private PrincipalPane principalPane;

    public Root(){
        criarLayout();
        menus();
        //registarObservadores();

    }

    void changeBackground(Region region, Color color){
        region.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    private void criarLayout(){
        int dimMin = Math.min(670,330)- 2 * 10;

        BackgroundSize backgroundSize = new BackgroundSize(dimMin, dimMin, false, false, false, false);
        changeBackground(Root.this, Color.AQUAMARINE);

   /*     GridPane tabuleiro = new GridPane();
        tabuleiro.setVgap(10);
        tabuleiro.setHgap(10);
        for(int i = 0; i < LARGURA; i++){
            for (int j = 0; j < ALTURA; j++){
                Pane pane = new Pane();
                pane.setPrefSize(45,45);
                changeBackground(pane, Color.BURLYWOOD);
                tabuleiro.add(pane, i, j);
            }
        }
        tabuleiro.setAlignment(Pos.CENTER);
        this.setCenter(tabuleiro);*/
        principalPane = new PrincipalPane();
        setCenter(principalPane);
    }

    private void menus(){
        MenuBar menuBar = new MenuBar();
        setTop(menuBar);

        // menu jogo
        Menu menuJogo = new Menu("Jogo");

        novoJogoMI = new MenuItem("Novo jogo");
        novoJogoMI.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));

        MenuItem lerObjMI = new MenuItem("Carregar jogo");
        lerObjMI.setAccelerator(new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN));

        MenuItem gravarObjMI = new MenuItem("Gravar jogo");
        gravarObjMI.setAccelerator(new KeyCodeCombination(KeyCode.G, KeyCombination.CONTROL_DOWN));

        MenuItem sairMI = new MenuItem("Sair");
        sairMI.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));

        menuJogo.getItems().addAll(novoJogoMI, lerObjMI, gravarObjMI,new SeparatorMenuItem(), sairMI);

        // menu ajuda
        Menu menuAjuda = new Menu("Ajuda");

        MenuItem instrucoesMI = new MenuItem("Instrucoes");
        instrucoesMI.setAccelerator(new KeyCodeCombination(KeyCode.J, KeyCombination.CONTROL_DOWN));

        MenuItem acercaMI = new MenuItem("Acerca");
        acercaMI.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN));

        menuAjuda.getItems().addAll(instrucoesMI, acercaMI);

        menuBar.getMenus().addAll(menuJogo,menuAjuda);
    }
}
