package Model;

import java.util.LinkedList;
import java.util.function.Function;

public class Fonction implements Function<Complex,Complex>  {


    private final LinkedList<double[]> coeff;
    private final Complex c;

    public static class BuilderFonction {

        private LinkedList<double[]> coeff = new LinkedList<>(); // 5x² = {5,2}
        private final Complex c;

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

    public LinkedList<double[]> getCoeff() {
        return new LinkedList<>(coeff);
    }

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder("Fonction : ");
        for (double[] doubles : coeff) {
            if (doubles[0] != 1) {
                text.append(doubles[0]);
            }
            text.append("x");
            if (doubles[1] != 1) {
                text.append((int) doubles[1]);
            }
            text.append(" + ");
        }
        text.append("c, ou c = ").append(c.toString());
        return String.valueOf(text);
    }


    /*
     * ***************************************************** *
     *                     Fonction
     * ***************************************************** *
     */

    @Override
    public Complex apply(Complex complex) {
        Complex res = this.c;
        for (double[] doubles : coeff) {
            Complex a = complex.puissance(complex, doubles[1]);
            Complex b = new Complex.Builder(doubles[0], 0).build();
            Complex c = a.mul(b);
            res = res.add(c);
        }
        return res;
    }
}
