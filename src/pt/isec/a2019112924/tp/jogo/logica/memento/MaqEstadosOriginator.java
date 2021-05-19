package pt.isec.a2019112924.tp.jogo.logica.memento;

import pt.isec.a2019112924.tp.MaquinaEstados;
import pt.isec.a2019112924.tp.jogo.logica.dados.IMiniJogo;
import pt.isec.a2019112924.tp.jogo.logica.dados.Jogador;
import pt.isec.a2019112924.tp.jogo.utils.Situacao;

import java.io.IOException;
import java.util.List;

public class MaqEstadosOriginator implements IMementoOriginator{
    MaquinaEstados maqEstados;

    public MaqEstadosOriginator(){maqEstados = new MaquinaEstados();}

    @Override
    public Memento saveMemento() throws IOException{
        return new Memento(maqEstados);
    }

    @Override
    public void restoreMemento(Memento memento) throws IOException, ClassNotFoundException {
        maqEstados = (MaquinaEstados) memento.getSnapshot();
    }

    public void adicionaJogador(String nome){maqEstados.adicionaJogador(nome);}

    public void iniciaJogo(){maqEstados.iniciaJogo();}

    public void jogaPeca(int coluna) {maqEstados.jogaPeca(coluna);}

    public void jogaPecaEspecial(int coluna) {maqEstados.jogaPecaEspecial(coluna);}

    public void escolheOpMiniJogo(){ maqEstados.escolheOpMiniJogo();}

    public void resolveCalculo(String resposta){ maqEstados.resolveCalculo(resposta); }

    public void digitaPalavras(String resposta){ maqEstados.digitaPalavras(resposta); }

    public void novaTentativa(){ maqEstados.novaTentativa(); }

    public Situacao getSituacaoAtual(){ return maqEstados.getSituacaoAtual(); }

    public int sorteiaColuna(){ return maqEstados.sorteiaColuna(); }

    public char[][] getTabuleiro(){ return maqEstados.getTabuleiro();}

    public List<Jogador> getJogadores(){ return maqEstados.getJogadores();}

    public Jogador getJogadorAtual(){
        return maqEstados.getJogadorAtual();
    }

    public IMiniJogo getMiniJogo(){ return maqEstados.getMiniJogo(); }
}
