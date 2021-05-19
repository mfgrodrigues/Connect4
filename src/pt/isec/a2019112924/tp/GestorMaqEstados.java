package pt.isec.a2019112924.tp;

import pt.isec.a2019112924.tp.jogo.logica.dados.IMiniJogo;
import pt.isec.a2019112924.tp.jogo.logica.dados.Jogador;
import pt.isec.a2019112924.tp.jogo.logica.memento.CareTaker;
import pt.isec.a2019112924.tp.jogo.logica.memento.MaqEstadosOriginator;
import pt.isec.a2019112924.tp.jogo.utils.Situacao;

import java.util.List;

public class GestorMaqEstados {
    private MaqEstadosOriginator originator;
    private CareTaker careTaker;

    public GestorMaqEstados(){
        originator = new MaqEstadosOriginator();
        careTaker = new CareTaker(originator);
    }

    public void adicionaJogador(String nome){
        originator.adicionaJogador(nome);
    }

    public void iniciaJogo(){
        careTaker.gravaMemento();
        originator.iniciaJogo();
    }

    public void jogaPeca(int coluna){
        careTaker.gravaMemento();
        originator.jogaPeca(coluna);
    }

    public void jogaPecaEspecial(int coluna){
        careTaker.gravaMemento();
        originator.jogaPecaEspecial(coluna);
    }

    public void escolheOpMiniJogo(){
        originator.escolheOpMiniJogo();
    }

    public void resolveCalculo(String resposta){
        originator.resolveCalculo(resposta);
    }

    public void digitaPalavras(String resposta){
        originator.digitaPalavras(resposta);
    }

    public void novaTentativa(){
        originator.novaTentativa();
    }

    public void voltarAtras() {
        careTaker.undo();
    }

    public Situacao getSituacaoAtual(){ return originator.getSituacaoAtual(); }


    public int sorteiaColuna(){ return originator.sorteiaColuna(); }

    public char[][] getTabuleiro(){ return originator.getTabuleiro();}

    public List<Jogador> getJogadores(){ return originator.getJogadores();}

    public Jogador getJogadorAtual(){ return originator.getJogadorAtual();}

    public IMiniJogo getMiniJogo(){ return originator.getMiniJogo(); }

}
