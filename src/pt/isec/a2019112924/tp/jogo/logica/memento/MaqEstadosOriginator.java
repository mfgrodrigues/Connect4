package pt.isec.a2019112924.tp.jogo.logica.memento;

import pt.isec.a2019112924.tp.MaquinaEstados;
import pt.isec.a2019112924.tp.jogo.logica.dados.IMiniJogo;
import pt.isec.a2019112924.tp.jogo.logica.dados.Jogador;
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
        //TODO guardar a informacao jogador
        //TODO so dou reset ao nr de jogadas de quem fez undo
        /*for(int i = 0; i < 2; i++) {
            maqEstados.getJogadores().get(i).getNrJogadas();
            maqEstados.getJogadores().get(i).getNrCreditos();
            maqEstados.getJogadores().get(i).getNrPecasEspeciais();
        }*/
        maqEstados = (MaquinaEstados) memento.getSnapshot();
        for(int i = 0; i < 2; i++) {
            maqEstados.getJogadores().get(i).setNrJogadas(0);
            maqEstados.getJogadores().get(i).setNrCreditos(0);
            maqEstados.getJogadores().get(i).setNrPecasEspeciais(0);
        }
    }

    public void adicionaJogador(String nome){maqEstados.adicionaJogador(nome);}

    public void iniciaJogo(){maqEstados.iniciaJogo();}

    public void jogaPeca(int coluna) {maqEstados.jogaPeca(coluna);}

    public void jogaPeca() {maqEstados.jogaPeca();}

    public void jogaPecaEspecial(int coluna) {maqEstados.jogaPecaEspecial(coluna);}

    public void escolheOpMiniJogo(){maqEstados.escolheOpMiniJogo();}

    public void resolveCalculo(String resposta){maqEstados.resolveCalculo(resposta);}

    public void digitaPalavras(String resposta){maqEstados.digitaPalavras(resposta);}

    public void novaTentativa(){maqEstados.novaTentativa();}

    public Situacao getSituacaoAtual(){return maqEstados.getSituacaoAtual();}

    public int sorteiaColuna(){return maqEstados.sorteiaColuna();}

    public char[][] getTabuleiro(){return maqEstados.getTabuleiro();}

    public List<Jogador> getJogadores(){return maqEstados.getJogadores();}

    public Jogador getJogadorAtual(){return maqEstados.getJogadorAtual();}

    public IMiniJogo getMiniJogo(){return maqEstados.getMiniJogo();}
}
