package pt.isec.a2019112924.tp.jogo.logica;

import pt.isec.a2019112924.tp.jogo.logica.dados.IMiniJogo;
import pt.isec.a2019112924.tp.jogo.logica.dados.Jogador;
import pt.isec.a2019112924.tp.jogo.utils.Situacao;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

import static pt.isec.a2019112924.tp.jogo.iu.gui.recursos.PropsID.*;

public class JogoObservavel {
    private Gestor gestor;
    private Situacao estado;
    private final PropertyChangeSupport propertyChangeSupport;

    public JogoObservavel(Gestor gestor) {
        this.gestor = gestor;
        propertyChangeSupport = new PropertyChangeSupport(gestor);
        estado = Situacao.AguardaInicio;
    }

    public void addPropertyChangelistener(String propertyName, PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
    }

    public boolean adicionaJogador(String nome) {
        return gestor.adicionaJogador(nome);
    }

    public List<String> getLog() { return gestor.getLog();}

    public void clearLog(){ gestor.clearLog();}

    public void iniciaJogo() {
        gestor.iniciaJogo();
        propertyChangeSupport.firePropertyChange(PROP_LOG, null, null);
        if (alteraEstado(getSituacaoAtual())) {
            propertyChangeSupport.firePropertyChange(PROP_ESTADO, null, null);
        }
    }

    public void jogaPeca(int coluna) {
        gestor.jogaPeca(coluna);
        propertyChangeSupport.firePropertyChange(PROP_LOG, null, null);
        propertyChangeSupport.firePropertyChange(PROP_TABULEIRO, null, null);
        propertyChangeSupport.firePropertyChange(PROP_JOGADORES, null, null);
        if (alteraEstado(getSituacaoAtual())) {
            propertyChangeSupport.firePropertyChange(PROP_ESTADO, null, null);
        }
    }

    public void jogaPeca() {
        gestor.jogaPeca();
        propertyChangeSupport.firePropertyChange(PROP_LOG, null, null);
        propertyChangeSupport.firePropertyChange(PROP_TABULEIRO, null, null);
        propertyChangeSupport.firePropertyChange(PROP_JOGADORES, null, null);
        if (alteraEstado(getSituacaoAtual())) {
            propertyChangeSupport.firePropertyChange(PROP_ESTADO, null, null);
        }

    }

    public void jogaPecaEspecial(int coluna) {
        gestor.jogaPecaEspecial(coluna);
        propertyChangeSupport.firePropertyChange(PROP_LOG, null, null);
        propertyChangeSupport.firePropertyChange(PROP_TABULEIRO, null, null);
        propertyChangeSupport.firePropertyChange(PROP_JOGADORES, null, null);
    }

    public void escolheOpMiniJogo() {
        gestor.escolheOpMiniJogo();
        if (alteraEstado(getSituacaoAtual())) {
            propertyChangeSupport.firePropertyChange(PROP_ESTADO, null, null);
        }
    }

    public void resolveCalculo(String resposta) {
        gestor.resolveCalculo(resposta);
        propertyChangeSupport.firePropertyChange(PROP_MINIJOGO, null, null);
        if (alteraEstado(getSituacaoAtual())) {
            propertyChangeSupport.firePropertyChange(PROP_ESTADO, null, null);
        }
    }

    public void digitaPalavra(String resposta) {
        gestor.digitaPalavras(resposta);
        if (alteraEstado(getSituacaoAtual())) {
            propertyChangeSupport.firePropertyChange(PROP_ESTADO, null, null);
        }
    }

    public void novaTentativa() {
        gestor.novaTentativa();
        if (alteraEstado(getSituacaoAtual())) {
            propertyChangeSupport.firePropertyChange(PROP_ESTADO, null, null);
        }
    }

    public boolean voltarAtras(int nrBaks){
        if (gestor.voltarAtras(nrBaks)){
            propertyChangeSupport.firePropertyChange(PROP_JOGADORES, null, null);
            propertyChangeSupport.firePropertyChange(PROP_TABULEIRO, null, null);
            return true;
        }
        return false;
    }

    public boolean saveEstadoJogoFicheiro(String nomeFich){
        if(gestor.saveEstadoJogoFicheiro(nomeFich)){
            return true;
        }
        return false;
    }

     public boolean loadEstadoJogoFicheiro(String nomeFich){
        if(gestor.loadEstadoJogoFicheiro(nomeFich)){
            if (alteraEstado(getSituacaoAtual())) {
                propertyChangeSupport.firePropertyChange(PROP_ESTADO, null, null);
            }
            return true;
        }
        return false;
     }

     public boolean saveReplayJogo(String nomeFich){
        if(gestor.saveReplayJogo(nomeFich)){
            return true;
        }
        return false;
     }

     public boolean loadReplayJogo(String nomeFich){
        System.out.println(nomeFich);
        if(gestor.loadReplayJogo(nomeFich)){
            propertyChangeSupport.firePropertyChange(PROP_STARTREPLAY, null, null);
            return true;
        }
        return false;
     }

    public  void avancaReplay(){
        gestor.avancaReplay();
        propertyChangeSupport.firePropertyChange(PROP_AVANCAREPLAY, null, null);
        if(gestor.getStackJogoSize() == 0){
            System.out.println("estou aqui");
            propertyChangeSupport.firePropertyChange(PROP_STACKSIZE, null, null);
        }
    }

    public void terminaReplay(){
        propertyChangeSupport.firePropertyChange(PROP_STOPREPLAY, null, null);
    }

    public char[][] getTabuleiro() {
        return gestor.getTabuleiro();
    }

    public List<Jogador> getJogadores() {
        return gestor.getJogadores();
    }

    public Jogador getJogadorAtual() {
        return gestor.getJogadorAtual();
    }

    public IMiniJogo getMiniJogo() {
        return gestor.getMiniJogo();
    }

    public Situacao getSituacaoAtual() {
        return gestor.getSituacaoAtual();
    }

    public boolean getColunaCompleta(){return gestor.getColunaCompleta();}

    private boolean alteraEstado(Situacao prox) {
        Situacao anterior = estado;
        estado = prox;
        System.out.println(estado);
        if (anterior != prox) {
            return true;
        }
        return false;
    }


}
