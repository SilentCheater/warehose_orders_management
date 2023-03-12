package Presentation;

import javax.swing.*;
import java.awt.*;

public class StartView extends JFrame {
    JLabel selection = new JLabel("Choose the action to be preformed");
    JButton clientOP = new JButton("Client operations");
    JButton productOP = new JButton("Product operations");
    JButton productOrders = new JButton("Make a new order");

    public StartView(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(4,1));

        this.add(selection);
        this.add(clientOP);
        this.add(productOP);
        this.add(productOrders);

        this.setVisible(true);
        this.pack();
    }

    public JButton getClientOP() {
        return clientOP;
    }

    public JButton getProductOP() {
        return productOP;
    }

    public JButton getProductOrders() {
        return productOrders;
    }
}

