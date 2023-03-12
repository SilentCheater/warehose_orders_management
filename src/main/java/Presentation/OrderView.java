package Presentation;

import javax.swing.*;
import java.awt.*;

public class OrderView extends JFrame {
    JComboBox clients;
    JComboBox products;
    JTextField quantity = new JTextField("Enter the quantity");
    JButton submit = new JButton("SUBMIT");
    public OrderView(String[] clientsArr, String[] productsArr){
        clients = new JComboBox(clientsArr);
        products = new JComboBox(productsArr);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new GridLayout(2, 2));

        add(clients);
        add(products);
        add(quantity);
        add(submit);

        setVisible(true);
        pack();
    }

    public JButton getSubmit() {
        return submit;
    }

    public JComboBox getClients() {
        return clients;
    }

    public JComboBox getProducts() {
        return products;
    }

    public JTextField getQuantity() {
        return quantity;
    }
}
