package service;

import persistence.CartDAO;
import persistence.OrderDAO;
import domain.*;
import utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class OrderService {
    private OrderDAO oDao = new OrderDAO();
    CartDAO cartDao = new CartDAO();

//    public void addOrder(Order order) {
//        Connection con = null;
//        try {
//            con = DBUtil.getConnection();
//            con.setAutoCommit(false); // 开启事务
//
//            // 查询购物车商品
//            List<Cart> cartList = cartDao.getCartItemsByUserId(order.getUser().getId());
////            Cart cart = new Cart();
////            cart.setUserId(order.getUser().getId());
////            List<Cart> cartList = cartDao.queryCartList(cart);
//            // 计算订单总金额
//            double total =0;
//            int totalAmount = 0;
//            for (Cart c : cartList) {
//                total += c.getPrice()*c.getQuantity();
//                totalAmount += c.getQuantity();
//            }
//            order.setTotal(total);
//            order.setAmount(totalAmount);
//            // 计算商品数量
////            int amount = cartDao.queryCartAmount(order.getUser().getId());
////            order.setAmount(amount);
////
////            oDao.insertOrder(con, order);
////            int id = oDao.getLastInsertId(con);
////            order.setId(id);
//
//            //插入订单
//            int orderId = oDao.insertOrder(order);
//            order.setId(orderId);
//
//            // 循环购物车列表，插入订单项
//            for (Cart c:cartList) {
//                OrderItem item = new OrderItem();
//                item.setPrice(c.getPrice());
//                item.setAmount(c.getQuantity());
//                item.setItem(new Item(c.getItemId()));
////                Item goods = new Item();
////                goods.setId(c.getItemId());
////                item.setItem(goods);
////                Order order1 = new Order();
////                order1.setId(id);
//                item.setOrder(order);   //关联订单
//                oDao.insertOrderItem(item);
//            }
//
//            // 清空购物车
//            for (Cart c : cartList) {
//                cartDao.deleteCartItem(order.getUser().getId(), c.getItemId());
//            }
////            cartDao.deleteCartAll(cart);
//            con.commit(); // 提交事务
//        } catch (Exception e) {
//            e.printStackTrace();
//            if(con != null) {
//                try {
//                    con.rollback(); // 回滚事务
//                } catch (Exception e1) {
//                    e1.printStackTrace();
//                }
//            }
//        }finally {
//            if(con != null) {
//                try{
//                    con.close();
//                }catch(Exception e){
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

    // 获取商品信息的方法
    public Item getItemById(int itemId) {
        Item item = null;
        String sql = "SELECT * FROM item WHERE id = ?"; // 假设表名为 items

        try {
            Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, itemId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                item = new Item();
                item.setId(rs.getInt("id"));
                item.setName(rs.getString("name"));
                item.setPrice(rs.getDouble("price"));
                // 根据 Item 类的属性添加更多字段
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    // 添加订单
    public int addOrder(Order order) {
        try {
            return oDao.insertOrder(order);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("添加订单失败", e);
        }
    }

    // 添加订单项
    public void addOrderItem(OrderItem orderItem) {
        try {
            oDao.insertOrderItem(orderItem);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("添加订单项失败", e);
        }
    }

    public List<Order> selectAll(int userId) {
        return oDao.findOrdersByUserId(userId);
    }

//    public Page getOrderPage(int status, int pageNumber) {
//        Page p = new Page();
//        p.setPageNumber(pageNumber);
//        int pageSize = 10;
//        int totalCount = 0;
//        try {
//            totalCount = oDao.getOrderCount(status);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        p.SetPageSizeAndTotalCount(pageSize, totalCount);
////        List list=null;
//        List<Order> list = null;
//        try {
//            list = oDao.selectOrderList(status, pageNumber, pageSize);
//            for(Order o :list) {
//                List<OrderItem> l = oDao.selectAllItem(o.getId());
//                o.setItemList(l);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        p.setList(list);
//        return p;
//    }

    public void updateStatus(int id,int status) {
        try {
            oDao.updateStatus(id, status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        Connection con = null;
        try {
            con = DBUtil.getConnection();
            con.setAutoCommit(false);

            oDao.deleteOrderItem(con, id);
            oDao.deleteOrder(con, id);
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if(con!=null) {
                try {
                    con.rollback();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }finally{
            if(con != null) {
                try{
                    con.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}

//    public List<Order> selectAll(int userId){
//        List<Order> list=null;
//        try {
//            list = oDao.selectAll(userId);
//            for(Order o :list) {
//                List<OrderItem> l = oDao.selectAllItem(o.getId());
//                o.setItemList(l);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
