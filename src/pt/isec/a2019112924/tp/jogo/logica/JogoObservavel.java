package pt.isec.a2019112924.tp.jogo.logica;

import pt.isec.a2019112924.tp.jogo.logica.dados.IMiniJogo;
import pt.isec.a2019112924.tp.jogo.logica.dados.Jogador;
import pt.isec.a2019112924.tp.jogo.logica.estados.IEstado;
import pt.isec.a2019112924.tp.jogo.utils.Situacao;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

import static pt.isec.a2019112924.tp.jogo.logica.PropsID.*;

public class JogoObservavel {
    private Gestor gestor;
    private Situacao estado;
    private final PropertyChangeSupport propertyChangeSupport;

    public JogoObservavel(Gestor gestor){
        this.gestor = gestor;
        propertyChangeSupport = new PropertyChangeSupport(gestor);
        estado = Situacao.AguardaInicio;
    }

    public void addPropertyChangelistener(String propertyName, PropertyChangeListener listener){
        propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
    }

    public boolean adicionaJogador(String nome){ return gestor.adicionaJogador(nome);}


    public void iniciaJogo(){
        gestor.iniciaJogo();
        propertyChangeSupport.firePropertyChange(PROP_ESTADO, null, null);
    }

    public void jogaPeca(int coluna){
        gestor.jogaPeca(coluna);
        propertyChangeSupport.firePropertyChange(PROP_TABULEIRO, null, null);
        propertyChangeSupport.firePropertyChange(PROP_JOGADORES, null, null);
        /*if(alterEstado(getSituacaoAtual())){
            propertyChangeSupport.firePropertyChange(PROP_ESTADO, null, null);
        }*/
    }

    public  void jogaPeca(){
        gestor.jogaPeca();
        propertyChangeSupport.firePropertyChange(PROP_TABULEIRO, null, null);
        propertyChangeSupport.firePropertyChange(PROP_JOGADORES, null, null);
        /*if(alterEstado(getSituacaoAtual())){
            propertyChangeSupport.firePropertyChange(PROP_ESTADO, null, null);
        }*/

    }

    public void jogaPecaEspecial(int coluna){
        gestor.jogaPecaEspecial(coluna);
        propertyChangeSupport.firePropertyChange(PROP_TABULEIRO, null, null);
        propertyChangeSupport.firePropertyChange(PROP_JOGADORES, null, null);
    }

    public char[][] getTabuleiro(){ return gestor.getTabuleiro();}

    public List<Jogador> getJogadores(){ return gestor.getJogadores();}

    public Jogador getJogadorAtual(){ return gestor.getJogadorAtual();}

    public IMiniJogo getMiniJogo(){ return gestor.getMiniJogo(); }

    public Situacao getSituacaoAtual(){ return gestor.getSituacaoAtual();}

    private boolean alterEstado(Situacao prox){
        Situacao anterior = estado;
        estado = prox;
        if(anterior != prox){
            return true;
        }
        return false;
    }




}
