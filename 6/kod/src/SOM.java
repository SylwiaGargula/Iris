import javax.swing.*;
import java.awt.*;

/**
 * Created by Sylwia on 2016-12-11.
 */
public class SOM extends JFrame {

    JButton[][] buttons=new JButton[10][10];
    int x=0;
    int y=0;
    int [][]tablica;
    SOM(int [][]ile)
    {
        tablica=ile;
        setSize(1110,730);
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        int max=tablica[0][0];
        int min=tablica[0][0];
        for(int i=0;i<10;i++)
        {
            for(int j=0;j<10;j++) {
                if (tablica[i][j] > max) {
                    max = tablica[i][j];

                } else if (tablica[i][j] < min && tablica[i][j] != 0) {
                    min = tablica[i][j];
                }
            }
        }
        for(int i=0;i<10;i++)
        {
            for(int j=0;j<10;j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setBounds(x,y,100,70);
                x+=100;
                buttons[i][j].setBackground(Color.white);
                add(buttons[i][j]);
                setButton(i,j,max,min);
            }
            y+=70;
            x=0;
        }
    }

    void setButton(int indeks1,int indeks2,int max,int min) {
        buttons[indeks1][indeks2].setText(String.valueOf(tablica[indeks1][indeks2]));
        if (tablica[indeks1][indeks2] == 0) {
            buttons[indeks1][indeks2].setBackground(Color.white);
        } else {

            double min_max = max - min;
            double moja = (tablica[indeks1][indeks2] / min_max)*255;
            int R = (int) (0 + moja);
            int B = (int) (255 - moja);

            if(R>255)
                R=255;
            else if(R<0)
                R=0;

            if(B>255)
                B=255;
            else if(B<0)
                B=0;

            buttons[indeks1][indeks2].setBackground(new Color(R,0,B));

        }
    }
}
