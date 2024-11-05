package com.ifacehub.tennis.util;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

public class ResponseObject implements Serializable {
    private static final long serialVersionUID = 1L;
    private Object data;
    private String overallStatus;
    private HttpStatus httpStatus;
    private String message;
    private long val;
    public ResponseObject() {
        super();
    }


    public ResponseObject(Object data, String overallStatus, HttpStatus httpStatus, String message) {
        super();
        this.data = data;
        this.overallStatus = overallStatus;
        this.httpStatus = httpStatus;
        this.message = message;
    }
    public ResponseObject(Object data, long val, String overallStatus, HttpStatus httpStatus, String message) {
        super();
        this.data = data;
        this.overallStatus = overallStatus;
        this.httpStatus = httpStatus;
        this.message = message;
        this.setVal(val);
    }


    public ResponseObject(HttpStatus httpStatus, String overallStatus, String message) {
        this.overallStatus = overallStatus;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getOverallStatus() {
        return overallStatus;
    }

    public void setOverallStatus(String overallStatus) {
        this.overallStatus = overallStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getVal() {
        return val;
    }

    public void setVal(long val) {
        this.val = val;
    }
}
