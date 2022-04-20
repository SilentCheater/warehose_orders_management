package View;

import javax.swing.*;
import java.awt.*;

public class AddView extends JFrame{
    private JLabel label1;
    private JTextField firstIn = new JTextField();

    private JLabel secondLabel;
    private JTextField secondIn = new JTextField();

    private JLabel thirdLabel;
    private JTextField thirdIn = new JTextField();

    private final JButton submit = new JButton("SUBMIT");

    public AddView(String type){
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new GridLayout(8,1));

        if(type.equals("Client")) {
            label1 = new JLabel("Insert your name");
            secondLabel = new JLabel("Insert your age");
            thirdLabel = new JLabel("Insert your address");
        }
        else if (type.equals("Product")) {
            label1 = new JLabel("Insert product name");
            secondLabel = new JLabel("Insert product cost");
            thirdLabel = new JLabel("Insert product quantity");
        }

        this.add(label1);
        this.add(firstIn);
        this.add(secondLabel);
        this.add(secondIn);
        this.add(thirdLabel);
        this.add(thirdIn);
        this.add(submit);

        this.setVisible(true);
        this.pack();
        this.setResizable(false);
    }

    public JButton getSubmit() {
        return submit;
    }

    public JTextField getThirdIn() {
        return thirdIn;
    }

    public JTextField getSecondIn() {
        return secondIn;
    }

    public JTextField getFirstIn() {
        return firstIn;
    }

}
