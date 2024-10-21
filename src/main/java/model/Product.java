package model;
/**
 * Represents a product in the system.
 * This class encapsulates details about a product, including its unique identifier, name, available quantity, and price.
 */
public class Product {
    private int productId;
    private String productName;
    private int quantity;
    private double price;
    /**
     * Constructs a new Product with specified details.
     *
     * @param productId The unique identifier for the product.
     * @param productName The name of the product.
     * @param quantity The quantity of the product available.
     * @param price The price of the product.
     */
    public Product(int productId,String productName,int quantity,double price)
    {     super();
        this.productId=productId;
        this.productName=productName;
        this.quantity=quantity;
        this.price=price;

    }
    /**
     * Sets the unique identifier of the product.
     *
     * @param productId the new product ID.
     */
    public void setId(int productId) {
        this.productId = productId;
    }
    /**
     * Gets the price of the product.
     *
     * @return the price.
     */
    public double getPrice() {
        return price;
    }
    /**
     * Sets the price of the product.
     *
     * @param price the new price of the product.
     */
    public void setPrice(double price) {
        this.price = price;
    }
    /**
     * Gets the unique identifier of the product.
     *
     * @return the product ID.
     */
    public int getId() {
        return productId;
    }
    /**
     * Gets the quantity of the product available.
     *
     * @return the quantity.
     */
    public int getQuantity() {
        return quantity;
    }
    /**
     * Gets the name of the product.
     *
     * @return the product name.
     */
    public String getProductName() {
        return productName;
    }
    /**
     * Sets the name of the product.
     *
     * @param productName the new name of the product.
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }
    /**
     * Sets the quantity of the product available.
     *
     * @param quantity the new quantity of the product.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    /**
     * Returns a string representation of the product, containing all relevant information.
     *
     * @return a string representation of the product.
     */
    @Override
    public String toString() {
        return "Product [id=" + productId + ", product_name=" + productName + "quantity= " + quantity + " price= " + price
                + "]";
    }


}
