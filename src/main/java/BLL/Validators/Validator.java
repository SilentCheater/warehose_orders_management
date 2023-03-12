package BLL.Validators;

import BLL.Exceptions.NegativeNumber;

public interface Validator {
    public void validate(String string) throws NumberFormatException, NegativeNumber;
}
