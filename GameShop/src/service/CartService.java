package service;

import domain.Cart;
import domain.Item;
import persistence.CartDAO;
import persistence.ItemDao;
import persistence.impl.ItemDaoImpl;

import java.util.List;

public class CartService {
    CartDAO cartDao = new CartDAO();
    ItemDao itemDAO = new ItemDaoImpl();    // 用来获取 item 的信息

    // 添加到购物车或更新数量
    public void addToCart(Cart cart) {
        if (cartDao.itemExists(cart.getUserId(), cart.getItemId())) {

        } else {
            // 如果商品不存在，则插入
            cartDao.insertCart(cart);
        }
    }

    // 删除购物车中的商品
    public void deleteCartItem(int userId, int itemId) {
        cartDao.deleteCartItem(userId, itemId);
    }

    // 获取用户购物车中的商品
    public List<Cart> getCartItemsByUserId(int userId) {
//        return cartDao.getCartItemsByUserId(userId); // 根据用户 ID 获取购物车商品
        List<Cart> cartItems = cartDao.getCartItemsByUserId(userId); // 获取购物车商品
        for (Cart cartItem : cartItems) {
            Item item = itemDAO.getItemById(cartItem.getItemId()); // 获取商品的详细信息
            cartItem.setItem(item); // 将商品信息设置到 Cart 中
        }
        return cartItems;
    }
}
