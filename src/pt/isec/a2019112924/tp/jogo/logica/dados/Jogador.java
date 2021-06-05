package pt.isec.a2019112924.tp.jogo.logica.dados;

import java.io.Serializable;

public abstract class Jogador implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;
    private int nrJogadas;
    private char peca;
    private int miniJogoAtivo;

    public Jogador(String nome) {
        this.nome = nome;
        nrJogadas = 0;
        miniJogoAtivo = 0;
    }

    public char getPeca() {
        return peca;
    }

    public String getNome() {
        return nome;
    }

    public int getNrJogadas() {
        return nrJogadas;
    }

    public void setPeca(char peca) {
        this.peca = peca;
    }

    public void setNrJogadas(int nrJogadas) {
        this.nrJogadas = nrJogadas;
    }

    public void aumentaNrJogada() {
        nrJogadas++;
    }

    public int getMiniJogoAtivo() { return miniJogoAtivo; }

    public void setMiniJogoAtivo(int miniJogoAtivo){ this.miniJogoAtivo = miniJogoAtivo;}

    public String toString() {
        return "Nome: " + nome + " Nr.Jogadas: " + nrJogadas;
    }

}
