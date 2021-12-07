package Model;

import java.util.LinkedList;

public class Fonction implements java.util.function.Function {

    // 5x² = {5,2}
    private LinkedList<double[]> coeff;
    //constante de la fonction
    private Complex c;


    public static class BuilderFonction {

        // 5x² = {5,2}
        private LinkedList<double[]> coeff = new LinkedList<>();
        //constante de la fonction
        private Complex c;

        public BuilderFonction (Complex c){
            this.c = c;
            coeff.add(new double[]{1, 2});

        }

        public BuilderFonction coef (LinkedList<double[]> c){
            this.coeff = c;
            return this;
        }

        public Fonction build(){
            return new Fonction(this);
        }
    }

    private Fonction( Fonction.BuilderFonction builder){
        coeff = builder.coeff;
        c = builder.c;
    }


    /*
    --------Fonctions---------
     */

    @Override
    public Complex apply(Object o) {
        /*
        Complex zn = (Complex) o;
        Complex res = this.c;
        for (int i = 0; i < coeff.size(); i++){
            Complex a = zn.puissance(zn,coeff.get(i)[1]);
            Complex b = new Complex.Builder(coeff.get(i)[0],0).build();
            Complex c = a.mul(b);
            res = res.add(c);
        }
        return res;
         */
        Complex zn = (Complex) o;
        Complex r = new Complex.Builder(zn.module(), 0).build();
        return (r.div(zn.div(this.c)));

    }

    @Override
    public String toString() {
        return "Fonction{" +
                "coeff=" + coeff +
                ", c=" + c +
                '}';
    }
}
