import java.util.UUID;

public class Customer {
    private String customer_id;
    private String name;
    private int phone;
    private String email;

    public Customer(String name, String email, int phone) {
        this.customer_id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.phone = phone;

    }

    public Customer(String customer_id, String name, String email, int phone) {
        this.customer_id = customer_id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public Customer() {
        this.customer_id = UUID.randomUUID().toString();
    }

    public String get_customer_id() {
        return customer_id;
    }

    public String get_name() {
        return this.name;
    }

    public int get_phone() {
        return this.phone;
    }
    public String get_email() {
        return this.email;
    }
    public void set_name(String name) {
        this.name = name;
    }
    public void set_phone(int phone) {
        this.phone = phone;
    }
    public void set_email(String email) {
        this.email = email;
    }

    public String toString() {
        return String.format("%s,%s,%s,%d", this.customer_id, this.name, this.email, this.phone);
    }
}
