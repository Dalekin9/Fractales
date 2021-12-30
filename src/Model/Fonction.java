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
            coeff.add(new double[]{1, 2,0});
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
        int compt = 0;
        for (double[] doubles : coeff) {
            //cas pour la constante
            if (doubles[0] == 1 && doubles[1] == 0 ) {
                if(doubles[2] == 1){ //cos
                    text.append(("cos("));
                    if (doubles[3] == 0)  {
                        if (doubles[4] == 0) {
                            text.append("c");
                        } else if (doubles[4] == 1){
                            text.append(("x/c"));
                        } else {
                            text.append("x/c").append(doubles[4]);
                        }
                    } else {
                        if (doubles[4] == 1){
                            text.append(doubles[3]).append("/c");
                        } else {
                            text.append(doubles[3]).append("/c").append(doubles[4]);
                        }
                    }
                    text.append((")"));
                }else if(doubles[2] == 2) {
                    text.append(("sin("));
                    if (doubles[3] == 0)  {
                        if (doubles[4] == 0) {
                            text.append("c");
                        } else if (doubles[4] == 1){
                            text.append(("x/c"));
                        } else {
                            text.append("x/c").append(doubles[4]);
                        }
                    } else {
                        if (doubles[4] == 1){
                            text.append(doubles[3]).append("/c");
                        } else {
                            text.append(doubles[3]).append("/c").append(doubles[4]);
                        }
                    }
                    text.append(")");
                } else if (doubles[2] == 0) {
                    text.append("c");
                } else {
                    if (doubles[4] == 1){
                        text.append(doubles[3]).append("/c");
                    } else {
                        text.append(doubles[3]).append("/c").append(doubles[4]);
                    }
                }
            }
            //cas general
            else {
                if(doubles[2] == 2){
                    text.append("sin("); // ajout du sin(res de la mult)
                    if (doubles[0] != 1) {
                        text.append(doubles[0]);
                    }
                    text.append("x");
                    if (doubles[1] != 1) {
                        text.append((int) doubles[1]);
                    }
                    text.append(")");
                }else if(doubles[2] == 1) {
                    text.append("cos("); // ajout du sin(res de la mult)
                    if (doubles[0] != 1) {
                        text.append(doubles[0]);
                    }
                    text.append("x");
                    if (doubles[1] != 1) {
                        text.append((int) doubles[1]);
                    }
                    text.append(")"); // 0 -> ajout simple du res de la multiplication
                } else {
                    if (doubles[0] != 1) {
                        text.append(doubles[0]);
                    }
                    text.append("x");
                    if (doubles[1] != 1) {
                        text.append((int) doubles[1]);
                    }
                }
            }
            if (compt != coeff.size() - 1) {
                text.append(" + ");
            }
            compt++;
        }
        text.append(", ou c = ").append(c.toString());
        return String.valueOf(text);
    }


    /*
     * ***************************************************** *
     *                     Fonction
     * ***************************************************** *
     */

    @Override
    public Complex apply(Complex complex) {
        Complex res = new Complex.Builder(0,0).build();
        for (double[] doubles : coeff) {
            //cas pour la constante
            if (doubles[0] == 1 && doubles[1] == 0 ) {
                if(doubles[2] == 1){ //cos
                    if (doubles[3] == 0)  {
                        if (doubles[4] == 0) { //+ cos(c)
                            res = res.add(this.c.cos());
                        } else if (doubles[4] == 1){ //+ cos(x/c)
                            res = res.add((complex.div(c)).cos());
                        } else { //+ cos(x/c2)
                            res = res.add((complex.div(Complex.puissance(c,doubles[4]))).cos());
                        }
                    } else {
                        Complex co = new Complex.Builder(doubles[3],0 ).build();
                        if (doubles[4] == 1){ //+ cos(../c)
                            res = res.add((co.div(c)).cos());
                        } else { //+ cos(../c..)
                            res = res.add(co.div(Complex.puissance(c,doubles[4])).cos());
                        }
                    }
                }
                else if(doubles[2] == 2) { //sin
                    if (doubles[3] == 0) {
                        if (doubles[4] == 0) { //+ sin(c)
                            res = res.add(this.c.sin());
                        } else if (doubles[4] == 1) { //+ sin(x/c)
                            res = res.add((complex.div(c)).sin());
                        } else { //+ sin(x/c2)
                            res = res.add((complex.div(Complex.puissance(c,doubles[4]))).sin());
                        }
                    }  else {
                        Complex co = new Complex.Builder(doubles[3],0 ).build();
                        if (doubles[4] == 1){ //+ sin(../c)
                            res = res.add((co.div(c)).sin());
                        } else { //+ sin(../c..)
                            res = res.add(co.div(Complex.puissance(c,doubles[4])).sin());
                        }
                    }
                } else if (doubles[2] == 0) {
                    res = res.add(this.c); //+ c
                } else {
                    Complex co = new Complex.Builder(doubles[3],0 ).build();
                    if (doubles[4] == 1){ // ../c
                        res = res.add(co.div(c));
                    } else { // ../c..
                        res = res.add(co.div(Complex.puissance(c,doubles[4])));
                    }
                }
            }else {
                // cas general
                Complex a = Complex.puissance(complex, doubles[1]); //met x a la puissance
                Complex b = new Complex.Builder(doubles[0], 0).build(); // coefficient multiplicateur
                Complex c = a.mul(b); //mul du coeff et de x a la puissance
                if(doubles[2] == 1){
                    res = res.add(c.cos()); // ajout du cos(res de la mult)
                }else if(doubles[2] == 2){
                    res = res.add(c.sin()); // ajout du sin(res de la mult)
                }else{
                    res = res.add(c); // 0 -> ajout simple du res de la multiplication
                }
            }
        }
        return res;
    }
}
