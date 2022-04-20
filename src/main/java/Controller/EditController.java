package Controller;

import DB.AbstractDAO;
import Model.Client;
import Model.Product;
import View.EditView;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class EditController<T> {
    EditView abstractEditView;
    AbstractDAO<T> abstractDAO;
    public EditController(Class<T> type){
        abstractDAO = new AbstractDAO<>(type);

        List<T> objects = abstractDAO.findAll();
        String[] array = new String[objects.size()];
        for(T o : objects) array[objects.indexOf(o)] = o.toString();

        abstractEditView = new EditView(array, type.getSimpleName());
        abstractEditView.getSubmit().addActionListener(
                e->{
                    T t = objects.get(abstractEditView.getComboBox().getSelectedIndex());


                    try{
                        if(type.isAssignableFrom(Client.class)) {
                            Client client = (Client) t;
                            client.setName(abstractEditView.getFirstIn().getText());
                            client.setAge(Integer.parseInt(abstractEditView.getSecondIn().getText()));
                            client.setAddress(abstractEditView.getThirdIn().getText());
                        }
                        else if(type.isAssignableFrom(Product.class)){
                            Product product = (Product) t;
                            product.setName(abstractEditView.getFirstIn().getText());
                            product.setCost(Integer.parseInt(abstractEditView.getSecondIn().getText()));
                            product.setQuantity(Integer.parseInt(abstractEditView.getThirdIn().getText()));
                        }
                        abstractDAO.update(t);
                        abstractEditView.getComboBox().removeAllItems();
                        for(T object : objects){
                            abstractEditView.getComboBox().addItem(object.toString());
                        }

                    }catch(NumberFormatException exception){
                        JOptionPane.showMessageDialog(null, "You entered a non-number in a number filed", "nan", JOptionPane.ERROR_MESSAGE);
                    }catch (SQLException sqlException){
                        JOptionPane.showMessageDialog(null, type.getName() + "DAO: " + "edit "  + sqlException.getMessage(), "SQL", JOptionPane.ERROR_MESSAGE);
                    }
                }
        );
    }

}
