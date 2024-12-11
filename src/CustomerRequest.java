import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CustomerRequest {
    public static Customer login(String email) {
        File customer_file = new File("customer_list.txt");
        try (Scanner scanner = new Scanner(customer_file)) {
            while (scanner.hasNextLine()) {
                String[] customer_data = scanner.nextLine().split(",");
                if (customer_data[2].equals(email)) {
                    return new Customer(customer_data[0], customer_data[1], customer_data[2], Integer.parseInt(customer_data[3]));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            if (Util.create_file(customer_file)) {
                System.out.println("File Created");
                System.out.println("There are no customers");
            }
        }
        return null;
    }

    public static Customer sign_in(String name, String email, int number) {
        File customer_file = new File("customer_list.txt");
        try (Scanner scanner = new Scanner(customer_file)) {
            while (scanner.hasNextLine()) {
                String[] customer_data = scanner.nextLine().split(",");
                if (customer_data[2].equals(email)) {
                    return null;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            if (Util.create_file(customer_file)) {
                System.out.println("File Created");
            } else {
                return null;
            }
        }
        Customer customer = new Customer(name, email, number);
        try (FileWriter fileWriter = new FileWriter(customer_file, true)) {
            fileWriter.write("\n" + customer.toString());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Error while writing to file");
            return null;
        }
        return customer;
    }

    public static void login_screen() {
        JFrame frame = new JFrame("Login Screen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,200);
        frame.setLayout(new GridLayout(2, 2, 10, 10));
        frame.setLocationRelativeTo(null);

        JLabel email_label = new JLabel("Enter Email");
        JTextField email_txt = new JTextField();

        JButton login_btn = new JButton("Login");
        JButton sign_up_btn = new JButton("Sign Up");

        frame.add(email_label);
        frame.add(email_txt);
        frame.add(sign_up_btn);
        frame.add(login_btn);

        login_btn.addActionListener(e -> {
            String email = email_txt.getText();
            Customer customer = login(email);
            if (!email.contains("@") || customer == null) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid email address");
            } else {
                JOptionPane.showMessageDialog(frame, "You have successfully logged in");
                ProductRequest.selection_screen(customer);
                frame.dispose();
            }
        });

        sign_up_btn.addActionListener(e -> {
            sign_in_screen();
            frame.dispose();
        });

        frame.setVisible(true);
    }

    public static void sign_in_screen() {
        JFrame frame = new JFrame("Login Screen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,200);
        frame.setLayout(new GridLayout(4, 2, 10, 10));
        frame.setLocationRelativeTo(null);

        JLabel name_label = new JLabel("Enter Name");
        JTextField name_txt = new JTextField();

        JLabel email_label = new JLabel("Enter Email");
        JTextField email_txt = new JTextField();

        JLabel phone_label = new JLabel("Enter Phone Number");
        JTextField phone_txt = new JTextField();

        JButton login_btn = new JButton("Login");
        JButton sign_up_btn = new JButton("Sign Up");

        frame.add(name_label);
        frame.add(name_txt);
        frame.add(email_label);
        frame.add(email_txt);
        frame.add(phone_label);
        frame.add(phone_txt);
        frame.add(login_btn);
        frame.add(sign_up_btn);



        login_btn.addActionListener(e -> {
            login_screen();
            frame.dispose();
        });

        sign_up_btn.addActionListener(e -> {
            String email = email_txt.getText();
            String name = name_txt.getText();
            String phone = phone_txt.getText();

            Customer customer = null;

            if ((!(phone.length() == 10)) || !(Util.isInt(phone))) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid phone number");
                return;
            }

            if (!email.contains("@")) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid email address");
                return;
            }

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid name");
                return;
            }

            customer = sign_in(name, email, Integer.parseInt(phone));

            if (customer == null) {
                JOptionPane.showMessageDialog(frame, "A System Error Occurred");
                JOptionPane.showMessageDialog(frame, "Start Application Again");
                System.exit(0);
            }

            JOptionPane.showMessageDialog(frame, "You have successfully Signed in");
            ProductRequest.selection_screen(customer);
            frame.dispose();

        });

        frame.setVisible(true);
    }
}
