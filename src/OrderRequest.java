import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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
}
