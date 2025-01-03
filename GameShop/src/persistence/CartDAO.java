package persistence;

import domain.Cart;

import java.util.List;

public interface CartDAO {
    public void addToCart(Cart cart);
    public boolean itemExists(int userId, int itemId);
    public List<Cart> getCartItemsByUserId(int userId);
    public void updateCartItemSelection(int userId, int itemId, int isSelected);
    public Cart getCartItem(int userId, int itemId);
    public void updateCartItem(Cart cart);
    public int insertCart(Cart cart);
    public void deleteCartItem(int userId, int itemId);
    public void deleteAllItemsByUserId(int userId);
}
