package pt.isec.a2019112924.tp.jogo.iu.gui.recursos;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class ImageLoader {
    static Map<String, Image> imgCache = new HashMap<>();

    public static Image getImage(String name) {
        Image img = imgCache.get(name);
        if (img != null)
            return img;
        try {
            img = new Image(Recursos.getResourceFileAsStream("imagens/"+name));
            imgCache.put(name,img);
            return img;
        } catch (Exception e) {
        }
        return null;
    }
}
