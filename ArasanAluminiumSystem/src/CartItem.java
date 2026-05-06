import javax.swing.*; 
public class CartItem {
    public int productId; 
    public String name;
    public int price;
    public int quantity;
    public String imagePath;
    public CartItem(int productId, String name, int price, int quantity, String imagePath) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imagePath = imagePath;
    }
    public int getSubtotal() {
        return price * quantity;
    }
}