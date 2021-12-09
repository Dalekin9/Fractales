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
        float s = (float) ( 30 + (val * 360)/this.iter ) /360;
        float bb = (float) ( 30 + (val * 360)/this.iter ) /360;
        if (val == this.iter) {
            return new Color(0,0,0).getRGB();
        }
        switch (this.color) {
            case 0 -> {
                int r = (255 * val) / this.iter;
                int g = (255 * val) / this.iter;
                int b = (255 * val) / this.iter;
                return new Color(r,g,b).getRGB();
            }
            case 1 -> {
                int res = -42 + (val * 84 / this.iter);
                if (res < 0) {
                    return Color.HSBtoRGB((float) (360-res)/360, s, bb);
                } else {
                    return Color.HSBtoRGB((float) (res)/360, s, bb);
                }
            }
            case 2 -> {
                int res = 216 + (val * 120 / this.iter);
                return Color.HSBtoRGB((float) (res)/360, s, bb);
            }
            case 3 -> {
                int res = 96 + (val * 120 / this.iter);
                return Color.HSBtoRGB((float) (res)/360, s, bb);
            }
            case 4 -> {
                int res = (val * 360 / this.iter);
                return Color.HSBtoRGB((float) (res)/360, 0.8F, 0.8F);
            }
            default -> {
                return Color.BLACK.getRGB();
            }
        }
    }

}
