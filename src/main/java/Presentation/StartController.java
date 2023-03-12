package Presentation;

import Model.Client;
import Model.Product;

public class StartController {
    StartView startView = new StartView();

    public StartController(){
        startView.getClientOP().addActionListener(
                e -> new OPController<>(Client.class)
        );
        startView.getProductOP().addActionListener(
                e -> new OPController<>(Product.class)
        );
        startView.getProductOrders().addActionListener(
                e -> new OrderController()
        );
    }
}
