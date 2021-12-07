package Model;

import java.awt.*;

public class Julia extends Fractal{

    Julia(BuilderFractal builderFractal){
        super();
        this.rect = builderFractal.rect;
        this.pas = builderFractal.pas;
        this.iter = builderFractal.iter; //nb d'iteration max de la fonction
        this.color = builderFractal.color; // 0 (noir/blanc) 1 (rouge) 2 (bleu) 3 (vert) 4 (multicolor)
        this.fic = builderFractal.fic; //nom pour le fichier
        this.function = builderFractal.function;
        this.type = builderFractal.type;
    }

    @Override
    public int coloration(int val) {
        int r = 0, g = 0 ,b = 0;
        if (val == this.iter) {
            return Color.BLACK.getRGB();
        } else {
            // 0 (noir/blanc) 1 (rouge) 2 (bleu) 3 (vert) 4 (multicolor)
            switch (this.color) {
                case 0 -> {
                    r = (255 * val) / this.iter;
                    g = (255 * val) / this.iter;
                    b = 255;
                }
                case 1 -> r = (255 * val) / this.iter;
                case 2 -> b = (255 * val) / this.iter;
                case 3 -> g = (255 * val) / this.iter;
            }
            Color color = new Color(r, g, b);
            return color.getRGB();
        }
    }

}
