package com.company.Incident.payload;

import java.beans.JavaBean;
import java.time.LocalDateTime;

@JavaBean
public class ApiResponse<T> {

    private Boolean success;
    private String message;
    private T data;
    private int error;
    private LocalDateTime timeStamp;

    public ApiResponse(Boolean success, String message, T data, int error, LocalDateTime timeStamp) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.error = error;
        this.timeStamp = timeStamp;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public ApiResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
