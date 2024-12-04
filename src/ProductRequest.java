import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ProductRequest {
    public static ArrayList<Product> read_product() {
        File product_file = new File("product_list.txt");
        try (Scanner scanner = new Scanner(product_file)) {
            ArrayList<Product> products = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String[] product_data = scanner.nextLine().split(",");
                Product product = new Product(product_data[0], product_data[1], Double.parseDouble(product_data[2]), Integer.parseInt(product_data[3]), product_data[4]);
                products.add(product);
            }
            return products;
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            if (Util.create_file(product_file)) {
                return new ArrayList<Product>();
            } else {
                System.out.println("Error while reading file");
                System.exit(0);
            }
        }
        return null;
    }

}
