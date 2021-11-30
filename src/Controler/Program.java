package Controler;

import Model.Complex;
import Model.Function;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.module.ModuleDescriptor;

public class Program {

    static int MAX_ITER = 1000;
    static double RADIUS = 2.;
    static Function f = new Function(new Complex.Builder(-0.7269,0.1889).build());

    public static int divergenceIndex (Complex z0){
        int ite = 0;
        Complex zn = z0;
        // sortie de boucle si divergence
        while (ite < MAX_ITER - 1 && zn.module() <=RADIUS){
            zn = f.img(zn.mul(zn));
            ite++;
        }
        return ite;

    }


}
