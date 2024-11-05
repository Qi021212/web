package service;

import dao.CartDAO;
import model.Cart;

import java.util.List;

public class CartService {
    CartDAO cartDao = new CartDAO();

//    // 创建购物车或更新购物车中的商品
//    public void addToCart(Cart cart) {
//        cartDao.addToCart(cart); // 将商品添加到购物车
//    }

    // 添加到购物车或更新数量
    public void addToCart(Cart cart) {
        if (cartDao.itemExists(cart.getUserId(), cart.getItemId())) {
            // 如果商品已存在，则更新数量
            cartDao.updateCartItem(cart.getUserId(), cart.getItemId(), cart.getQuantity());
        } else {
            // 如果商品不存在，则插入
            cartDao.insertCart(cart);
        }
    }

    public void updateCartItem(int userId, int itemId, int quantityChange) {
        Cart cartItem = cartDao.getCartItem(userId, itemId);
        if (cartItem != null) {
            int newQuantity = cartItem.getQuantity() + quantityChange;
            if (newQuantity <= 0) {
                cartDao.deleteCartItem(userId, itemId);
            } else {
                cartDao.updateCartItem(userId, itemId, quantityChange);
            }
        }
    }
    // 更新购物车中商品的数量
//    public void updateCartItem(int userId, int itemId, int quantityChange) {
////        // 这里你可以根据数量变化量进行更新
////        cartDao.updateCartItem(userId, itemId, quantityChange);
//
//        // 更新购物车中商品数量
//        if (quantityChange > 0) {
//            cartDao.updateCartItem(userId, itemId, quantityChange);
//        } else {
//            // 如果数量减少，检查是否减少到零，若是则删除
//            Cart cartItem = cartDao.getCartItem(userId, itemId);
//            if (cartItem != null) {
//                int newQuantity = cartItem.getQuantity() + quantityChange;
//                if (newQuantity <= 0) {
//                    deleteCartItem(userId, itemId); // 数量为0，删除商品
//                } else {
//                    cartDao.updateCartItem(userId, itemId, quantityChange); // 更新数量
//                }
//            }
//        }
//    }

    // 删除购物车中的商品
    public void deleteCartItem(int userId, int itemId) {
        cartDao.deleteCartItem(userId, itemId);
    }

    // 获取用户购物车中的商品
    public List<Cart> getCartItemsByUserId(int userId) {
        return cartDao.getCartItemsByUserId(userId); // 根据用户 ID 获取购物车商品
    }

//    // 调整购物车商品数量
//    public int editCart(Cart cart){
//        return cartDao.editCart(cart);
//    }

//    // 查询购物车数量
//    public int queryCartAmount(int userId){
//        return cartDao.queryCartAmount(userId);
//    }
//    // 查询购物车
//    public List<Cart> queryCartList (Cart cart){
//        return cartDao.queryCartList(cart);
//    }
//
//    // 根据商品ID查询
//    public Cart queryCartByGoodsId (Cart c){
//        return cartDao.queryCartByGoodsId(c);
//    }
//    // 删除购物车中的商品
//    public int deleteCart(Cart cart){
//        return cartDao.deleteCart(cart);
//    }
//
//    // 清空购物车
//    public int deleteCartAll(Cart cart){
//        return cartDao.deleteCartAll(cart);
//    }
//
//    // 添加商品
//    public int cartBuy(Cart cart){
//        int result = 0;
//        Cart c = cartDao.queryCartByGoodsId(cart);
//        if(c != null){ // 如果该商品在购物车已存在，则商品数量加1
//            cart.setId(c.getId());
//            cart.setAmount(1);
//            result =cartDao.editCart(cart);
//        }else{
//            // 如果该商品在购物车中不存在，则创建该商品
//            result = cartDao.insertCart(cart);
//        }
//
//        return result;
//    }
//
//    // 减少商品
//    public int cartLessen(Cart cart){
//        int result = 0;
//        Cart c = cartDao.queryCartByGoodsId(cart);
//
//        if(c != null){
//            cart.setId(c.getId());
//            // 如果该商品数量为1，就从购物车删除
//            if(c.getAmount() == 1){
//                result = cartDao.deleteCart(cart);
//            }else{
//                // 如果该商品数量大于1，数量减1
//                cart.setAmount(-1);
//                result =cartDao.editCart(cart);
//            }
//
//        }
//
//        return result;
//    }
}
