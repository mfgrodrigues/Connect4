package pt.isec.a2019112924.tp.jogo.logica.dados;

import java.io.Serializable;

public abstract class Jogador implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;
    private int nrCreditos;
    private int nrJogadas;
    private int nrPecasEspeciais;
    private char peca;

    public Jogador(String nome) {
        this.nome = nome;
        nrCreditos = 5;
        nrJogadas = 0;
        nrPecasEspeciais = 0;
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

    public int getNrCreditos() {
        return nrCreditos;
    }

    public int getNrPecasEspeciais() {
        return nrPecasEspeciais;
    }

    public void setPeca(char peca) {
        this.peca = peca;
    }

    public void setNrCreditos(int nrCreditos) {
        this.nrCreditos = nrCreditos;
    }

    public void setNrJogadas(int nrJogadas) {
        this.nrJogadas = nrJogadas;
    }

    public void setNrPecasEspeciais(int nrPecasEspeciais) {
        this.nrPecasEspeciais = nrPecasEspeciais;
    }

    public void incrementaPecasEspeciais() {
        nrPecasEspeciais++;
    }

    public void aumentaNrJogada() {
        nrJogadas++;
    }

    public String toString() {
        return "Nome: " + nome + ", Nr.Creditos: " + nrCreditos + ", Nr.Pecas Especiais: " + nrPecasEspeciais + ", Nr.Jogadas: " + nrJogadas;
    }

}
