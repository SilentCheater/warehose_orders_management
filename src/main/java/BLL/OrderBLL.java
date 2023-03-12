package BLL;

import BLL.Exceptions.NegativeNumber;
import DB.AbstractDAO;
import Model.Order;
import Model.Product;

import javax.swing.*;
import java.sql.SQLException;

public class OrderBLL {
    AbstractDAO<Order> abstractDAO = new AbstractDAO<>(Order.class);

    public OrderBLL(){}

    public OrderBLL(int client, int product, String quantity) throws NumberFormatException, NegativeNumber{
        if(Integer.parseInt(quantity)<0)
            throw new NegativeNumber();

    }

    public boolean insert(Order order, Product product) throws SQLException {
        if(order.getQuantity() != 0 && order.getQuantity()<=product.getQuantity()) {
            abstractDAO.insert(order);
            return true;
        }
        else {
            JOptionPane.showMessageDialog(null, "Can't order so many " + product.getName() + "\nMax quantity = " + product.getQuantity(), "quantity error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
