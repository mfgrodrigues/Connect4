package pt.isec.a2019112924.tp.jogo.logica.memento;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Memento implements Serializable {
    private static final long serialVersionUID = 1L;

    private final byte[] snapshot;

    public Memento(Object obj) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)){
             oos.writeObject(obj);
             snapshot = baos.toByteArray();
        }
    }

    public Object getSnapshot() throws IOException, ClassNotFoundException {
        if (snapshot == null)
            return null;
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(snapshot))){
            return ois.readObject();
        }
    }
}
