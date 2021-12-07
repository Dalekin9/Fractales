package Model;

import java.util.LinkedList;
import java.util.function.Function;

public class BuilderFractal {

    //required
    protected double[][] rect;
    protected double pas;
    protected Fonction function;
    //optionnel
    protected int iter;
    protected int color;
    protected String fic;
    protected int type;

    public BuilderFractal(double[] r, double p){
        this.pas = p;
        this.rect = new double[][]{{r[0], r[1]}, {r[2], r[3]}};
        this.iter = 100;
        this.color = 0;
        this.fic = "Fractale";
        this.type = 2;
    }

    public BuilderFractal fonction(Fonction f){
        this.function = f;
        return this;
    }

    public BuilderFractal fichier(String f){
        this.fic = f;
        return this;
    }

    public BuilderFractal coloration(int t){
        this.color = t;
        return this;
    }

    public BuilderFractal iter(int i){
        this.iter = i;
        return this;
    }

    public Fractal build(){
        if (this.type == 1) {
            return new Julia(this);
        } else if (this.type == 2){
            return new Mandelbrot(this);
        } else {
            return null;
        }
    }

}
