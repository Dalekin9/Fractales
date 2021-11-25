package Model;

public class Function {

    private double z;
    private Complex c;

    public Function(Complex c){
        this.c = c;
    }

    public Complex img(Complex z){
        return z.add(c);
    }
}
