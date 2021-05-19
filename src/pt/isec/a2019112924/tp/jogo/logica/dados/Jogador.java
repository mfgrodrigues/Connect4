package pt.isec.a2019112924.tp.jogo.logica.dados;

import java.io.Serializable;

public abstract class Jogador implements Serializable {
    private String nome;
    private int nrCreditos;
    private int nrJogadas;
    private int nrPecasEspeciais;
    private char peca;

    public Jogador(String nome){
        this.nome = nome;
        nrCreditos = 5;
        nrJogadas = 0;
        nrPecasEspeciais = 0;
    }
    public void setPeca(char peca){this.peca = peca;}

    public char getPeca(){return peca;}

    public String getNome(){return nome; }

    public void aumentaNrJogada(){nrJogadas++;}

    public String toString(){
        return "Nome: " + nome + ", Nr.Creditos: " + nrCreditos + ", Nr.Jogadas: " + nrJogadas;
    }
}
