package pt.isec.a2019112924.tp.jogo.logica.memento;

import pt.isec.a2019112924.tp.jogo.logica.dados.Jogo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;

public class CareTaker implements Serializable {

    private static final long serialVersionUID = 1L;

    private final IMementoOriginator originator;
    private Deque<Memento> stackHist = new ArrayDeque<>();
    private Deque<Memento> stackJogo = new ArrayDeque<>();

    public CareTaker(IMementoOriginator org) {originator = org;}

    public Deque<Memento> getStackJogo(){return stackJogo;}

    public void setStackJogo(Deque<Memento> stackJogo){ this.stackJogo = stackJogo;}

    public void gravaMemento() {
        try{
            stackHist.push(originator.saveMemento());
        } catch(IOException e) {
            System.err.println("[Erro] gravaMemento " + e);
            stackHist.clear();
        }
    }

    public void gravaJogo() {
        try{
            stackJogo.push(originator.saveMementoJogo());
        } catch(IOException e) {
            System.err.println("[Erro] gravaJogo " + e);
            stackJogo.clear();
        }
    }


    public void undo() {
        if (stackHist.isEmpty()) {
            return;
        }
        try {
            Memento anterior = stackHist.pop();
            originator.restoreMemento(anterior);
        } catch(IOException | ClassNotFoundException e) {
            System.err.println("[Erro] undo " + e);
            stackHist.clear();
        }
    }

    public Jogo replayJogo(){
        if(stackJogo.isEmpty()){
            return null;
        }
        try{
            return (Jogo)stackJogo.pollLast().getSnapshot();
        } catch(IOException | ClassNotFoundException e) {
            System.err.println("[Erro] replayJogo " + e);
            stackHist.clear();
        }
        return null;
    }

    public void saveReplay(String nomeFich){
        gravaJogo();
        String ficheiro = "./replays/" + nomeFich + ".bin";
        File f = new File(ficheiro);
        try{
            f.createNewFile();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
            oos.writeObject(stackJogo);
            oos.close();
        }catch (Exception e){
            System.err.println("[Erro] saveReplay " + e);
        }
    }

    public void loadReplay(String nomeFich){
        String ficheiro = "./replays/" + nomeFich + ".bin";
        try{
            File f = new File(ficheiro);
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
            stackJogo = (Deque<Memento>)ois.readObject();
            ois.close();
        }catch(FileNotFoundException e){
            System.err.println("[Erro] loadReplay " + e);
        }catch (Exception e){
            System.err.println("[Erro] loadReplay " + e);
        }
    }

}
