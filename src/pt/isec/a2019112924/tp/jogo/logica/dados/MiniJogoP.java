package pt.isec.a2019112924.tp.jogo.logica.dados;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringJoiner;

public class MiniJogoP implements IMiniJogo, Serializable {
    private static List<String> palavras = new ArrayList<>();
    private static String palavrasSorteadas;
    private int numPalavrasCertas;
    private long comeca;
    private boolean terminou;
    private boolean ganhou;

    public MiniJogoP() {
        if (palavras.isEmpty()) {
            acrescentaPalavras();
        }
        terminou = false;
        numPalavrasCertas = 0;
        ganhou = false;
        sorteiaPergunta();
    }

    public void acrescentaPalavras() {

        try (BufferedReader br = new BufferedReader(new FileReader("palavras.txt"))) {
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

    private void sorteiaPergunta() {
        StringJoiner sj = new StringJoiner(" ");
        Random rand = new Random();

        for (int i = 0; i < 5; i++) {
            sj.add(palavras.get(rand.nextInt(palavras.size())));
        }
        palavrasSorteadas = sj.toString();
    }

    @Override
    public String getPergunta() {
        comeca = System.currentTimeMillis();
        return palavrasSorteadas;
    }

    @Override
    public boolean validaResposta(String resposta) {
        long tempo = (System.currentTimeMillis() - comeca) / 1000;
        terminou = true;
        if(resposta.equals(palavrasSorteadas) && tempo <= palavrasSorteadas.length() / 2){
            ganhou = true;
        }
        return ganhou;
    }

    @Override
    public boolean getJogoTerminou() {
        return terminou;
    }

    @Override
    public boolean ganhou() {
        return ganhou;
    }
}
