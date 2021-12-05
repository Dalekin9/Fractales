package Model;

import java.util.LinkedList;

public class Function {

    // 5x² = {5,2}
    private LinkedList<double[]> coeff = new LinkedList<>();
    private Complex c;

    public Function(Complex c, LinkedList<double[]> co){
        this.c = c;
        System.out.println(this.c);
        if (co.isEmpty()){
            coeff.add(new double[]{1, 2});
        } else {
            this.coeff = co;
        }
    }

    public Complex img(Complex z){
        Complex res = this.c;
        for (int i = 0; i < coeff.size(); i++){
            //System.out.println(coeff.get(i)[0]+" , "+coeff.get(i)[1]);
            Complex a = z.puissance(z,coeff.get(i)[1]);
            Complex b = new Complex.Builder(coeff.get(i)[0],0).build();
            Complex c = a.mul(b);
            res = res.add(c);
            //System.out.println(res);
        }
        return res;
    }

    public Complex getC() {
        return c;
    }
}
