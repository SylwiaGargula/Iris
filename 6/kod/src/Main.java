import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {

    static double [][]uczace=new double[1050][5];
    static double [][]uczace1=new double[1050][5];
    static double [][]walidujace=new double[300][5];
    static int liczbaneuronow=10;
    static double promienSasiedztwa=2;


    public static void main(String[] args) throws FileNotFoundException {
        ZbierzDane();

        int[][] ile = new int[liczbaneuronow][liczbaneuronow];
        for(int x=0;x<1;x++) {
            PrintWriter zapis = new PrintWriter(x+ "klasyfikacja.txt");
            WTA[][] neurony = new WTA[liczbaneuronow][liczbaneuronow];
            PrintWriter[][] zapisy = new PrintWriter[liczbaneuronow][liczbaneuronow];
            for (int i = 0; i < liczbaneuronow; i++) {
                for (int j = 0; j < liczbaneuronow; j++) {
                    neurony[i][j] = new WTA();
                    zapisy[i][j]=new PrintWriter("neurony/"+(i*10+j)+"-kolory.txt");
                    ile[i][j] = 0;
                }
            }
            for (int i = 0; i < 100; i++) {
                for (int j = 0; j < 1050; j++) {
                    int y = 0,z=0;
                    for (int k = 0; k < liczbaneuronow; k++) {
                        for (int l = 0; l < liczbaneuronow; l++) {
                            neurony[k][l].sprawdz(uczace[j]);
                            if (neurony[k][l].wynik > neurony[z][y].wynik) {
                                z = k;
                                y = l;
                            }

                        }
                    }
                    if (i == 99) {
                        ile[z][y]++;
                        for (int l = 0; l < 5; l ++) {
                            zapisy[z][y].print(uczace1[j][l] + " ");

                        }

                        zapisy[z][y].println();
                    }
                    // neurony[z][y].uczWTA(uczace[j]);
                    //uczSom(uczace[j],z,y,neurony);
                    for(int a=0;a<liczbaneuronow;a++)
                    {
                        for(int b=0;b<liczbaneuronow;b++)
                        {
                            neurony[a][b].uczSasiedztwo(uczace[j],z,y,a,b);
                        }
                    }
                }

            }
            for(int i=0;i<liczbaneuronow;i++)
                for(int j=0;j<liczbaneuronow;j++)
                    zapis.println(ile[i][j]);
            zapis.close();
            SOM map = new SOM(ile);
            for(int i=0;i<liczbaneuronow;i++)
                for(int j=0;j<liczbaneuronow;j++) {
                    zapisy[i][j].close();

                }

        }



    }



    public static void ZbierzDane() {

        File file = new File("kwiotki.txt");
        Scanner in = null;
        try {
            in = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String[] tablica1;

        for (int j = 0; j <1050; j++) {
            tablica1 = in.nextLine().split(",");

            for (int i = 0; i < 4; i++) {
                uczace[j][i] = Double.parseDouble(tablica1[i]);
                uczace1[j][i]=uczace[j][i];
            }
            if (tablica1[4] == "Iris-setosa") {
                uczace[j][4] = 0;
                uczace1[j][4] = 0;
            } else {
                uczace[j][4] = 1;
                uczace1[j][4] = 1;
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
