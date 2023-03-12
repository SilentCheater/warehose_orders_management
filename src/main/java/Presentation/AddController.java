package Presentation;

import BLL.ClientBLL;
import BLL.Exceptions.NegativeNumber;
import BLL.ProductBLL;
import DB.AbstractDAO;
import Model.Product;

import javax.swing.*;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class AddController<T> {
    AddView addView;
    public AddController(Class<T> type){
        addView = new AddView(type.getSimpleName());
        addView.getSubmit().addActionListener(
                e ->{
                    try{
                        List<Object> attributes = new ArrayList<>();
                        attributes.clear();
                        attributes.add(ThreadLocalRandom.current().nextInt(1, 5000000));
                        attributes.add(addView.getFirstIn().getText());
                        attributes.add(addView.getSecondIn().getText());
                        attributes.add(addView.getThirdIn().getText());
                        if(type.isAssignableFrom(Product.class)) {
                            ProductBLL productBLL = new ProductBLL();
                            productBLL.insert(attributes);
                        }
                        else {
                            ClientBLL clientBLL = new ClientBLL();
                            clientBLL.insert(attributes);
                        }
                        addView.dispose();
                    }catch(NumberFormatException exception){
                        JOptionPane.showMessageDialog(null, "You entered a non-number in a number filed", "nan", JOptionPane.ERROR_MESSAGE);
                    } catch (SQLException sqlException) {
                        JOptionPane.showMessageDialog(null, type.getName() + "DAO: " + "insert "  + sqlException.getMessage(), "SQL", JOptionPane.ERROR_MESSAGE);
                    }
                    catch (NegativeNumber exception){
                        JOptionPane.showMessageDialog(null, "You entered a negative number", "Negative number", JOptionPane.ERROR_MESSAGE);
                    }
                }
        );
    }

}
