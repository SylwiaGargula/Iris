import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {

    static double[][] uczace = new double[700][6];
    static double[][] walidujace = new double[300][6];
    static int liczbaneuronow = 3;
    private static int error = 0;
    private static int error1 = 0;
    private static double MSE = 0;
    private static double MSE1 = 0;


    public static void main(String[] args) throws FileNotFoundException {
        ZbierzDane();
        PrintWriter zapis = new PrintWriter("Podzial.txt");
        PrintWriter zapis1 = new PrintWriter("Uczenie logi.txt");
        for (int x = 0; x < 100; x++) {
            zapis1.println("powtorzenie nr " + x);
            int [] ile=new int[liczbaneuronow];
            WTA[] neurony = new WTA[liczbaneuronow];
            for (int i = 0; i < liczbaneuronow; i++) {
                neurony[i] = new WTA();
                ile[i]=0;
            }
            //szukanie zwyciezcy
            for (int i = 0; i < 100; i++) {
                for (int j = 0; j < 700; j++) {
                    int id = 0;
                    for (int k = 0; k < liczbaneuronow; k++) {
                        neurony[k].sprawdz(uczace[j]);
                        if (neurony[k].wynik > neurony[id].wynik)
                            id = k;
                    }
                    neurony[id].uczWTA(uczace[j], i);
                    uczace[j][5] = id;
                     if(i==99)
                        ile[id]++;
                }

            }
            zapis.print(x+" ");
            for(int j=0;j<liczbaneuronow;j++)
                zapis.print(ile[j]+" ");
            zapis.println();
            ucz(zapis1);
        }
        zapis.close();
        zapis1.close();
    }
    private static void ucz(PrintWriter zapis3) throws FileNotFoundException {
        for (int i = 0; i < liczbaneuronow; i++) {
            zapis3.println("Neuron nr " +i);
            String numer = "Klasa" + i;
            PrintWriter zapis = new PrintWriter(numer + "czas.txt");
            PrintWriter zapis1 = new PrintWriter(numer + "bledy.txt");
            PrintWriter zapis2 = new PrintWriter(numer + "MSE.txt");
            PrintWriter zapis12 = new PrintWriter(numer + "bledy2.txt");
            PrintWriter zapis22 = new PrintWriter(numer + "MSE2.txt");

            HebbaZWspolczynnikiemZapominania hebba = new HebbaZWspolczynnikiemZapominania();
            hebba.losujWagi();
            for (int j = 0; j < 100; j++) {
                error = 0;
                error1 = 0;
                MSE = 0.0;
                MSE1 = 0.0;
                //uczenie kazdej klasy osobno
                long czas = System.currentTimeMillis();
                for (int k = 0; k < 700; k++) {
                    if (uczace[k][5] == i) {
                        hebba.uczHebba(uczace[k]);
                    }

                }
                long czas2 = System.currentTimeMillis();
                zapis.println((czas2 - czas));
                for (int k = 0; k < 300; k++) {
                    if (walidujace[k][5] == i) {
                        hebba.check(walidujace[k], error1, MSE1);
                    }
                }
                for (int k = 0; k < 700; k++) {
                    if (uczace[k][5] == i) {
                        hebba.check(uczace[k], error, MSE);
                    }
                }


                zapis1.println(error);
                zapis2.println(MSE * 1 / 700);
                zapis12.println(error1);
                zapis22.println(MSE1 * 1 / 300);
                zapis3.println("Epoka nr "+j+"dlugosc uczenia epoki: "+(czas2 - czas)+" Ilosc błędów uczace: "+error+" MSE "+MSE+" Ilosc błędów walidujace: "+error+" MSE "+MSE );

            }
            zapis.close();
            zapis1.close();
            zapis2.close();
            zapis12.close();
            zapis22.close();

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

        for (int j = 0; j < 700; j++) {
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



        for (int j = 0; j < 300; j++) {
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
