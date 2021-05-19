package pt.isec.a2019112924.tp.jogo.logica.memento;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Memento {
    private byte[] snapshot = null;

    public Memento(Object obj) throws IOException {
        ByteArrayOutputStream baos;
        ObjectOutputStream oos = null;
        try {
            //array de bytes em memoria
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            snapshot = baos.toByteArray();
        }finally {
            if(oos!=null){
                oos.close();
            }
        }
    }

    public Object getSnapshot() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = null;
        if (snapshot == null)
            return null;
        try {
            ois = new ObjectInputStream(new ByteArrayInputStream(snapshot));
            return ois.readObject();
        } finally {
            if(ois!=null){
                ois.close();
            }
        }
    }
}
