import java.io.FileNotFoundException;

/**
 * Created by Sylwia on 2016-10-23.
 */
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
       Percepton percepton=new Percepton();
          percepton.startlearnig();


        MCP Mcp=new MCP();
        try {
           Mcp.startlearnig();
        } catch (FileNotFoundException e) {
           e.printStackTrace();
       }

    }


}
