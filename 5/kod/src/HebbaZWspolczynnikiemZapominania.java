
import java.util.Random;



public class HebbaZWspolczynnikiemZapominania {


    double[] wagi = new double[5];
//bo bias

    void losujWagi() {
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            wagi[i] = random.nextDouble() * 1000;

        }
    }


    void check(double[] tablica, int error, double MSE) {

        double suma = 0;
        for (int j = 0; j < 4; j++) {
            suma += tablica[j] * wagi[j];
        }
        suma += wagi[4];
        if (f(suma) != f(tablica[4])) {
            error++;
            MSE += Math.pow((f(suma) - tablica[4]), 2);
        }
    }

    double f(double x) {

        if (x > 0)
            return 1;
        else
            return 0;

    }

    public void uczHebba(double[] tablica) {
        double suma = 0;
        for (int i = 0; i < 4; i++) {
            suma = suma + wagi[i] * tablica[i];

        }
        //bias
        suma += wagi[4] * 1;
        double pom = f(suma);

        for (int i = 0; i < 4; i++) {
            wagi[i] = (1 - 0.01) * wagi[i] + 0.001 * tablica[i] * pom;
        }
        //bias
        wagi[4] = (1 - 0.01) * wagi[4] + 0.001 * pom;

    }
}


