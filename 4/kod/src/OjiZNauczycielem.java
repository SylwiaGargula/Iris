import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Sylwia on 2016-10-23.
 */
public class OjiZNauczycielem {


    double[] wagi = new double[4];
    int poprawny = 0;

    void losujWagi() {
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            wagi[i] = random.nextDouble();
        }
    }

    void wyznaczWagi() {
        for (int i = 0; i < 9; i++) {
            wagi[i] = 0.1 + i * 0.1;
        }
    }

    public void startlearnig() throws FileNotFoundException {
        PrintWriter zapis1 = new PrintWriter("OjiZNauczycielem.txt");

        losujWagi();
        double[] wartosciNowe = new double[5];
        String[] wartosci;
        File file = new File("iris.txt");
        Scanner in = null;


        for (int j = 0; j < 100; j++) {

            long czas = System.currentTimeMillis();
            in = new Scanner(file);
            do {


                wartosci = in.nextLine().split(",");


                for (int i = 0; i < 4; i++) {

                    if (wartosci[i].equals("Iris-setosa"))
                        wartosciNowe[i] = 1.0;
                    else if (wartosci[i].equals("Iris-versicolor"))
                        wartosciNowe[i] = 0.0;
                    else
                        wartosciNowe[i] = Double.parseDouble(wartosci[i]);

                    //   System.out.println(wagi[i]);
                }


                uczPercepton(wartosciNowe);


            } while (in.hasNextLine());
            long czas2 = System.currentTimeMillis();
            zapis1.println((czas2 - czas));

            check();

        }

        zapis1.close();
    }


    private void check() {
        //zbior maly
        //   System.out.println("zbior maly");
        File file = new File("iris2.txt");
        Scanner in = null;

        poprawny = 0;
        double[] wartosciNowe = new double[5];
        String[] wartosci;

        for (int j = 0; j < 100; j++) {


            try {
                in = new Scanner(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            do {


                wartosci = in.nextLine().split(",");


                for (int i = 0; i < 4; i++) {

                    if (wartosci[i].equals("Iris-setosa"))
                        wartosciNowe[i] = 1;
                    else if (wartosci[i].equals("Iris-versicolor"))
                        wartosciNowe[i] = 0;
                    else
                        wartosciNowe[i] = Double.parseDouble(wartosci[i]);

                }

                double sumuj = 0;
                for (int i = 0; i < 4; i++) {
                    sumuj += wartosciNowe[i] * wagi[i];
                }
                double wynikf = f(sumuj); //sprawdzanie z wynikiem w pliku
                if (wynikf == f(wartosciNowe[4]))
                    poprawny++;

            } while (in.hasNextLine());

            System.out.println("niepoprawnych " + poprawny);
            poprawny = 0;
        }


        //zbior duzy
        //  System.out.println("zbior duzy");
        File file2 = new File("iris.txt");
        Scanner in2 = null;

        int poprawny2 = 0;
        double[] wartosciNowe2 = new double[5];
        String[] wartosci2;

        for (int j = 0; j < 100; j++) {


            try {
                in2 = new Scanner(file2);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            do {


                wartosci2 = in2.nextLine().split(",");


                for (int i = 0; i < 4; i++) {

                    if (wartosci2[i].equals("Iris-setosa"))
                        wartosciNowe2[i] = 1;
                    else if (wartosci2[i].equals("Iris-versicolor"))
                        wartosciNowe2[i] = 0;
                    else
                        wartosciNowe2[i] = Double.parseDouble(wartosci2[i]);

                }

                double sumuj2 = 0;
                for (int i = 0; i < 4; i++) {
                    sumuj2 += wartosciNowe2[i] * wagi[i];
                }
                double wynikf2 = f(sumuj2); //sprawdzanie z wynikiem w pliku
                if (wynikf2 == f(wartosciNowe2[4]))
                    poprawny2++;

            } while (in2.hasNextLine());

            System.out.println("niepoprawnych " + poprawny2);
            poprawny2 = 0;
        }


    }

    private double f(double sumuj) {
        if (sumuj > 0) {
            return 1.0;
        } else {
            return 0.0;
        }
    }


    private void uczPercepton(double[] wartosciNowe) {


        double sumuj = 0;
        for (int i = 0; i < 4; i++) {
            sumuj += wartosciNowe[i] * wagi[i];
        }
        double wynikf = f(sumuj); //sprawdzanie z wynikiem w pliku

        if (wynikf != wartosciNowe[4]) {
            for (int i = 0; i < 4; i++) {
                wagi[i] += 0.0001 * (wartosciNowe[i] - wartosciNowe[4] * wagi[i]) * wynikf;
            }
        }


    }
}
