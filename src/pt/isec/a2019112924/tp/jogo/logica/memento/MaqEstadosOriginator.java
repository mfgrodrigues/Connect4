package pt.isec.a2019112924.tp.jogo.logica.memento;

import pt.isec.a2019112924.tp.MaquinaEstados;
import pt.isec.a2019112924.tp.jogo.logica.dados.IMiniJogo;
import pt.isec.a2019112924.tp.jogo.logica.dados.Jogador;
import pt.isec.a2019112924.tp.jogo.logica.dados.Jogo;
import pt.isec.a2019112924.tp.jogo.utils.Situacao;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class MaqEstadosOriginator implements IMementoOriginator, Serializable {
    private static final long serialVersionUID = 1L;

    MaquinaEstados maqEstados;

    public MaqEstadosOriginator(){maqEstados = new MaquinaEstados();}

    @Override
    public Memento saveMemento() throws IOException{
        return new Memento(maqEstados);
    }

    @Override
    public Memento saveMementoJogo() throws IOException{
        return new Memento(maqEstados.getJogo());
    }

    @Override
    public void restoreMemento(Memento memento) throws IOException, ClassNotFoundException {
        maqEstados = (MaquinaEstados) memento.getSnapshot();
    }

    public Jogo getJogo(){ return maqEstados.getJogo();}

    public void iniciaJogo(){maqEstados.iniciaJogo();}

    public void jogaPeca(int coluna) {maqEstados.jogaPeca(coluna);}

    public void jogaPeca() {maqEstados.jogaPeca();}

    public void jogaPecaEspecial(int coluna) {maqEstados.jogaPecaEspecial(coluna);}

    public void escolheOpMiniJogo(){maqEstados.escolheOpMiniJogo();}

    public void resolveCalculo(String resposta){maqEstados.resolveCalculo(resposta);}

    public void digitaPalavras(String resposta){maqEstados.digitaPalavras(resposta);}

    public void novaTentativa(){maqEstados.novaTentativa();}

    public Situacao getSituacaoAtual(){return maqEstados.getSituacaoAtual();}

    public char[][] getTabuleiro(){return maqEstados.getTabuleiro();}

    public List<Jogador> getJogadores(){return maqEstados.getJogadores();}

    public Jogador getJogadorAtual(){return maqEstados.getJogadorAtual();}

    public IMiniJogo getMiniJogo(){return maqEstados.getMiniJogo();}

    public List<String> getLog(){ return maqEstados.getLog();}

    public void clearLog(){ maqEstados.clearLog();}
}
