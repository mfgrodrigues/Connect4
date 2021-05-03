package pt.isec.a2019112924.tp.jogo.logica.dados;

import java.util.ArrayList;
import java.util.List;

public class Jogo {
    final static int ALTURA = 6;
    final static int LARGURA = 7;

    private List<Jogador> jogadores;
    private char[][] tabuleiro;

    private boolean vencedor = false;
    private Jogador jogadorAtual;

    public Jogo(){
        jogadores = new ArrayList<>(2);
        tabuleiro = new char[ALTURA][LARGURA];
    }

    public boolean adicionaJogador(String nome){
        jogadores.add(new JogadorHumano(nome));
        return true;
    }

    public void inicia(){
        if(jogadores.size() == 0){
            while(jogadores.size() != 2){
                jogadores.add(new JogadorVirtual());
            }
        }
        else if(jogadores.size() == 1) {
            jogadores.add(new JogadorVirtual());
        }
        preparaPecas();
        preparaTabuleiro();
        jogadorAtual = jogadores.get((int)(Math.random() * 1));
    }

    private void preparaPecas(){
        jogadores.get(0).setPeca('X');
        jogadores.get(1).setPeca('0');
    }

    private void preparaTabuleiro(){
        for(int i = 0; i < ALTURA; i++){
            for(int j = 0; j < LARGURA; j++){
                tabuleiro[i][j] = '_';
            }
        }
    }

    public char[][] getTabuleiro(){
        return tabuleiro;
    }

    public List<Jogador> getJogadores(){
        return jogadores;
    }

    public Jogador getJogadorAtual(){
        return jogadorAtual;
    }

    public void trocaJogador(){
        jogadorAtual = jogadores.get(1-jogadores.indexOf(jogadorAtual));
    }

    public int sorteiaColuna(){
        return (int)(Math.random() * 6) + 1;
    }

    public boolean colocaPeca(int coluna){
        if(coluna < 0 || coluna - 1 > tabuleiro[0].length) {
            return false;
        } else if(tabuleiro[0][coluna] != '_'){
            return false;
        }

        for (int linha = tabuleiro.length-1; linha >= 0; linha--){
            if(tabuleiro[linha][coluna - 1] == '_'){
                tabuleiro[linha][coluna - 1] = jogadorAtual.getPeca();
                jogadorAtual.aumentaNrJogada();
                return true;
            }
        }
        return false;
    }

    public boolean avaliaVencedor(){

        for(int i = 0; i < tabuleiro.length; i++){
            for (int j = 0;j < tabuleiro[0].length - 3; j++){
                if (tabuleiro[i][j] == jogadorAtual.getPeca()   &&
                        tabuleiro[i][i+1] == jogadorAtual.getPeca() &&
                        tabuleiro[i][i+2] == jogadorAtual.getPeca() &&
                        tabuleiro[i][i+3] == jogadorAtual.getPeca()){
                    return true;
                }
                else if (tabuleiro[i][j] == jogadorAtual.getPeca()  &&
                        tabuleiro[i-1][j+1] == jogadorAtual.getPeca() &&
                        tabuleiro[i-2][j+2] == jogadorAtual.getPeca() &&
                        tabuleiro[i-3][j+3] == jogadorAtual.getPeca()){
                    return true;

                }
            }
        }

        for(int i = 0; i < tabuleiro.length - 3; i++){
            for(int j = 0; j < tabuleiro[0].length; j++){
                if (tabuleiro[i][j] == jogadorAtual.getPeca()   &&
                        tabuleiro[i+1][j] == jogadorAtual.getPeca()  &&
                        tabuleiro[i+2][j] == jogadorAtual.getPeca()  &&
                        tabuleiro[i+3][j] == jogadorAtual.getPeca() ){
                    return true;
                }
                else if(tabuleiro[i][j] == jogadorAtual.getPeca()   &&
                        tabuleiro[i+1][j+1] == jogadorAtual.getPeca()  &&
                        tabuleiro[i+2][j+2] == jogadorAtual.getPeca() &&
                        tabuleiro[i+3][j+3] == jogadorAtual.getPeca() ){
                    return true;
                }
            }
        }
        return false;
    }





}
