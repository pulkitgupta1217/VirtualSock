package pgupta.virtualsock.Facade;

import pgupta.virtualsock.Model.User;

/**
 * Created by pulki on 10/14/2017.
 */

public class AuthTuple {
    private String errorMessage;
    private boolean success;

    public AuthTuple() {
        errorMessage = "";
        success = false;
    }


    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
