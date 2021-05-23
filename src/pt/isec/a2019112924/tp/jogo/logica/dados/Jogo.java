package pt.isec.a2019112924.tp.jogo.logica.dados;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Jogo implements Serializable {
    private static final long serialVersionUID = 1L;

    final static int ALTURA = 6;
    final static int LARGURA = 7;

    private List<Jogador> jogadores;
    private char[][] tabuleiro;
    private boolean vencedor = false;
    private Jogador jogadorAtual;
    private IMiniJogo miniJogo;
    private int miniJogoAtivo;

    //Log
    private List<String> log = new ArrayList<>();

    public void addLog(String str){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");;
        log.add(dtf.format(LocalDateTime.now()) + ": " + str);
    }

    public void setLog(List<String> log){ this.log = log;}

    public List<String>getLog(){
        return log;
    }

    public void clearLog(){
        log.clear();
    }

    //Jogo
    public Jogo() {
        jogadores = new ArrayList<>(2);
        tabuleiro = new char[ALTURA][LARGURA];
    }

    public void adicionaJogador(String nome) {
        jogadores.add(new JogadorHumano(nome));
    }

    public void eliminaJogadores(){
        jogadores.clear();
    }

    public void inicia() {
        if (jogadores.isEmpty()) {
            while (jogadores.size() != 2) {
                jogadores.add(new JogadorVirtual());
            }
        } else if (jogadores.size() == 1) {
            jogadores.add(new JogadorVirtual());
        }
        preparaPecas();
        preparaTabuleiro();
        miniJogoAtivo = 0;
        jogadorAtual = jogadores.get((int)(Math.random() * 1));
    }

    private void preparaPecas() {
        jogadores.get(0).setPeca('X');
        jogadores.get(1).setPeca('0');
    }

    private void preparaTabuleiro() {
        for (int i = 0; i < ALTURA; i++) {
            for (int j = 0; j < LARGURA; j++) {
                tabuleiro[i][j] = '_';
            }
        }
    }

    public char[][] getTabuleiro() {
        return tabuleiro;
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public Jogador getJogadorAtual() {
        return jogadorAtual;
    }

    public void trocaJogador() {
        if(jogadorAtual.getNome().equals(jogadores.get(0).getNome())) {
            jogadorAtual = jogadores.get(1);
        }else if(jogadorAtual.getNome().equals(jogadores.get(1).getNome())){
            jogadorAtual = jogadores.get(0);}
    }

    public int sorteiaColuna() {
        Random rand = new Random();
        return rand.nextInt(LARGURA);
    }

    public boolean colocaPeca(int coluna) {
        if (coluna < 0 || coluna >= LARGURA) {
            return false;
        } else if (tabuleiro[0][coluna] != '_') {
            return false;
        }

        for (int linha = tabuleiro.length - 1; linha >= 0; linha--) {
            if (tabuleiro[linha][coluna] == '_') {
                tabuleiro[linha][coluna] = jogadorAtual.getPeca();
                jogadorAtual.aumentaNrJogada();
                return true;
            }
        }
        return false;
    }

    public boolean colocaPecaEspecial(int coluna) {
        if (coluna < 0 || coluna >= LARGURA) {
            return false;
        } else if (tabuleiro[0][coluna] != '_') {
            return false;
        }
        for (int linha = tabuleiro.length - 1; linha >= 0; linha--) {
            if (tabuleiro[linha][coluna] == '_') {
                for (int i = linha + 1; i < tabuleiro.length; i++) {
                    tabuleiro[i][coluna] = '_';
                }
                jogadorAtual.aumentaNrJogada();
                return true;
            }
        }
        return false;
    }

    public boolean avaliaVencedor(){
        for(int i = 0; i<ALTURA; i++){
            for (int j = 0;j < LARGURA - 3;j++){
                if (tabuleiro[i][j] == jogadorAtual.getPeca()   &&
                        tabuleiro[i][j+1] == jogadorAtual.getPeca() &&
                        tabuleiro[i][j+2] == jogadorAtual.getPeca() &&
                        tabuleiro[i][j+3] == jogadorAtual.getPeca()){
                    return true;
                }
            }
        }
        for(int i = 0; i < ALTURA - 3; i++){
            for(int j = 0; j < LARGURA; j++){
                if (tabuleiro[i][j] == jogadorAtual.getPeca()   &&
                        tabuleiro[i+1][j] == jogadorAtual.getPeca() &&
                        tabuleiro[i+2][j] == jogadorAtual.getPeca() &&
                        tabuleiro[i+3][j] == jogadorAtual.getPeca()){
                    return true;
                }
            }
        }
        for(int i = 3; i < ALTURA; i++){
            for(int j = 0; j < LARGURA - 3; j++){
                if (tabuleiro[i][j] == jogadorAtual.getPeca()   &&
                        tabuleiro[i-1][j+1] == jogadorAtual.getPeca() &&
                        tabuleiro[i-2][j+2] == jogadorAtual.getPeca() &&
                        tabuleiro[i-3][j+3] == jogadorAtual.getPeca()){
                    return true;
                }
            }
        }
        for(int i = 0; i < ALTURA - 3; i++){
            for(int j = 0; j < LARGURA - 3; j++){
                if (tabuleiro[i][j] == jogadorAtual.getPeca()   &&
                        tabuleiro[i+1][j+1] == jogadorAtual.getPeca() &&
                        tabuleiro[i+2][j+2] == jogadorAtual.getPeca() &&
                        tabuleiro[i+3][j+3] == jogadorAtual.getPeca()){
                    return true;
                }
            }
        }
        return false;
    }

    public void iniciaMiniJogo() {
        switch (miniJogoAtivo) {
            case 0:
                miniJogoAtivo = 1;
                System.out.println(miniJogoAtivo);
                miniJogo = new MiniJogoC();
                break;
            case 1:
                miniJogoAtivo = 0;
                System.out.println(miniJogoAtivo);
                miniJogo = new MiniJogoP();
                break;
        }
    }

    public int getMiniJogoAtivo() {
        return miniJogoAtivo;
    }

    public IMiniJogo getMiniJogo() {
        return miniJogo;
    }
}
