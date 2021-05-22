package pt.isec.a2019112924.tp.jogo.logica.memento;

import java.io.IOException;

public interface IMementoOriginator {
    Memento saveMemento() throws IOException; //saveState
    void restoreMemento(Memento m) throws IOException, ClassNotFoundException; //restoreState
    Memento saveMementoJogo() throws IOException;
}
