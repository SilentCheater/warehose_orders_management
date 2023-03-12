package BLL;

import BLL.Exceptions.NegativeNumber;
import BLL.Validators.NumberValidator;
import DB.AbstractDAO;
import Model.Product;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class ProductBLL {

    AbstractDAO<Product> abstractDAO = new AbstractDAO<>(Product.class);

    public ProductBLL(){}

    public void validate(List<Object> fields) throws NumberFormatException, NegativeNumber {
        NumberValidator numberValidator = new NumberValidator();
        numberValidator.validate((String)fields.get(2));
        numberValidator.validate((String)fields.get(3));
        int aux = Integer.parseInt((String)fields.get(2));
        int aux2 = Integer.parseInt((String) fields.get(3));
        fields.set(2, aux);
        fields.set(3,aux2);
    }
    public void insert(List<Object> fields) throws SQLException, NegativeNumber, NumberFormatException {
        validate(fields);
        ObjectCreator<Product> objectCreator = new ObjectCreator<>();
        abstractDAO.insert(objectCreator.createObject(Product.class, fields));
        JOptionPane.showMessageDialog(null, "Successful insert", "good job", JOptionPane.INFORMATION_MESSAGE);
    }

    public List<Product> findAll(){
        return abstractDAO.findAll();
    }

    public void delete(Product product) throws SQLException {
        abstractDAO.delete(product);
    }
    public void update(Product product) throws SQLException {
        abstractDAO.update(product);
    }
    public void edit(List<Object> fields, Product product) throws NegativeNumber, SQLException {
        validate(fields);
        product.setName((String) fields.get(1));
        product.setCost((Integer) fields.get(2));
        product.setQuantity((Integer) fields.get(3));
        abstractDAO.update(product);
    }
}
