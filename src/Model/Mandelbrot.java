package Model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Mandelbrot extends Fractal{

    Mandelbrot(BuilderFractal builderFractal){
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
    public int divergenceIndex (Complex z0){
        int ite = 0;
        Complex zn = z0;
        // sortie de boucle si divergence
        while (ite < this.iter - 1 && zn.module() <=2){
            Fonction f = new Fonction.BuilderFonction(z0).coef(this.function.getCoeff()).build();
            zn = f.apply(zn);
            ite++;
        }
        return ite;
    }


    /*
    @Override
    public int coloration(int val) {
        int r = 0, g = 0 ,b = 0;
        if (val == this.iter){
            return Color.RED.getRGB();
        }
        switch (this.color) {
                case 0 -> {
                    r = (255*val) / this.iter;
                    g = (255*val) / this.iter;
                    b = (255 * val) / this.iter;
                }
                case 1 -> r = (255 * val) / this.iter;
                case 2 -> b = (255 * val) / this.iter;
                case 3 -> g = (255 * val) / this.iter;
            }
            Color color = new Color(r, g, b);
            return Math.abs(color.getRGB() - 255);
    }

     */
}
