import java.util.UUID;

public class Product {
    private String product_id;
    private String product_name;
    private double price;
    private int quantity;
    private String description;

    public Product(String product_id, String product_name, double price, int quantity, String description) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
    }

    public Product() {
        this.product_id = UUID.randomUUID().toString();
    }

    public void set_product_name(String product_name) {
        this.product_name = product_name;
    }

    public void set_price(double price) {
        this.price = price;
    }

    public void set_quantity(int quantity) {
        this.quantity = quantity;
    }

    public void update_quantity(int number) {
        this.quantity -= number;
    }

    public String get_product_name() {
        return this.product_name;
    }

    public String get_product_id() {
        return this.product_id;
    }

    public double get_price() {
        return this.price;
    }

    public int get_quantity() {
        return this.quantity;
    }

    public String get_description() {
        return this.description;
    }
}
