import java.util.Date;
import java.util.UUID;

public class Order {
    private String order_id;
    private String customer_id;
    private Date order_date;
    private boolean order_status;
    private Product[] products;

    public Order(String customer_id, Date order_date, Product[] products) {
        this.order_id = UUID.randomUUID().toString();
        this.customer_id = customer_id;
        this.order_date = order_date;
        this.products = products;
        this.order_status = false;
    }

    public Order(String order_id, String customer_id, Date order_date, Product[] products) {
        this.order_id = order_id;
        this.customer_id = customer_id;
        this.order_date = order_date;
        this.products = products;
        this.order_status = false;
    }

    public String get_order_id() {
        return this.order_id;
    }

    public String get_customer_id() {
        return this.customer_id;
    }

    public void set_order_status(boolean order_status) {
        this.order_status = order_status;
    }

    public boolean get_order_status() {
        return this.order_status;
    }

    public Product[] get_products() {
        return this.products;
    }

    public void add_products(Product[] products) {
        Product[] new_list = new Product[products.length + this.products.length];
        System.arraycopy(this.products, 0, new_list, 0, this.products.length);
        System.arraycopy(products, 0, new_list, this.products.length, products.length);
        this.products = new_list;
    }

    public String save_order() {
        StringBuilder order_date = new StringBuilder(String.format("%s,%s,%s,%s", this.order_id, this.customer_id, this.order_date, this.order_status));
        for (Product p : this.products) {
            order_date.append(",").append(p.get_product_id());
        }

        return order_date.toString();
    }
}
