package Presentation;

import BLL.ClientBLL;
import BLL.Exceptions.NegativeNumber;
import BLL.ProductBLL;
import Model.Client;
import Model.Product;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EditController<T> {
    EditView editView;
    ProductBLL productBLL = new ProductBLL();
    ClientBLL clientBLL = new ClientBLL();
    public EditController(Class<T> type){
        List<T> objects;
        if(type.isAssignableFrom(Product.class)) {
            objects = (List<T>) productBLL.findAll();
        }
        else {
            objects = (List<T>) clientBLL.findAll();
        }
        String[] array = new String[objects.size()];
        for(T o : objects) array[objects.indexOf(o)] = o.toString();

        editView = new EditView(array, type.getSimpleName());
        editView.getSubmit().addActionListener(
                e->{
                    T t = objects.get(editView.getComboBox().getSelectedIndex());

                    try{
                        if(type.isAssignableFrom(Client.class)) {
                            List<Object> fields = new ArrayList<>();
                            fields.add("random");
                            fields.add(editView.getFirstIn().getText());
                            fields.add(editView.getSecondIn().getText());
                            fields.add(editView.getThirdIn().getText());
                            clientBLL.edit(fields, (Client) t);
                        }
                        else if(type.isAssignableFrom(Product.class)){
                            List<Object> fields = new ArrayList<>();
                            fields.add("random");
                            fields.add(editView.getFirstIn().getText());
                            fields.add(editView.getSecondIn().getText());
                            fields.add(editView.getThirdIn().getText());
                            productBLL.edit(fields, (Product) t);
                        }
                        editView.getComboBox().removeAllItems();
                        for(T object : objects){
                            editView.getComboBox().addItem(object.toString());
                        }

                    }catch(NumberFormatException exception){
                        JOptionPane.showMessageDialog(null, "You entered a non-number in a number filed", "nan", JOptionPane.ERROR_MESSAGE);
                    }catch (SQLException sqlException){
                        JOptionPane.showMessageDialog(null, type.getName() + "DAO: " + "edit "  + sqlException.getMessage(), "SQL", JOptionPane.ERROR_MESSAGE);
                    } catch (NegativeNumber negativeNumber) {
                        JOptionPane.showMessageDialog(null, "You entered a negative number", "nan", JOptionPane.ERROR_MESSAGE);
                    }
                }
        );
    }

}
