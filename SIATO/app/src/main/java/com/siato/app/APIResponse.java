package com.siato.app;

public class APIResponse<T> {
    private Boolean error;
    private String message;
    private T data;

    public APIResponse(Boolean error, String message, T data) {
        this.error = error;
        this.message = message;
        this.data = data;
    }

    public Boolean getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
