package pt.isec.a2019112924.tp.jogo.logica.dados;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class MiniJogoP implements IMiniJogo {
    private static List<String> palavras;
    private static List<String> palavrasSorteadas;
    private int numPalavrasCertas;
    private long comeca;

    public MiniJogoP() {
        palavrasSorteadas = new ArrayList<>();
        palavras = new ArrayList<>();
        if (palavras.isEmpty()) {
            acrescentaPalavras();
        }
        numPalavrasCertas = 0;
        comeca = System.currentTimeMillis();
        sorteiaPergunta();
    }

    public void acrescentaPalavras() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("palavras.txt"));
            String str;
            while ((str = br.readLine()) != null) {
                palavras.add(str);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sorteiaPergunta(){
        for(int i = 0; i < 5; i ++) {
            Random rand = new Random();
            System.out.println(palavrasSorteadas.add(palavras.get(rand.nextInt(palavras.size()))));
        }
    }

    @Override
    public String getPergunta() {
        StringBuilder sb = new StringBuilder();
        if (!palavrasSorteadas.isEmpty()) {
            Iterator<String> it = palavrasSorteadas.iterator();

            while (it.hasNext()) {
                String plv = it.next();
                sb.append(plv + " ");
            }
        }
        else{
            System.out.println("Palavras sorteadas encontra-se vazio.");
        }
        return sb.toString();
    }

    @Override
    public boolean validaResposta(String resposta) {
        long tempo = System.currentTimeMillis() - comeca;
        return false;
    }

    @Override
    public boolean getJogoTerminou() {
        return false;
    }
}
