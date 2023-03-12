package BLL;

import BLL.Exceptions.NegativeNumber;
import BLL.Validators.NumberValidator;
import DB.AbstractDAO;
import Model.Client;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class ClientBLL {

    AbstractDAO<Client> abstractDAO = new AbstractDAO<>(Client.class);

    public ClientBLL(){}

    public void validate(List<Object> fields) throws NumberFormatException, NegativeNumber {
        NumberValidator numberValidator = new NumberValidator();
        numberValidator.validate((String)fields.get(2));
        int aux = Integer.parseInt((String)fields.get(2));
        fields.set(2, aux);
    }
    public void insert(List<Object> fields) throws SQLException, NumberFormatException, NegativeNumber {
        validate(fields);
        ObjectCreator<Client> objectCreator = new ObjectCreator<>();
        abstractDAO.insert(objectCreator.createObject(Client.class, fields));
        JOptionPane.showMessageDialog(null, "Successful insert", "good job", JOptionPane.INFORMATION_MESSAGE);
    }
    public List<Client> findAll(){
        return abstractDAO.findAll();
    }

    public void delete(Client client) throws SQLException {
        abstractDAO.delete(client);
    }

    public void edit(List<Object> fields, Client client) throws NegativeNumber, SQLException {
        validate(fields);
        client.setName((String) fields.get(1));
        client.setAge((Integer) fields.get(2));
        client.setAddress((String) fields.get(3));
        abstractDAO.update(client);
    }
}
