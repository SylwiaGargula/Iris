import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Sylwia on 2016-10-23.
 */
public class Percepton {

    static double[][] uczace = new double[70][6];
    static double[][] walidujace = new double[30][6];
    static int liczbaneuronow = 3;
    private static int error = 0;
    private static int error1 = 0;
    private static double MSE = 0;
    private static double MSE1 = 0;

    double []wagi=new double[4];
    int poprawny=0;

    void losujWagi()
    {
        Random random=new Random();
        for(int i=0;i<4;i++)
        {
            wagi[i]=random.nextDouble();
        }
    }

    void wyznaczWagi()
    {
        for(int i=0;i<9;i++)
        {
            wagi[i]=0.1+i*0.1;
        }
    }
    public void startlearnig() throws FileNotFoundException {
        PrintWriter zapis = new PrintWriter("Percepton_czas.txt");
        PrintWriter zapis1 = new PrintWriter("Percepton_bledy_wal.txt");
        PrintWriter zapis2 = new PrintWriter("Percepton_MSE_wal.txt");
        PrintWriter zapis12 = new PrintWriter("Percepton_bledy.txt");
        PrintWriter zapis22 = new PrintWriter("Percepton_MSE.txt");
        PrintWriter zapis3 = new PrintWriter("Percepton logi.txt");
        ZbierzDane();


        for (int o = 0; o < 100; o++) {
            losujWagi();
            zapis3.println("powtorzenie nr " + o);

            for (int j = 0; j < 100; j++) {

                error = 0;
                error1 = 0;
                MSE = 0;
                MSE1 = 0;
                long czas = System.currentTimeMillis();
                for (int i = 0; i < 1; i++) {
                    uczPercepton(uczace[i]);
                }
                long czas2 = System.currentTimeMillis();


                check();
                if(j==99) {
                    zapis.println((czas2 - czas));
                    zapis1.println(error);
                    zapis2.println(MSE * 1 / 30);
                    zapis12.println(error1);
                    zapis22.println(MSE1 * 1 / 70);
                }
                zapis3.println("Epoka nr " + j + "dlugosc uczenia epoki: " + (czas2 - czas) + " Ilosc błędów walidujace: " + error + " MSE " + MSE * 1 / 300 + " Ilosc błędów uczace: " + error1 + " MSE " + MSE1 * 1 / 700);

            }
        }
        zapis.close();
        zapis1.close();
        zapis2.close();
        zapis12.close();
        zapis22.close();

    }

    private void check() {

        poprawny = 0;


        for (int j = 0; j < 30; j++) {


            double sumuj = 0;
            for (int i = 0; i < 4; i++) {
                sumuj += walidujace[j][i] * wagi[i];
            }
            double wynikf = f(sumuj); //sprawdzanie z wynikiem w pliku
            if(wynikf==f(walidujace[j][4]))
                poprawny++;
            else
            {
                error++;
                MSE += Math.pow((wynikf - walidujace[j][4]), 2);
            }



        }


        for (int j = 0; j < 70; j++) {


            double sumuj = 0;
            for (int i = 0; i < 4; i++) {
                sumuj += uczace[j][i] * wagi[i];
            }
            double wynikf = f(sumuj); //sprawdzanie z wynikiem w pliku
            if(wynikf==f(uczace[j][4]))
                poprawny++;
            else
            {
                error1++;
                MSE1 += Math.pow((wynikf - uczace[j][4]), 2);
            }

        }


    }
    private double f(double sumuj)
    {
        if(sumuj>0)
        {
            return 1.0;
        }
        else
        {
            return 0.0;
        }
    }



    private void uczPercepton(double[] wartosciNowe) {

      //  losujWagi();
      //  System.out.println("uczy");
        double sumuj=0;
        for(int i=0; i<4;i++)
        {
            sumuj+=wartosciNowe[i]*wagi[i];
        }
    double wynikf=f(sumuj); //sprawdzanie z wynikiem w pliku

        if((wynikf==0.0) && (f(wartosciNowe[4])==1.0))
        {
            for(int i=0; i<4; i++)
    wagi[i]=wagi[i]+wartosciNowe[i];

       //    System.out.println("wynik 1.0");
        }

        if((wynikf==1.0)  && (f(wartosciNowe[4])==0.0))
        {
            for(int i=0; i<4; i++)
                wagi[i]=wagi[i]-wartosciNowe[i];
        //    System.out.println("wynik 0.0");
        }

    }

    public static void ZbierzDane() {

        File file = new File("iris.txt");
        Scanner in = null;
        try {
            in = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String[] tablica1;

        for (int j = 0; j < 70; j++) {
            tablica1 = in.nextLine().split(",");

            for (int i = 0; i < 4; i++) {
                uczace[j][i] = Double.parseDouble(tablica1[i]);
            }
            if (tablica1[4] == "Iris-setosa") {
                uczace[j][4] = 0;
            } else {
                uczace[j][4] = 1;
            }
            double sumapoteg = 0;
            for (int i = 0; i < 5; i++) {
                sumapoteg += Math.sqrt(Math.pow(uczace[j][i], 2));
            }
            for (int i = 0; i < 5; i++) {
                uczace[j][i] /= sumapoteg;
            }

        }

        File file1 = new File("iris2.txt");
        Scanner in1 = null;
        try {
            in1 = new Scanner(file1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



        for (int j = 0; j < 30; j++) {
            tablica1 = in1.nextLine().split(",");

            for (int i = 0; i < 4; i++) {
                walidujace[j][i] = Double.parseDouble(tablica1[i]);
            }
            if (tablica1[4] == "Iris-setosa") {
                walidujace[j][4] = 0;
            } else {
                walidujace[j][4] = 1;
            }
            double sumapoteg = 0;
            for (int i = 0; i < 5; i++) {
                sumapoteg += Math.sqrt(Math.pow(walidujace[j][i], 2));
            }
            for (int i = 0; i < 5; i++) {
                walidujace[j][i] /= sumapoteg;
            }

        }
    }



}
