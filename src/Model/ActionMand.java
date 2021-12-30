package Model;

import java.util.concurrent.RecursiveAction;

public class ActionMand extends RecursiveAction {

    int from, to;
    double[][] data;
    double pas;
    double x;
    double y;
    Fonction f;
    int iter;
    int min;

    public ActionMand(int f, int t, double[][] d, double pas, double x, double y, Fonction fo, int i, int min){
        from = f;
        to = t;
        data = d;
        this.pas = pas;
        this.x = x;
        this.y = y;
        this.f = fo;
        iter = i;
        this.min = min;
    }

    @Override
    protected void compute() {
        if (to - from <= min) {
            computeDirectly();
            return;
        }
        int middle = (from + to) / 2;
        invokeAll(new ActionMand(from, middle, data, pas, x, y,f,iter,min),
                new ActionMand(middle, to, data, pas, x, y,f,iter,min));
    }

    private void computeDirectly(){
        int[] inds = calculeInd();
        int vi = inds[0];
        int vj = inds[1];
        int compt = 0;
        while (compt < to) {
            for (int j = vj; j < data[0].length; j++) {
                Complex c = new Complex.Builder((x + pas*j), y + pas*vi).build();
                data[vi][j] = divergenceIndex(c);
                compt++;
            }
            for (int i = vi+1; i < data.length; i++) {
                for (int j= 0; j < data[0].length ;j++) {
                    Complex c = new Complex.Builder(x + pas*j, y + pas*i).build();
                    data[i][j] = divergenceIndex(c);
                    compt++;
                }
            }
        }
    }

    /**
     * Calcul l'indice de divergence
     * @param z0 : complexe initial
     * @return l'indice de divergence en fonction du complexe initial
     */
    public int divergenceIndex (Complex z0){
        int ite = 0;
        Complex zn = z0;
        Fonction fonction = new Fonction.BuilderFonction(z0).coef(f.getCoeff()).build();
        //System.out.println(z0);
        while (ite < this.iter && zn.module() <=2){
            zn = fonction.apply(zn);
            ite++;
        }
        return ite;
    }

    /**
     * Calcule les premiers indices de depart
     * @return un tableau avec le premier i et j correspondant a from
     */
    public int[] calculeInd(){
        int i = 0;
        int j = 0;
        int compt = 0;
        while (compt < from) {
            for (int a = 0; a < data.length; a++) {
                i = a;
                for (int b = 0; b < data[0].length; b++) {
                    j = b;
                    compt++;
                }
            }
        }
        return new int[]{i,j};
    }
}
