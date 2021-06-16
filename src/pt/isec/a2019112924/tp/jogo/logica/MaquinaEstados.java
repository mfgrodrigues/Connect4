package pt.isec.a2019112924.tp.jogo.logica;

import pt.isec.a2019112924.tp.jogo.logica.dados.IMiniJogo;
import pt.isec.a2019112924.tp.jogo.logica.dados.Jogador;
import pt.isec.a2019112924.tp.jogo.logica.dados.Jogo;
import pt.isec.a2019112924.tp.jogo.logica.estados.AguardaInicio;
import pt.isec.a2019112924.tp.jogo.logica.estados.IEstado;
import pt.isec.a2019112924.tp.jogo.utils.Situacao;

import java.io.Serializable;
import java.util.List;

public class MaquinaEstados implements Serializable {

    private IEstado estadoAtual;
    private final Jogo jogo;

    public MaquinaEstados(){
        jogo = new Jogo();
        estadoAtual = new AguardaInicio(jogo);
    }

    private void setEstadoAtual(IEstado estadoAtual){this.estadoAtual = estadoAtual;}

    public void iniciaJogo(){ setEstadoAtual(estadoAtual = estadoAtual.iniciaJogo());}

    public void jogaPeca(int coluna) { setEstadoAtual(estadoAtual = estadoAtual.jogaPeca(coluna));}

    public void jogaPeca() { setEstadoAtual(estadoAtual = estadoAtual.jogaPeca());}

    public void jogaPecaEspecial(int coluna) { setEstadoAtual(estadoAtual = estadoAtual.jogaPecaEspecial(coluna));}

    public void escolheOpMiniJogo(){ setEstadoAtual(estadoAtual = estadoAtual.escolheOpMiniJogo());}

    public void resolveCalculo(String resposta){ setEstadoAtual(estadoAtual = estadoAtual.resolveCalculo(resposta));}

    public void digitaPalavras(String resposta){ setEstadoAtual(estadoAtual = estadoAtual.digitaPalavras(resposta));}

    public void novaTentativa(){ setEstadoAtual(estadoAtual = estadoAtual.novaTentativa());}

    public Situacao getSituacaoAtual(){ return estadoAtual.getSituacaoAtual();}

    public char[][] getTabuleiro(){ return jogo.getTabuleiro();}

    public List<Jogador> getJogadores(){ return jogo.getJogadores();}

    public Jogador getJogadorAtual(){ return jogo.getJogadorAtual();}

    public boolean getColunaCompleta(){return jogo.getColunaCompleta();}

    public IMiniJogo getMiniJogo(){ return jogo.getMiniJogo();}

    public List<String> getLog(){ return jogo.getLog();}

    public void clearLog(){ jogo.clearLog();}

    public void addLog(String log){ jogo.addLog(log);}

    public void setLog(List<String> logs){ jogo.setLog(logs);}

    public boolean adicionaJogador(String nome){ return jogo.adicionaJogador(nome);}

}
