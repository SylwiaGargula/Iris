
import java.util.Random;

public class WTA {

    double promienSasiedztwa=2;
    double []wagi=new double[5];
    double wynik;

    public WTA()
    {
        losujWagi();

    }

    public void losujWagi()
    {
        Random random=new Random();
        for(int i=0;i<5;i++)
        {
            wagi[i]=random.nextDouble();
        }
    }

    public void sprawdz(double[]tablica)
    {
        wynik=0;
        for(int i=0;i<5;i++)
            wynik+=tablica[i]*wagi[i];
    }

    public  void  uczWTA(double[] tablica) {

        for(int i=0;i<10;i++)
        {
            wagi[i]+=0.01*(tablica[i]-wagi[i]);

        }



    }
    public void uczSasiedztwo(double []tabD, int wiersz, int kolumna, int moj_wiersz, int moja_kolumna){
        double odleglosc=Math.sqrt((wiersz-moj_wiersz)*(wiersz-moj_wiersz)+(kolumna-moja_kolumna)*(kolumna-moja_kolumna));
        for (int j = 0; j < 5; j++) {
            wagi[j] += 0.1 * G(odleglosc,promienSasiedztwa) *(tabD[j] - wagi[j]); //WTM
        }
    }
    public static double G(double odleglosc,double promienSasiedztwa)
    {
        return(Math.exp(-(odleglosc*odleglosc/(2*Math.pow(promienSasiedztwa,2)))));
    }

//    public static double G(double odleglosc,double promienSasiedztwa)
//    {
//        if(odleglosc<=promienSasiedztwa)
//            return 1;
//        else return 0;
//    }


}
