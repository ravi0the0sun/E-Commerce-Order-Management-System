import java.io.File;
import java.io.IOException;

public class Util {
    public static boolean create_file(File file) {
        try {
            if (file.createNewFile()) {
                return true;
            } else {
                throw new IOException("File already exists");
            }
        } catch (IOException e) {
            System.out.println("Error creating file");
            System.out.println(e.toString());
            return false;
        }
    }

    public static boolean isInt(String i) {
        try {
            Integer.parseInt(i);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isDouble(String i) {
        try {
            Double.parseDouble(i);
            return true;
        } catch (NumberFormatException e) {
            System.out.println(e.toString());
            return false;
        }
    }
}
