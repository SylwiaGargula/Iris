import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by Sylwia on 2016-11-20.
 */
public class Siec {
    Warstwa [] Warstwy=new Warstwa[2];
    double [][]uczace=new double[70][5];
    double [][]walidujace=new double[30][5];
    int error=0;
    int error2=0;
    double MSE=0;
    double MSE1=0;

    public Siec() {
        Warstwy[0]=new Warstwa(4);
        Warstwy[1]=new Warstwa(1);
        ZbierzDane();
        try {
            UczSiec();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void ZbierzDane() {
        //zbior maly
        //   System.out.println("zbior maly");
        File file = new File("iris2.txt");
        Scanner in = null;

        String[] wartosci;

            try {
                in = new Scanner(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
          for(int j=0;j<30;j++)
          {

                wartosci = in.nextLine().split(",");


                for (int i = 0; i < 4; i++) {

                    if (wartosci[i].equals("Iris-setosa"))
                        walidujace[j][4] = 1.0;
                    else if (wartosci[i].equals("Iris-versicolor"))
                        walidujace[j][4] = 0;
                    else
                        walidujace[j][i] = Double.parseDouble(wartosci[i]);

                }



            }
        File file1 = new File("iris.txt");
        Scanner in1 = null;



        try {
            in1 = new Scanner(file1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for(int j=0;j<70;j++)
        {

            wartosci = in1.nextLine().split(",");


            for (int i = 0; i < 4; i++) {

                if (wartosci[i].equals("Iris-setosa"))
                    uczace[j][4] = 1.0;
                else if (wartosci[i].equals("Iris-versicolor"))
                    uczace[j][4] = 0;
                else
                    uczace[j][i] = Double.parseDouble(wartosci[i]);

            }

        }

    }
    double f(double x)
    {
        return(1/(1+Math.pow(Math.E,(-0.5*x))));

    }
    private void UczSiec() throws FileNotFoundException {
        PrintWriter zapis = new PrintWriter("Siec-czas.txt");
        PrintWriter zapis1 = new PrintWriter("Walidujace_błedy.txt");
        PrintWriter zapis2=new PrintWriter("Walidujace MSE.txt");
        PrintWriter zapis12 = new PrintWriter("Uczace_błędy.txt");
        PrintWriter zapis22=new PrintWriter("Uczace_MSE.txt");
        PrintWriter zapis3 = new PrintWriter("Siec logi.txt");
        ZbierzDane();
        for (int o = 0; o < 100; o++) {
            zapis3.println("powtorzenie nr " + o);
            for (int epoka = 0; epoka < 100; epoka++) {
                long czas = System.currentTimeMillis();
                for (int i = 0; i < 70; i++) {
                    error = 0;
                    error2 = 0;
                    MSE = 0;
                    MSE1 = 0;
                    for (int j = 0; j < 4; j++) {
                        Warstwy[0].Perceptony[j].uczPercepton(uczace[i]);
                        Warstwy[0].wynikWarstwy[j] = Warstwy[0].Perceptony[j].wynik;
                    }
                    Warstwy[1].Perceptony[0].uczPercepton(Warstwy[0].wynikWarstwy);

                    if (Math.abs(Warstwy[1].Perceptony[0].wynik - f(uczace[i][4])) > 0.1) {
                        double sigma = uczace[i][4] - Warstwy[1].Perceptony[0].wynik;
                        double[] sigmaWag = new double[4];
                        for (int k = 0; k < 4; k++) {
                            sigmaWag[k] = Warstwy[1].Perceptony[0].wagi[k] * sigma;
                        }

                        for (int m = 0; m < 4; m++) {
                            for (int k = 0; k < 4; k++) {
                                for (int l = 0; l < 4; l++) {
                                    Warstwy[0].Perceptony[k].wagi[l] += 0.01 * sigmaWag[m] * uczace[i][l] * Warstwy[0].Perceptony[k].pochodnaf(Warstwy[0].Perceptony[k].wynik);
                                }
                            }
                        }
                        for (int k = 0; k < 4; k++) {
                            Warstwy[1].Perceptony[0].wagi[k] += 0.01 * sigma * uczace[i][4] * Warstwy[0].Perceptony[0].pochodnaf(Warstwy[0].Perceptony[0].wynik);
                        }

                    }

                }


                long czas2 = System.currentTimeMillis();
                zapis.println(czas2 - czas);
                check();
                zapis1.println(error);
                zapis2.println(MSE * 1 / 30);
                zapis12.println(error2);
                zapis22.println(MSE1 * 1 / 70);
                zapis3.println("Epoka nr " + epoka + "dlugosc uczenia epoki: " + (czas2 - czas) + " Ilosc błędów uczace: " + error + " MSE " + MSE * 1 / 300 + " Ilosc błędów walidujace: " + error2 + " MSE " + MSE1 * 1 / 700);

            }
        }
        zapis.close();
        zapis1.close();
        zapis2.close();
        zapis12.close();
        zapis22.close();
    }
    public void check()
    {
        for(int i=0;i<30;i++)
        {
            for(int j=0;j<4;j++)
            {
                Warstwy[0].Perceptony[j].uczPercepton(walidujace[i]);
                Warstwy[0].wynikWarstwy[j] = Warstwy[0].Perceptony[j].wynik;
            }
            Warstwy[1].Perceptony[0].uczPercepton(Warstwy[0].wynikWarstwy);
            if (Math.abs(Warstwy[1].Perceptony[0].wynik - f(walidujace[i][4])) > 0.1) {
                error++;
                MSE+=Math.pow((Warstwy[1].Perceptony[0].wynik-walidujace[i][4]),2);
            }
        }
        for(int i=0;i<70;i++)
        {
            for(int j=0;j<4;j++)
            {
                Warstwy[0].Perceptony[j].uczPercepton(uczace[i]);
                Warstwy[0].wynikWarstwy[j] = Warstwy[0].Perceptony[j].wynik;
            }
            Warstwy[1].Perceptony[0].uczPercepton(Warstwy[0].wynikWarstwy);
            if (Math.abs(Warstwy[1].Perceptony[0].wynik - f(uczace[i][4])) > 0.1) {
                error2++;
                MSE1+=Math.pow((Warstwy[1].Perceptony[0].wynik-uczace[i][4]),2);
            }
        }
    }

}


