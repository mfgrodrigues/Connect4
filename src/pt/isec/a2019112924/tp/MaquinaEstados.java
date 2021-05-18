package pt.isec.a2019112924.tp;

import pt.isec.a2019112924.tp.jogo.logica.dados.IMiniJogo;
import pt.isec.a2019112924.tp.jogo.logica.dados.Jogador;
import pt.isec.a2019112924.tp.jogo.logica.dados.Jogo;
import pt.isec.a2019112924.tp.jogo.logica.estados.AguardaInicio;
import pt.isec.a2019112924.tp.jogo.logica.estados.IEstado;
import pt.isec.a2019112924.tp.jogo.utils.Situacao;

import java.util.List;

public class MaquinaEstados {

    IEstado estadoAtual;
    Jogo jogo;

    public MaquinaEstados(){
        jogo = new Jogo();
        estadoAtual = new AguardaInicio(jogo);
    }

    public void adicionaJogador(String nome){ estadoAtual = estadoAtual.adicionaJogador(nome); }

    public void iniciaJogo(){ estadoAtual = estadoAtual.iniciaJogo(); }

    public int sorteiaColuna(){ return jogo.sorteiaColuna(); }

    public void jogaPeca(int coluna) { estadoAtual = estadoAtual.jogaPeca(coluna); }

    public void jogaPecaEspecial(int coluna) { estadoAtual = estadoAtual.jogaPecaEspecial(coluna); }

    public void escolheOpMiniJogo(){ estadoAtual = estadoAtual.escolheOpMiniJogo();}

    public void resolveCalculo(String resposta){ estadoAtual = estadoAtual.resolveCalculo(resposta); }

    public void digitaPalavras(String resposta){estadoAtual = estadoAtual.digitaPalavras(resposta); }

    public void novaTentativa(){ estadoAtual = estadoAtual.novaTentativa(); }

    public Situacao getSituacaoAtual(){ return estadoAtual.getSituacaoAtual(); }

    public char[][] getTabuleiro(){ return jogo.getTabuleiro();}

    public List<Jogador> getJogadores(){ return jogo.getJogadores();}

    public Jogador getJogadorAtual(){
        return jogo.getJogadorAtual();
    }

    public IMiniJogo getMiniJogo(){ return jogo.getMiniJogo(); }


}
