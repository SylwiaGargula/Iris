import java.io.FileNotFoundException;

/**
 * Created by Sylwia on 2016-10-23.
 */
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
     HebbaBezNauczyciela psi=new HebbaBezNauczyciela();
        psi.startlearnig();

        HebbaBezNauczycielaZZapominaniem psi1=new HebbaBezNauczycielaZZapominaniem();
        psi1.startlearnig();
//
//        HebbaZNauczycielem psi=new HebbaZNauczycielem();
//        psi.startlearnig();
//
//        HebbaZNauczycielemZZapominaniem psi=new HebbaZNauczycielemZZapominaniem();
//        psi.startlearnig();
//
        OjiBezNauczyciela psi2=new OjiBezNauczyciela();
        psi2.startlearnig();
//
//        OjiZNauczycielem psi=new OjiZNauczycielem();
//        psi.startlearnig();

    }
}
