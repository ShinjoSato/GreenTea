/**
 * @author Shinjo Sato
 * @version 15-12-2019
 */
public class Main{
    public static void main(final String[] args){
        /**
         * @param symbols   This is the strings, which is spritted by a space, of some
         *                  program text, which is GreenTea file.
         */
        String[] symbols = Input.readFile(args[0]);  //String[] symbols = str.split(" ");
        CPU.init(symbols);
    }
}