package pt.isec.a2019112924.tp.jogo.logica;

import pt.isec.a2019112924.tp.jogo.logica.dados.IMiniJogo;
import pt.isec.a2019112924.tp.jogo.logica.dados.Jogador;
import pt.isec.a2019112924.tp.jogo.logica.dados.JogadorHumano;
import pt.isec.a2019112924.tp.jogo.logica.memento.CareTaker;
import pt.isec.a2019112924.tp.jogo.logica.memento.MaqEstadosOriginator;
import pt.isec.a2019112924.tp.jogo.utils.Situacao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Gestor {
    private MaqEstadosOriginator originator;
    private CareTaker careTaker;

    public Gestor(){
        originator = new MaqEstadosOriginator();
        careTaker = new CareTaker(originator);
    }

    public boolean adicionaJogador(String nome){
        for(int i = 0; i < originator.getJogadores().size(); i++) {
            if (originator.getJogadores().get(i).getNome().equals(nome)){
                return false;
            }
        }
        originator.adicionaJogador(nome);
        return true;
    }

    //Maquina Estados
    public void iniciaJogo(){ originator.iniciaJogo();
        careTaker.gravaJogo();}

    public void jogaPeca(int coluna){
        careTaker.gravaMemento();
        originator.jogaPeca(coluna);
        careTaker.gravaJogo();
    }

    public void jogaPeca() {
        careTaker.gravaMemento();
        originator.jogaPeca();
        careTaker.gravaJogo();
    }

    public void jogaPecaEspecial(int coluna){
        careTaker.gravaMemento();
        originator.jogaPecaEspecial(coluna);
        careTaker.gravaJogo();
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
        careTaker.limpaHistoricos();
        originator.getLog().clear();
        originator.novaTentativa();
    }

    public Situacao getSituacaoAtual(){ return originator.getSituacaoAtual(); }

    public char[][] getTabuleiro(){ return originator.getTabuleiro();}

    public List<Jogador> getJogadores(){ return originator.getJogadores();}

    public Jogador getJogadorAtual(){ return originator.getJogadorAtual();}

    public boolean getColunaCompleta(){return originator.getColunaCompleta();}

    public IMiniJogo getMiniJogo(){ return originator.getMiniJogo(); }

    // CareTaker
    public boolean voltarAtras(int nrBacks) {
        Jogador jog1, jog2, jogAtual;
        List<String> logCopia = new ArrayList<>(originator.getLog());

        if(originator.getJogadorAtual() instanceof JogadorHumano) {
            if (nrBacks > ((JogadorHumano) originator.getJogadorAtual()).getNrCreditos()) {
                return false;
            }
        }
        jogAtual = originator.getJogadorAtual();
        jog1 = originator.getJogadores().get(0);
        jog2 = originator.getJogadores().get(1);

        if(!careTaker.undo(nrBacks)){
            return false;
        }

        originator.getJogadores().clear();
        originator.getJogadores().add(jog1);
        originator.getJogadores().add(jog2);
        jogAtual.setNrJogadas(0);
        ((JogadorHumano)jogAtual).setNrCreditos(((JogadorHumano)jogAtual).getNrCreditos() - nrBacks);
        originator.setLog(logCopia);
        originator.addLog(jogAtual.getNome() + ": Voltou para tras " + nrBacks + " vezes");
        careTaker.gravaJogo();
        return true;
    }

    public boolean loadReplayJogo(String nomeFich) {
        /*if(!existeFicheiro(nomeFich)){
            System.out.println("entrei neste if");
            return false;
        } */
        careTaker.loadReplay(nomeFich);
        return true;
    }

    public boolean saveReplayJogo(String nomeFich) {
        verificaReplays();
        if(existeFicheiro(nomeFich)){
            return false;
        }
        careTaker.saveReplay(nomeFich);
        return true;
    }

    public  void avancaReplay(){ careTaker.replayJogo();}

    public String[] reuneFicheiros(){
        File f = new File("./replays");
        return f.list();
    }

    public boolean existeFicheiro(String nomeFich){
        String[] ficheiros;
        ficheiros = reuneFicheiros();
        for(int i = 0; i < ficheiros.length; i++){
            if(ficheiros[i].equals(nomeFich + ".bin")){
                return true;
            }
        }
        return false;
    }

    private void verificaReplays(){
        File dir = new File("./replays");
        if(!dir.exists()){
            dir.mkdir();
        }
        if(dir.listFiles().length == 5){
            long dataMaisAntiga = dir.listFiles()[0].lastModified();
            int indiceFich = 0;
            for (int i = 0; i < dir.listFiles().length; i++) {
                if (dir.listFiles()[i].lastModified() < dataMaisAntiga) {
                    dataMaisAntiga = dir.listFiles()[i].lastModified();
                    indiceFich = i;
                }
            }
            dir.listFiles()[indiceFich].delete();
        }
    }

    public int getStackJogoSize(){ return careTaker.getStackJogo().size();}

    public boolean saveEstadoJogoFicheiro(String nomeFich){
        try{
            File f = new File(nomeFich);
            f.createNewFile();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
            oos.writeObject(originator);
            oos.writeObject(careTaker);
            oos.close();
            return true;
        }catch(Exception e){
            System.err.println("[Erro] saveEstadoJogoFicheiro " + e);
        }
        return false;
    }

    public boolean loadEstadoJogoFicheiro(String nomeFich){
        File f = new File(nomeFich);
        if(!f.exists()){
            return false;
        }
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
            originator = (MaqEstadosOriginator) ois.readObject();
            careTaker = (CareTaker) ois.readObject();
            f.delete();
            return true;
        }catch(FileNotFoundException e){
            System.err.println("[Erro] loadEstadoJogoFicheiro " + e);
        }catch (Exception e){
            System.err.println("[Erro] loadEstadoJogoFicheiro " + e);
        }
        return false;
    }

    // Log
    public List<String> getLog(){ return originator.getLog();}

    public void clearLog(){ originator.clearLog();}
}
