package Presentation;

import javax.swing.*;
import java.awt.*;

public class DeleteView extends JFrame {
    private JComboBox comboBox;
    private JButton submit = new JButton("SUBMIT");
    public DeleteView(String[] objects){
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new GridLayout(2,1));

        comboBox = new JComboBox(objects);
        this.add(comboBox);
        this.add(submit);
        this.setVisible(true);
        this.pack();
    }
    public JButton getSubmit(){
        return submit;
    }

    public JComboBox getComboBox() {
        return comboBox;
    }
}
