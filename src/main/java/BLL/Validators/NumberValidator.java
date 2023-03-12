package BLL.Validators;

import BLL.Exceptions.NegativeNumber;

import javax.swing.*;
import java.text.NumberFormat;

public class NumberValidator implements Validator{
    @Override
    public void validate(String string) throws NumberFormatException, NegativeNumber {
        try{
            if(Integer.parseInt(string)<0)
                throw new NegativeNumber();
        }
        catch(NumberFormatException e){
            throw new NumberFormatException();
        }
    }
}
