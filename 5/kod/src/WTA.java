
import java.util.Random;

public class WTA {

int [] iloscwklasie =new int[100];

    double []wagi=new double[4];
    double wynik;

   public  WTA()
    {
        losujWagi();

    }

   public void losujWagi()
    {
        Random random=new Random();
        for(int i=0;i<4;i++)
        {
            wagi[i]=random.nextDouble();
        }
    }

    public void sprawdz(double[]tablica)
    {
        //suma
        wynik=0;
        for(int i=0;i<4;i++)
        wynik+=tablica[i]*wagi[i];
    }

    public  void  uczWTA(double[] tablica,int x) {

        for(int i=0;i<4;i++)
        {
            wagi[i]+=0.01*(tablica[i]-wagi[i]);

        }
            iloscwklasie[x]++;

    }

}
