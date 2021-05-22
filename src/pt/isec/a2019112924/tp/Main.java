package pt.isec.a2019112924.tp;

import pt.isec.a2019112924.tp.jogo.iu.texto.IU;

public class Main {
    public static void main(String []args){
        Gestor MEJogo = new Gestor();
        IU vistaJogo = new IU(MEJogo);
        vistaJogo.corre();

    }
}
