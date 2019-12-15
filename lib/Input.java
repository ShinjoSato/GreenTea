import java.io.*;
import java.util.*;
/**
 * This is a Input class, which is like the Von Neumann architecture.
 * @author Shinjo Sato
 * @version 15-12-2019
 */
public class Input{
    /**
     * This is a function which return strings in the file.
     * @param filename      This is a file name which is read by this program.
     * @return strings in the file.
     */
    public static String[] readFile(final String filename){
        final ArrayList<String> array = new ArrayList<String>();
        try {
            final Scanner scanner = new Scanner(new File(filename));
            while(scanner.hasNext()) {
                array.add(scanner.next());
            }
        } catch (final FileNotFoundException e) {
            System.out.println("File doesn't exist.");
        }
        return array.toArray(new String[array.size()]);
    }
}