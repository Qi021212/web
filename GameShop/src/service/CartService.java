package service;

import domain.Cart;
import domain.Item;
import persistence.impl.CartDAOImpl;
import persistence.ItemDao;
import persistence.impl.ItemDaoImpl;

import java.util.List;

public class CartService {
    CartDAOImpl cartDao = new CartDAOImpl();
    ItemDao itemDAO = new ItemDaoImpl();

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
//        Cart cartItem = cartDao.getCartItem(userId, itemId);
//        if(cartItem != null) {
//            cartItem.setInCart(0);  //标记为逻辑删除
//            cartDao.updateCartItem(cartItem);   //更新数据库
//        }
    }

    // 获取用户购物车中的商品
    public List<Cart> getCartItemsByUserId(int userId) {
        List<Cart> cartItems = cartDao.getCartItemsByUserId(userId); // 获取购物车商品
        for (Cart cartItem : cartItems) {
            Item item = itemDAO.getItemById(cartItem.getItemId()); // 获取商品的详细信息
            cartItem.setItem(item); // 将商品信息设置到 Cart 中
        }
        return cartItems;
    }

    //点击对应某商品的多选框更新数据库表中该用户该商品对应的isSelected值，多选框选中则为1，未选中或者取消选中为0
    public void updateCartSelection(int userId, int selectedItemId, int isSelected) {
        isSelected = isSelected == 1 ? 1 : 0;
        cartDao.updateCartSelection(userId, selectedItemId, isSelected); // 设置选中的商品
    }
}
