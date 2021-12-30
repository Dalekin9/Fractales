package Model;

public class Complex {

    private final double p_reel;
    private final double p_img;

    public static class Builder {

        private final double p_reel;
        private final double p_img;

        public Builder (double r, double i){
            p_img = i;
            p_reel = r;
        }

        public Complex build(){
            return new Complex(this);
        }
    }

    private Complex( Builder builder){
        p_img = builder.p_img;
        p_reel = builder.p_reel;
    }

    @Override
    public String toString() {
        return p_reel + " + i"+ p_img;
    }


    /*
     * ***************************************************** *
     *                     Operations
     * ***************************************************** *
     */

    /**
     * Additionne deux complexes
     * @param b : le complexe qu'on souhaite additionner
     * @return l'addition de notre complexe et b
     */
    public Complex add(Complex b){
        return new Complex( new Builder( (this.p_reel + b.p_reel), (this.p_img + b.p_img) ));
    }

    /**
     * Multiplie deux complexes
     * @param b : le complexe qu'on souhaite multiplier
     * @return la multiplication entre notre complexe et b
     */
    public Complex mul(Complex b){
        double pr = (this.p_reel * b.p_reel) - (this.p_img * b.p_img);
        double pi = ((this.p_reel + this.p_img)*(b.p_reel + b.p_img)) -
                (this.p_reel*b.p_reel) - (this.p_img * b.p_img);
        return new Complex( new Builder( pr, pi ));
    }

    /**
     * Calcule le module du complexe
     * @return le module du complexe
     */
    public double module(){
        return Math.sqrt( (this.p_reel * this.p_reel) + (this.p_img * this.p_img));
    }

    /**
     * Met un complexe sous une puissance
     * @param c : complexe initial
     * @param puissance : puissance voulue
     * @return le complexe c passé a la puissance voulue
     */
    public static Complex puissance(Complex c, double puissance){
        if (puissance == 1 ){
            return c;
        } else {
            Complex res = c;
            for (double i = 1; i < puissance; i += 1) {
                res=res.mul(c);
            }
            return res;
        }
    }

    public Complex cos(){
        return new Complex.Builder(Math.cos(p_reel) * Math.cosh(p_img), - (Math.sin(p_reel) * Math.sinh(p_img))).build();
    }

    public Complex sin(){
        return new Complex.Builder(Math.sin(p_reel) * Math.cosh(p_img), - (Math.cos(p_reel) * Math.sinh(p_img))).build();
    }

}
