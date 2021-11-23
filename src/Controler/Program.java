package Controler;

import Model.Complex;
import Model.Function;

public class Program {

    int MAX_ITER = 1000;
    double RADIUS = 2.;
    Function f;

    int divergenceIndex (Complex z0){
        int ite = 0;
        Complex zn = z0;
        // sortie de boucle si divergence
        while (ite < MAX_ITER - 1 && zn.module() <=RADIUS){
            zn = f.img(zn);
            ite++;
        }
        return ite;

    }

    public static void main(String[] args) {

        /*
        prendre un rectangle du plan complexe (ex [−1, 1]×[−1, 1])
        prendre des points du rectangles avec un pas h (ex h= 0.01)
        obtention d'une grille de points (ex 201*201 points)
        pour chaque points z :
            calcule de ind=divergenceIndex(z)
            colorie le pixel de z selon la valeur de int (definir code couleur)
        obtention de l'image de julia
         */


    }

}
