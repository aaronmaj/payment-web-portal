package com.econet.epg.portal.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("passwordValidator")
public class ConfirmPasswordValidator implements Validator {

    private static final String CONFIRMATION = "confirmation";

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        String password = (String) value;
        //HtmlInputSecret confirmationInput = (HtmlInputSecret) component.getAttributes().get("confirmation");
        UIInput confirmationInput = (UIInput) component.getAttributes().get(CONFIRMATION);

        if (confirmationInput == null) {
            return;
        }

        String confirm = String.valueOf(confirmationInput.getSubmittedValue());

        if (!isPasswordValid(password, confirm)) {
            confirmationInput.setValid(false);

            throw new ValidatorException(
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, getMessage(), "")
            );
        }
    }

    private String getMessage() {
        return "The password is not valid, acording the confiration input value";
    }

    private boolean isPasswordValid(String password, String confirm) {
        if (password == null || confirm == null) {
            return false;
        }

        if (!password.trim().equals(confirm.trim())) {
            return false;
        }

        return true;
    }

}
