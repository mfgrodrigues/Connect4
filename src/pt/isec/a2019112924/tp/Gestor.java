package pt.isec.a2019112924.tp;

import pt.isec.a2019112924.tp.jogo.logica.dados.IMiniJogo;
import pt.isec.a2019112924.tp.jogo.logica.dados.Jogador;
import pt.isec.a2019112924.tp.jogo.logica.dados.Jogo;
import pt.isec.a2019112924.tp.jogo.logica.memento.CareTaker;
import pt.isec.a2019112924.tp.jogo.logica.memento.MaqEstadosOriginator;
import pt.isec.a2019112924.tp.jogo.logica.memento.Memento;
import pt.isec.a2019112924.tp.jogo.utils.Situacao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class Gestor {
    private MaqEstadosOriginator originator;
    private CareTaker careTaker;

    public Gestor(){
        originator = new MaqEstadosOriginator();
        careTaker = new CareTaker(originator);
    }

    // Maquina de Estados
    public void adicionaJogador(String nome){
        originator.adicionaJogador(nome);
    }

    public void iniciaJogo(){ originator.iniciaJogo();}

    public void jogaPeca(int coluna){
        careTaker.gravaJogo();
        careTaker.gravaMemento();
        originator.jogaPeca(coluna);
    }

    public void jogaPeca() {
        careTaker.gravaJogo();
        careTaker.gravaMemento();
        originator.jogaPeca();
    }

    public void jogaPecaEspecial(int coluna){
        careTaker.gravaJogo();
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

    public Situacao getSituacaoAtual(){ return originator.getSituacaoAtual(); }

    public int sorteiaColuna(){ return originator.sorteiaColuna(); }

    public char[][] getTabuleiro(){ return originator.getTabuleiro();}

    public List<Jogador> getJogadores(){ return originator.getJogadores();}

    public Jogador getJogadorAtual(){ return originator.getJogadorAtual();}

    public IMiniJogo getMiniJogo(){ return originator.getMiniJogo(); }

    // CareTaker
    public boolean voltarAtras(int nrBacks) {
        if (nrBacks <= originator.getJogadorAtual().getNrCreditos()) {
            for(int i = 0; i < nrBacks; i++) {
                careTaker.undo();
            }
            return true;
        }
        return false;
    }

    public void loadReplayJogo(String nomeFich) {careTaker.loadReplay(nomeFich);}

    public void saveReplayJogo(String nomeFich) {careTaker.saveReplay(nomeFich);}

    public Jogo avancaReplay(){ return careTaker.replayJogo();}

    public int getStackJogoSize(){ return careTaker.getStackJogo().size();}

    public void saveEstadoJogoFicheiro(String nomeFich){
        try{
            File f = new File(nomeFich);
            f.createNewFile();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
            oos.writeObject(originator);
            oos.writeObject(careTaker);
            oos.close();
            System.out.println("Jogo gravado com sucesso");
        }catch(Exception e){
            System.err.println("[Erro] saveEstadoJogoFicheiro " + e);
        }
    }

    public void loadEstadoJogoFicheiro(String nomeFich){
        File f = new File(nomeFich);
        if(!f.exists()){
            System.out.println("Não existe jogo gravado");
        }
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
            originator = (MaqEstadosOriginator) ois.readObject();
            careTaker = (CareTaker) ois.readObject();
            f.delete();
        }catch(FileNotFoundException e){
            System.err.println("[Erro] loadEstadoJogoFicheiro " + e);
        }catch (Exception e){
            System.err.println("[Erro] loadEstadoJogoFicheiro " + e);
        }
    }







}
