package pt.isec.a2019112924.tp.jogo.utils;

import java.util.Scanner;

public class Util {
    static Scanner sc = new Scanner(System.in);

    private Util(){}

    public static String pedeString(String pergunta) {
        String resposta;
            System.out.print(pergunta);
            resposta = sc.nextLine();
        return resposta;
    }


}
