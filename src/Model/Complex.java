package Model;

public class Complex {

    private double p_reel;
    private double p_img;

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

    /*********************************************************
     *********************************************************
     *****************GETTERS AND SETTERS*********************
     *********************************************************
     ********************************************************/

    public double getP_img() {
        return p_img;
    }

    public double getP_reel() {
        return p_reel;
    }

    public void setP_img(double p_img) {
        this.p_img = p_img;
    }

    public void setP_reel(double p_reel) {
        this.p_reel = p_reel;
    }

    /*********************************************************
     *********************************************************
     **********************OPERATIONS*************************
     *********************************************************
     ********************************************************/

    //Addition
    public Complex add(Complex b){
        return new Complex( new Builder( (this.p_reel + b.p_reel), (this.p_img + b.p_img) ));
    }

    //Multiplication
    public Complex mul(Complex b){
        double pr = (this.p_reel * b.p_reel) - (this.p_img * b.p_img);
        double pi = ((this.p_reel + this.p_img)*(b.p_reel + b.p_img)) -
                (this.p_reel*b.p_reel) - (this.p_img * b.p_img);
        return new Complex( new Builder( pr, pi ));
    }

    //Substraction
    public Complex sub(Complex b){
        return new Complex( new Builder ( (this.p_reel - b.p_reel), (this.p_img - b.p_img) ));
    }

    public double module(){
        return Math.sqrt( (this.p_reel * this.p_reel) + (this.p_img * this.p_img));
    }

    public Complex puissance(Complex a, double puissance){
        if (puissance == 1 ){
            return a;
        } else {
            Complex res = a; //x * x
            for (double i = 1; i < puissance; i += 1) {
                res=res.mul(a);
                //System.out.println(res);
            }
            return res;
        }
    }

}
