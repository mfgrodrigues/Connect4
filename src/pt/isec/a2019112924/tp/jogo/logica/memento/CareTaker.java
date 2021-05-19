package pt.isec.a2019112924.tp.jogo.logica.memento;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

public class CareTaker {
    private final IMementoOriginator originator;
    //permitem funcionalidades de uma pilha
    private Deque<Memento> stackHist = new ArrayDeque<>();
    private Deque<Memento> stackRedo = new ArrayDeque<>();

    public CareTaker(IMementoOriginator org) {originator = org;}

    //grava todos os momentos num historico
    public void gravaMemento() {
        stackRedo.clear();
        try{
            stackHist.push(originator.saveMemento());
        } catch(IOException e) {
            System.err.println("gravaMemento: " + e);
            stackHist.clear();
            stackRedo.clear();
        }
    }

    //recebe ordem undo
    public void undo() {
        if (stackHist.isEmpty()) {
            return;
        }
        try {
            Memento atual = originator.saveMemento();
            stackRedo.push(atual);
            Memento anterior = stackHist.pop();
            originator.restoreMemento(anterior);
        } catch(IOException | ClassNotFoundException e) {
            System.err.println("undo: " + e);
            stackHist.clear();
            stackRedo.clear();
        }

    }
}
