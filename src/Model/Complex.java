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
     **********************OPERATIONS*************************
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

    //Module
    public double module(){
        return Math.sqrt( (this.p_reel * this.p_reel) + (this.p_img * this.p_img));
    }

    //puissance
    public Complex puissance(Complex c, double puissance){
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

}
