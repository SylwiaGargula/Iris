import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Sylwia on 2016-10-23.
 */
public class Percepton {



    double []wagi=new double[4];
    double wynik=0;


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



//sigomidalna
    double pochodnaf(double x)
    {
        return (Math.sqrt(Math.E))/Math.pow(Math.sqrt(Math.E)*x+1,2);
    }

    double f(double x)
    {
        return(1/(1+Math.pow(Math.E,(-0.5*x))));

    }
    //gaussa
    double pochodnaf1(double x)
    {
        return  (((-5)/16)*(x-3)* Math.pow(Math.E,((-1)/32)*Math.pow(x-3,2)))  ;
    }

    double f1(double x)
    {
        return (5 * Math.pow(Math.E, (-1) * ((Math.pow(x - 3, 2)) / (2 * Math.pow(4, 2)))));
    }




    public void uczPercepton(double[] wartosciNowe) {

      //  losujWagi();
      //  System.out.println("uczy");
        double sumuj=0;
        for(int i=0; i<4;i++)
        {
            sumuj+=wartosciNowe[i]*wagi[i];
        }
     wynik=f(sumuj); //sprawdzanie z wynikiem w pliku



    }


}
