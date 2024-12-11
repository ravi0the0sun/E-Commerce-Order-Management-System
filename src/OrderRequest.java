import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class OrderRequest {
    public static void order_confirm(Customer customer, ArrayList<Product> cart) {
        JFrame frame = new JFrame("Cart");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1, 10, 10));

        JScrollPane product_scroll_pane = new JScrollPane(panel);
        product_scroll_pane.setBorder(BorderFactory.createTitledBorder("Product List"));
        frame.add(product_scroll_pane, BorderLayout.NORTH);

        JPanel footer = new JPanel(new BorderLayout());
        JPanel inner_price_panel = new JPanel(new BorderLayout());
        JPanel price_panel = new JPanel(new BorderLayout());

        JButton confirm = new JButton("Confirm");
        JButton back = new JButton("Back");

        frame.add(footer, BorderLayout.SOUTH);

        confirm.addActionListener(e -> {
            write_order(customer, cart);
        });

        back.addActionListener(e -> {
           int response =  JOptionPane.showConfirmDialog(frame, "Do you want to continue? If yes your cart will be deleted", "Confirm", JOptionPane.YES_NO_OPTION);
           if (response == JOptionPane.YES_OPTION) {
               frame.dispose();
               ProductRequest.show_product(customer);
           } else {
               return;
           }
        });

        double total_price = 0;

        for (Product product : cart) {
            total_price += product.get_price();
            JPanel product_panel = new JPanel(new BorderLayout());
            JLabel product_name = new JLabel(product.get_product_name());
            JLabel product_price = new JLabel(String.valueOf("$" + product.get_price()));
            product_panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

            product_panel.add(product_name, BorderLayout.WEST);
            product_panel.add(product_price, BorderLayout.EAST);

            panel.add(product_panel);
        }

        JLabel price = new JLabel(String.valueOf("Total: $" + total_price));
        price_panel.add(price, BorderLayout.EAST);
        inner_price_panel.add(price, BorderLayout.EAST);
        footer.add(inner_price_panel, BorderLayout.NORTH);

        JPanel inner_footer = new JPanel();
        inner_footer.add(back, BorderLayout.WEST);
        inner_footer.add(confirm, BorderLayout.EAST);
        footer.add(inner_footer, BorderLayout.SOUTH);

        frame.setVisible(true);

    }

    public static void write_order(Customer customer, ArrayList<Product> cart) {
        File order_file = new File("order_list.txt");
        try {
            Scanner scanner = new Scanner(order_file);
        } catch (FileNotFoundException e) {
            create_file(order_file);
        }
        Order order = new Order(customer.get_customer_id(), cart.toArray(Product[]::new));
        System.out.println(order.save_order());
    }

    public static void create_file(File file) {
        try  {
            if (file.createNewFile()) {
                System.out.println("File created");
            } else {
                throw new IOException();
            }
        }catch(IOException e) {
            System.out.println("Error creating file");
            JOptionPane.showMessageDialog(null, "Error creating file, Closing application");

        }
    }
}
