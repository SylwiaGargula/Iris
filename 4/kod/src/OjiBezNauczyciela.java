import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Sylwia on 2016-10-23.
 */
public class OjiBezNauczyciela {



    static double [][]uczace=new double[70][5];
    static double [][]walidujace=new double[30][5];

    double []wagi=new double[4];
    int error=0;
    int error1=0;
    double MSE=0;
    double MSE1=0;

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
        PrintWriter zapis = new PrintWriter("OjiBezNauczyciela.txt");
        PrintWriter zapis1 = new PrintWriter("OjiBezNauczyciela_bledy.txt");
        PrintWriter zapis2 = new PrintWriter("OjiBezNauczyciela_MSE.txt");
        PrintWriter zapis12 = new PrintWriter("OjiBezNauczyciela_bledy_wal.txt");
        PrintWriter zapis22 = new PrintWriter("OjiBezNauczyciela_MSE_wal.txt");
        PrintWriter zapis3 = new PrintWriter("logi OjiBezNauczyciela .txt");
        ZbierzDane();
        losujWagi();

        for (int x = 0; x < 100; x++) {
            zapis3.println("Powtorzenie nr "+x);
            for (int j = 0; j < 100; j++) {

                long czas = System.currentTimeMillis();
                for(int i=0;i<70;i++)
                    uczPercepton(uczace[i]);



                long czas2 = System.currentTimeMillis();
                zapis.println((czas2 - czas));

                check();
                error = 0;
                error1 = 0;
                MSE = 0;
                MSE1 = 0;
                if (x == 99) {
                    zapis1.println(error);
                    zapis2.println(MSE * 1 / 30);
                    zapis12.println(error1);
                    zapis22.println(MSE1 * 1 / 70);
                }
                zapis3.println("Epoka nr " + j + "dlugosc uczenia epoki: " + (czas2 - czas) + " Ilosc błędów uczace: " + error + " MSE " + MSE + " Ilosc błędów walidujace: " + error + " MSE " + MSE);


            }

        }
        zapis.close();
        zapis1.close();
        zapis2.close();
        zapis12.close();
        zapis22.close();
        zapis3.close();

    }




    private void check() {
        //zbior maly
        //   System.out.println("zbior maly");
        for(int j=0;j<30;j++){
            double sumuj = 0;
            for (int i = 0; i < 4; i++) {
                sumuj += walidujace[j][i] * wagi[i];
            }
            double wynikf = f(sumuj); //sprawdzanie z wynikiem w pliku
            if (wynikf == f(walidujace[j][4]))
                error1++;
            MSE1+=Math.pow((wynikf-walidujace[j][4]),2);

        }




        //zbior duzy
        //  System.out.println("zbior duzy");

        for(int j=0;j<70;j++){
            double sumuj2 = 0;
            for (int i = 0; i < 4; i++) {
                sumuj2 += uczace[j][i] * wagi[i];
            }
            double wynikf2 = f(sumuj2); //sprawdzanie z wynikiem w pliku
            if (wynikf2 == f(uczace[j][4]))
                error++;
            MSE+=Math.pow((wynikf2-uczace[j][4]),2);

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


        double sumuj=0;
        for(int i=0; i<4;i++)
        {
            sumuj+=wartosciNowe[i]*wagi[i];
        }
    double wynikf=f(sumuj); //sprawdzanie z wynikiem w pliku

        for(int i=0;i<4;i++)
        {
            wagi[i]+=0.0001*(wartosciNowe[i]-wynikf*wagi[i])*wynikf;
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

        for (int j = 0; j <70; j++) {
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
    }


}
