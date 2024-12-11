import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
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

    public static void show_product(Customer customer) {
        JFrame frame = new JFrame("Product List");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1, 10, 10));

        JScrollPane product_scroll_pane = new JScrollPane(panel);
        product_scroll_pane.setBorder(BorderFactory.createTitledBorder("Product List"));
        frame.add(product_scroll_pane, BorderLayout.CENTER);

        ArrayList<Product> products = read_product();

        ArrayList<Product> cart = new ArrayList<>();

        JPanel footer = new JPanel();
        JButton confirm_button = new JButton("Confirm");
        footer.add(confirm_button, BorderLayout.EAST);

        JButton back_button = new JButton("Back");
        footer.add(back_button, BorderLayout.WEST);

        frame.add(footer, BorderLayout.SOUTH);

        confirm_button.addActionListener(e -> {
            if (cart.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Cart is empty");
                return;
            }
            OrderRequest.order_confirm(customer, cart);
            frame.dispose();

        });

        back_button.addActionListener(e -> {
            frame.dispose();
            selection_screen(customer);
        });

        for (Product product : products) {
            JPanel product_panel = new JPanel(new BorderLayout());
            JPanel inner_panel = new JPanel(new BorderLayout());
            product_panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

            JLabel product_name = new JLabel(product.get_product_name());
            JLabel product_price = new JLabel("$" + String.valueOf(product.get_price()));
            JLabel product_description = new JLabel(product.get_description());
            JButton add_button = new JButton("Add to Cart");


            add_button.addActionListener(e -> {
                if (product.get_quantity() == 0) {
                    JOptionPane.showMessageDialog(frame, "No Stock Available");
                    return;
                }
                JOptionPane.showMessageDialog(frame, "Adding " + product.get_product_name() + " to Cart ");
                cart.add(product);

            });

            inner_panel.add(product_name, BorderLayout.CENTER);
            inner_panel.add(product_price, BorderLayout.EAST);
            inner_panel.add(product_description, BorderLayout.SOUTH);
            product_panel.add(inner_panel, BorderLayout.CENTER);
            product_panel.add(add_button, BorderLayout.EAST);

            panel.add(product_panel);
        }

        frame.setVisible(true);
    }

    public static void selection_screen(Customer customer) {
        JFrame frame = new JFrame("Home");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));
        frame.add(panel);

        JLabel customer_name = new JLabel("Welcome " + customer.get_name());
        JButton buy_button = new JButton("Buy Product");
        JButton add_product = new JButton("Add Product");
        JButton log_out = new JButton("Log Out");

        buy_button.addActionListener(e -> {
            show_product(customer);
            frame.dispose();
        });

        add_product.addActionListener(e -> {
            add_product(customer);
            frame.dispose();
        });

        log_out.addActionListener(e -> {
            CustomerRequest.login_screen();
            frame.dispose();
        });


        panel.add(customer_name, BorderLayout.CENTER);
        panel.add(buy_button, BorderLayout.CENTER);
        panel.add(add_product, BorderLayout.CENTER);
        panel.add(log_out, BorderLayout.CENTER);

        frame.setVisible(true);

    }

    public static void add_product(Customer customer)  {
        JFrame frame = new JFrame("Add Product");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));
        frame.add(panel, BorderLayout.CENTER);

        JLabel product_name = new JLabel("Product Name");
        JTextField name_input = new JTextField();

        JLabel product_price = new JLabel("Product Price");
        JTextField price_input = new JTextField();

        JLabel product_quantity = new JLabel("Product Quantity");
        JTextField quantity_input = new JTextField();

        JLabel product_description = new JLabel("Product Description");
        JTextField description_input = new JTextField();

        JButton add_button = new JButton("Add Product");
        JButton back_button = new JButton("Back");

        add_button.addActionListener(e -> {
            String name = name_input.getText();
            String price = price_input.getText();
            String quantity = quantity_input.getText();
            String description = description_input.getText();

            if (name.isEmpty() || price.isEmpty() || quantity.isEmpty() || description.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Fields cannot be empty");
                return;
            }

            if (!(Util.isInt(quantity))) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number");
                return;
            }

            if (!(Util.isDouble(price))) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number");
                return;
            }

            Product product = new Product(name, Double.parseDouble(price), Integer.parseInt(quantity), description);

            edit_product(customer, product);
            frame.dispose();
        });

        back_button.addActionListener(e -> {
            selection_screen(customer);
            frame.dispose();
        });

        panel.add(product_name, BorderLayout.CENTER);
        panel.add(name_input, BorderLayout.CENTER);
        panel.add(product_price, BorderLayout.CENTER);
        panel.add(price_input, BorderLayout.CENTER);
        panel.add(product_quantity, BorderLayout.CENTER);
        panel.add(quantity_input, BorderLayout.CENTER);
        panel.add(product_description, BorderLayout.CENTER);
        panel.add(description_input, BorderLayout.CENTER);
        panel.add(add_button, BorderLayout.EAST);
        panel.add(back_button, BorderLayout.WEST);

        frame.setVisible(true);

    }

    public static void edit_product(Customer customer, Product product)  {
        File file = new File("product_list.txt");
        try (Scanner scanner = new Scanner(file)) {
            try (FileWriter fileWriter = new FileWriter(file, true)) {
                fileWriter.write("\n" + product.to_string());
                System.out.println("File Edited");
                JOptionPane.showMessageDialog(null, "Product Edited");
                add_product(customer);
            } catch (IOException e) {
                System.out.println("Error while editing product");
                System.exit(0);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(0);
        }
    }

}
