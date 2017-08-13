/**
 * Created by Sylwia on 2016-11-20.
 */
public class Warstwa {

    public Percepton [] Perceptony;
    int ilosc_perceptonow;
    double[] wynikWarstwy;

    public Warstwa(int ilosc_perceptonow) {

        this.ilosc_perceptonow = ilosc_perceptonow;

        Perceptony=new Percepton[ilosc_perceptonow];
        wynikWarstwy=new double[ilosc_perceptonow];
        for(int i=0;i<ilosc_perceptonow;i++) {
            Perceptony[i]=new Percepton();
            Perceptony[i].losujWagi();
        }
    }
}


