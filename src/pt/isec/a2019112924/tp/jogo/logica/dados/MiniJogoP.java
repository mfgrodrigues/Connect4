package pt.isec.a2019112924.tp.jogo.logica.dados;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MiniJogoP {
    private List<String> palavrasSorteadas;
    private static List<String> palavras;

    public MiniJogoP(){
        palavrasSorteadas = new ArrayList<>();
        palavras = new ArrayList<>();
    }

    /*public boolean acrescentaPalavras(){
        try{
            File f = new File("palavras.txt");
            FileInputStream fstream = new FileInputStream(f);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String s;

            fstream.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }*/
}
