package Model;
import Model.Complex;

public class Function {

    private Complex z;
    private Complex c;

    public Function(Complex c){
        this.c = c;
    }

    public Complex img(Complex z){
        return z.add(c);
    }
}
