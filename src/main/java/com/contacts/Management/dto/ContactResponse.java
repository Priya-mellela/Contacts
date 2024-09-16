package com.contacts.Management.dto;



public class ContactResponse {

    private String message;
    private boolean hasErrors;
    private Object data;

    public ContactResponse(String message, boolean hasErrors, Object data) {
        this.message = message;
        this.hasErrors = hasErrors;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isHasErrors() {
        return hasErrors;
    }

    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
